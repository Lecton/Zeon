/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.offline;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import core.database.abstractInterface.Database;
import core.database.offline.object.Client;
import core.database.offline.object.Collection;
import core.database.offline.object.Connection;
import core.database.offline.object.StreamData;
import core.database.offline.object.StreamProp;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class OfflineDatabase implements Database {
    protected static OfflineDatabase INSTANCE;
    
    protected ObjectContainer db;
    protected MessageDigest digest;

    public OfflineDatabase() {
        try {
            digest =MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean connect() {
        INSTANCE =this;
        System.out.println("Starting Offline Database");
        db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "stream2Me.db");
        
        ObjectSet<Collection> groups = db.queryByExample(new Collection("-111", null, null, null));
        if (groups.size() == 0) {
            Collection.DEFAULT =new Collection("-111", null, "Default", null);
            db.store(Collection.DEFAULT);
            db.commit();
        } else {
            Collection.DEFAULT =groups.next();
        }

        ObjectSet<Client> result = db.queryByExample(Client.class);
        System.out.println("\tProfile count: " + result.size());
        ObjectSet<Collection> result2 = db.queryByExample(Collection.class);
        System.out.println("\tGroup count: " + result2.size());
        ObjectSet<Collection> result3 = db.queryByExample(Connection.class);
        System.out.println("\tConnection count: " + result3.size());
        ObjectSet<Collection> result4 = db.queryByExample(StreamProp.class);
        System.out.println("\tStreamProperty count: " + result4.size());
        ObjectSet<Collection> result5 = db.queryByExample(StreamData.class);
        System.out.println("\tStreamData count: " + result5.size());
        return true;
    }
    
    @Override
    public String getPassword(String pass, String key) throws UnsupportedEncodingException {
        String pwd =key+pass;
        byte[] pwdDigest =digest.digest(pwd.getBytes("Latin1"));
        String temp =new String(pwdDigest, "Latin1");
        return temp;
    }

    @Override
    public boolean close() {
        System.out.println("Closing");
        if (db != null) {
            db.commit();
            return db.close();
        }
        return false;
    }
}
