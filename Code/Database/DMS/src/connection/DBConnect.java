/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lecton
 */
public class DBConnect{
    private String host; //127.0.0.1
    private int port;//5433
    private String database;//stream2me
    private String username;//postgres
    private String password;//root
    private Connection connection;
    public DBConnect(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connection = null;
    }
    
    public boolean connect(){

                if(checkDrivers()){
                    try {
                            connection = DriverManager.getConnection(
                                            "jdbc:postgresql://"+this.host+":"+this.port+"/"+this.database, 
                                            this.username,this.password);
                        if (connection != null) {
                            return true;
                        }                         

                    } catch (SQLException e) {

                            System.out.println("Connection Failed! Check output console");
                            e.printStackTrace();
                            return false;

                    }     
        }  
        return false;
    }
    
    private boolean checkDrivers(){
        try 
        {
            Class.forName("org.postgresql.Driver");
            return true;
        }catch(ClassNotFoundException e){
            System.out.println("PostgreSQL JDBC Driver not found");
            e.printStackTrace();
            return false;
        }        
    }
    
    public PreparedStatement getPreparedStatement(String statement){
        try {
            return connection.prepareStatement(statement);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//    
    public Statement getStatment(){
        try {
            return connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
