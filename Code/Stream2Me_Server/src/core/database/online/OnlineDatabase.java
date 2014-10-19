/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.online;

import core.database.abstractInterface.Database;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.util.Arrays;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class OnlineDatabase implements Database {
    public static OnlineDatabase INSTANCE;
            
    private MessageDigest digest;
    
    private final String host; //127.0.0.1
    private final int port;//5433
    private final String database;//stream2me
    private final String username;//postgres
    private final String dbPassword;//root
    private Connection connection;

    public OnlineDatabase() {
        try {
            digest =MessageDigest.getInstance(ENCRYPTION);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        this.host = "127.0.0.1";
        this.port = 5433;
        this.database = "stream2me";
        this.username = "postgres";
        this.dbPassword = "root";
        this.connection = null;
    }
    
    @Override
    public boolean connect() {
        INSTANCE =this;
        System.out.println("Starting Online Database");
        if(checkDrivers()) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://"+this.host+":"+this.port+"/"+this.database, 
                        this.username, this.dbPassword);
                if (connection != null) {
                    checkDefaultGroup();
                    
                    
                    return true;
                }
            } catch (SQLException e) {
                Logger.getLogger(OnlineDatabase.class.getName()).log(Level.SEVERE, 
                        "Connection Failed! Check output console", e);
                return false;
            }
        }
        return false;
    }
    
    private boolean checkDrivers(){
        try {
            Class.forName("org.postgresql.Driver");
            return true;
        } catch(ClassNotFoundException e) {
            Logger.getLogger(OnlineDatabase.class.getName()).log(Level.SEVERE, 
                    "PostgreSQL JDBC Driver not found", e);
            return false;
        }        
    }
    
    public PreparedStatement getPreparedStatement(String statement){
        try {
            return connection.prepareStatement(statement);
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    protected Statement getStatment(){
        try {
            return connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean close() {
        if (connection != null) {
            try {
                connection.close();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(OnlineDatabase.class.getName()).log(Level.SEVERE, "SQLException", ex);
            }
        }
        return false;
    }

    private void checkDefaultGroup() {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT groupid "
                        + "FROM collection "
                        + "WHERE groupid = '-111'";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try{
            result = statement.executeQuery();

            if(result.next()) {
                return ;
            } else {
                query ="INSERT INTO collection (groupid, ownerid, name) VALUES ('-111','-111', 'Default')";
                statement =OnlineDatabase.INSTANCE.getPreparedStatement(query);
                statement.execute();
            }
        } catch (SQLException ex) {
            Logger.getLogger(OnlineDatabase.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(OnlineDatabase.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public boolean validatePassword(String salt, String pass, String key) {
        System.out.println(pass);
        pass =getPasswordForDatabase(salt, pass);
        System.out.println(pass);
        System.out.println(key);
        System.out.println(Arrays.areEqual(pass.getBytes(), key.getBytes()));
        return Arrays.areEqual(pass.getBytes(), key.getBytes());
    }

    @Override
    public String getPasswordForDatabase(String salt, String pass) {
        pass =salt+pass;
        return getHex(digest.digest(pass.getBytes()));
    }

    @Override
    public String getHex(byte[] b){
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++){
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
}
