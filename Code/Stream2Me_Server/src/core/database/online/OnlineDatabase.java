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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class OnlineDatabase implements Database {
    protected static OnlineDatabase INSTANCE;
            
    MessageDigest digest;
    
    private final String host; //127.0.0.1
    private final int port;//5433
    private final String database;//stream2me
    private final String username;//postgres
    private final String password;//root
    private Connection connection;

    public OnlineDatabase() {
        try {
            digest =MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        this.host = "127.0.0.1";
        this.port = 5433;
        this.database = "stream2me";
        this.username = "postgres";
        this.password = "root";
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
                        this.username,this.password);
                if (connection != null) {
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
    
    protected PreparedStatement getPreparedStatement(String statement){
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
    public String getPassword(String pass, String key) throws UnsupportedEncodingException {
        String pwd =new String((key+pass).getBytes(),ENCODING);
        byte[] pwdDigest =digest.digest(pwd.getBytes(ENCODING));
        String temp =new String(pwdDigest, ENCODING);
        return temp;
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
}
