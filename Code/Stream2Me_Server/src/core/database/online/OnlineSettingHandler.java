/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.online;

import biz.source_code.base64Coder.Base64Coder;
import channel.group.ClientHandler;
import core.database.abstractInterface.SettingHandler;
import core.database.objects.Settings;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class OnlineSettingHandler implements SettingHandler {
    @Override
    public Settings getSettings(String userID) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT DISTINCT c.groupid, col.name, col.ownerid, "
                + "(SELECT CONCAT(cl.name, ' ',cl.surname) "
                    + "FROM client AS cl "
                    + "WHERE cl.userid = col.ownerid) as owner "
                + "FROM client AS c, collection AS col "
                + "WHERE c.groupid = col.groupid AND c.userid = ?";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, userID);
            result = statement.executeQuery();

            if (result.next()) {
                String groupID =result.getString("groupid");
                String groupName =result.getString("name");
                String ownerID =result.getString("ownerid");
                String ownerName =result.getString("owner");
                return new Settings(groupID, groupName, ownerName, ownerID);
            }
        } catch(SQLException ex) {
              Logger.getLogger(OnlineUserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        
        return null;
    }

    @Override
    public Map<String, String> getGroups() {
        Map<String, String> groups =new HashMap<>();
        
        
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT groupid, name "
                + "FROM collection ";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try {
            result = statement.executeQuery();

            while (result.next()) {
                String groupID =result.getString("groupid");
                String groupName =result.getString("name");
                groups.put(groupID, groupName);
            }
        } catch(SQLException ex) {
              Logger.getLogger(OnlineUserHandler.class.getName())
                      .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        
        return groups;
    }

    @Override
    public GroupJoinMessage groupJoin(String userID, String groupID, String password) {
        Settings settings =getSettings(userID);
        
        PreparedStatement statement =null;
        ResultSet result =null;
        try {
            if (settings.isOwner(userID)) {
                System.out.println("Hello joinses");
            }
            String query = "SELECT groupID, password "
                    + "FROM collection "
                    + "WHERE groupID = ?";
            statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
            
            statement.setString(1, groupID);
            result = statement.executeQuery();

            if (result.next()) {
                String uid =result.getString("groupID");
                String pass =result.getString("password");

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
                    byte[] dbDigest =pass.getBytes(OnlineDatabase.ENCODING);

                    password =Base64Coder.decodeString(password);
                    String pwd =uid+password;
                    byte[] pwdDigest =OnlineDatabase.INSTANCE.digest.digest(pwd.getBytes(OnlineDatabase.ENCODING));
                    digestMatch =MessageDigest.isEqual(pwdDigest, dbDigest);
                }
                if (digestMatch || pass == null) {
                    query = "UPDATE client " +
                        "set groupid = ?" +
                        "WHERE userid = ?";
                    statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
                    statement.setString(1, groupID);
                    statement.setString(2, userID);
                    statement.executeUpdate();
                    return new GroupJoinMessage(true);
                }
            }
        } catch(SQLException ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, "SQLException", ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OnlineSettingHandler.class.getName())
                    .log(Level.SEVERE, "UnsupportedEncodingException", ex);
        } finally {
            try {
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        GroupJoinMessage gjm =new GroupJoinMessage(false);
        gjm.setError("Password mismatch");
        return gjm;
    }

    @Override
    public GroupCreateMessage groupCreate(String userID, String groupName, String password) {
        Settings settings =getSettings(userID);
        
        PreparedStatement statement;
        try {
            if (settings.isOwner(userID)) {
                System.out.println("Hello creatsing");
                //Find someone else and promote
            }

            String query ="INSERT INTO collection "
                    + "(groupID, ownerID, name, password) "
                    + "VALUES (?, ?, ?, ?)";

            String pwd =Base64Coder.decodeString(password);
            statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
            String uID =UUID.randomUUID().toString();
            statement.setString(1, uID);
            statement.setString(2, userID);
            statement.setString(3, groupName);
            statement.setString(4, OnlineDatabase.INSTANCE.getPassword(pwd, uID));
            
            int result =statement.executeUpdate();
            
            if (result == 1) {
                query = "UPDATE client " +
                    "set groupid = ?" +
                    "WHERE userid = ?";
                statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
                statement.setString(1, uID);
                statement.setString(2, userID);
                statement.executeUpdate();
                
                ClientHandler.createGroup(uID);
                return new GroupCreateMessage(true, uID);
            } else {
                System.out.println("Hell. Hells fury");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OnlineRegistrationHandler.class.getName()).log(Level.SEVERE, "SQLException", ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OnlineRegistrationHandler.class.getName()).log(Level.SEVERE, "UnsupportedEncodingException", ex);
        }
        return new GroupCreateMessage(false, null);
    }
}
