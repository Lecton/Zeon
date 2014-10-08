/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

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
public class Database {
    static MessageDigest digest;
    static {
        try {
            digest =MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    private String host; //127.0.0.1
    private int port;//5433
    private String database;//stream2me
    private String username;//postgres
    private String password;//root
    private Connection connection;
    
    public static Database INSTANCE =new Database();
    
    private Database() {
        this.host = "127.0.0.1";
        this.port = 5433;
        this.database = "stream2me";
        this.username = "postgres";
        this.password = "root";
        this.connection = null;
    }
    
    public boolean connect() {
        System.out.println("Starting Database");
        if(checkDrivers()) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://"+this.host+":"+this.port+"/"+this.database, 
                        this.username,this.password);
                if (connection != null) {
                    return true;
                }
            } catch (SQLException e) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, 
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
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, 
                    "PostgreSQL JDBC Driver not found", e);
            return false;
        }        
    }
    
    public PreparedStatement getPreparedStatement(String statement){
        try {
            return connection.prepareStatement(statement);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Statement getStatment(){
        try {
            return connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getPassword(String pass, String key) throws UnsupportedEncodingException {
        String pwd =key+pass;
        byte[] pwdDigest =digest.digest(pwd.getBytes("Latin1"));
        String temp =new String(pwdDigest, "Latin1");
        return temp;
    }
}
