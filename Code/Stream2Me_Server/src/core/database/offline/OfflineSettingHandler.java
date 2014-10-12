/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.offline;

import biz.source_code.base64Coder.Base64Coder;
import channel.group.ClientHandler;
import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.Db4oIOException;
import core.database.abstractInterface.SettingHandler;
import core.database.objects.Settings;
import core.database.offline.object.Client;
import core.database.offline.object.Collection;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.settings.group.GroupCreateMessage;
import messages.settings.group.GroupJoinMessage;

/**
 *
 * @author Bernhard
 */
public class OfflineSettingHandler implements SettingHandler {

    @Override
    public Settings getSettings(String userID) {
        try {
            ObjectSet<Client> getUser =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client client =getUser.next();

            Collection group =client.getGroup();
            String groupID =group.getGroupID();
            String groupName =group.getName();
            String ownerID ="";
            String ownerName =null;
            if (group.getOwner() != null) {
                Client owner =group.getOwner();
                ownerID =owner.getUserid();
                ownerName =owner.getName()+" "+owner.getSurname();
            }

            return new Settings(groupID, groupName, ownerName, ownerID);
        } catch (Db4oIOException ioe) {
              Logger.getLogger(OfflineSettingHandler.class.getName())
                      .log(Level.SEVERE, null, ioe);
        } catch (DatabaseClosedException dce) {
              Logger.getLogger(OfflineSettingHandler.class.getName())
                      .log(Level.SEVERE, null, dce);
        }
        return null;
    }
    
    @Override
    public Map<String, String> getGroups() {
        Map<String, String> groups =new HashMap<>();
        
        try {
            ObjectSet<Collection> result =OfflineDatabase.INSTANCE.db.query(Collection.class);
            while (result.hasNext()) {
                Collection col =result.next();
                groups.put(col.getGroupID(), col.getName());
            }
        } catch(Db4oIOException ex) {
              Logger.getLogger(OfflineUserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
        }
        
        return groups;
    }

    @Override
    public GroupJoinMessage groupJoin(String userID, String groupID, String password) {
        Settings settings =getSettings(userID);
        
        try {
            if (settings.isOwner(userID)) {
                System.out.println("Hello joinses");
            }
            ObjectSet<Collection> result =OfflineDatabase.INSTANCE.db.queryByExample(new Collection(groupID, null, null, null));
            
            if (result.hasNext()) {
                Collection col =result.next();
                String uid =col.getGroupID();
                String pass =col.getPassword();

                boolean digestMatch =false;
                if (pass == null) {
                    if (password.isEmpty()) {
                        digestMatch =true;
                    } else {
                        GroupJoinMessage gjm =new GroupJoinMessage(false);
                        gjm.setError("Password mismatch");
                        return gjm;
                    }
                    
                } else {
                    byte[] dbDigest =pass.getBytes(OfflineDatabase.ENCODING);

                    password =Base64Coder.decodeString(password);
                    String pwd =uid+password;
                    byte[] pwdDigest =OfflineDatabase.INSTANCE.digest.digest(pwd.getBytes(OfflineDatabase.ENCODING));
                    digestMatch =MessageDigest.isEqual(pwdDigest, dbDigest);
                }
                if (digestMatch || pass == null) {
                    ObjectSet<Client> resultC =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
                    Client c =resultC.next();
                    c.setGroup(col);
                    OfflineDatabase.INSTANCE.db.store(c);
                    OfflineDatabase.INSTANCE.db.commit();
                    return new GroupJoinMessage(true);
                }
            }
        } catch(Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, "SQLException", ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OfflineSettingHandler.class.getName())
                    .log(Level.SEVERE, "UnsupportedEncodingException", ex);
        }
        GroupJoinMessage gjm =new GroupJoinMessage(false);
        gjm.setError("Password mismatch");
        return gjm;
    }

    @Override
    public GroupCreateMessage groupCreate(String userID, String groupName, String password) {
        Settings settings =getSettings(userID);
        
        try {
            if (settings.isOwner(userID)) {
                System.out.println("Hello creatsing");
                //Find someone else and promote
            }
            
            ObjectSet<Client> clientRes =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client c =clientRes.next();
            
            String uID =UUID.randomUUID().toString();
            String pwd =Base64Coder.decodeString(password);
            Collection col =new Collection(uID, c, groupName, OfflineDatabase.INSTANCE.getPassword(pwd, uID));
            OfflineDatabase.INSTANCE.db.store(col);
            OfflineDatabase.INSTANCE.db.commit();
            c.setGroup(col);
            OfflineDatabase.INSTANCE.db.store(c);
            OfflineDatabase.INSTANCE.db.commit();

            ClientHandler.createGroup(uID);
            return new GroupCreateMessage(true, uID);
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineRegistrationHandler.class.getName()).log(Level.SEVERE, "SQLException", ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OfflineRegistrationHandler.class.getName()).log(Level.SEVERE, "UnsupportedEncodingException", ex);
        }
        return new GroupCreateMessage(false, null);
    }
}
