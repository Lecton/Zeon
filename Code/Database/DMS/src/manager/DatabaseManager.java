/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import connection.DBConnect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.User;

/**
 *
 * @author Lecton
 */
public class DatabaseManager {
    
    private DBConnect dbconnect;
    private String host; //127.0.0.1
    private int port;//5433
    private String database;//stream2me
    private String username;//postgres
    private String password;//root
    
    public DatabaseManager(String host, int port, String database, String username, String password) {
        dbconnect = new DBConnect(host, port, database, username, password);
    }
        
    public void connect(){
        if(!dbconnect.connect()){
            System.err.println("Could not connect to database.");
        }
    }
    
    public String getUserID(String email){
        if(email != null){
            PreparedStatement statement;
            ResultSet result = null;
            String query = "SELECT userid " +
                            "FROM client " +
                            "WHERE email = ?";
            statement = dbconnect.getPreparedStatement(query);
            int i = 1;
            try{
                statement.setString(i, email);
                result = statement.executeQuery();
                
                if(result.next()){
                    return result.getString(1);
                }
              }catch (SQLException ex) {
                  System.out.println("here");
                  ex.printStackTrace();
//                  Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
              }finally{
                  try{
                      if(statement != null){
                          statement.close();
                      }
                  }catch(SQLException ex){
                      System.out.println("here2");
                      Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
            
        }
        return null;
    }
    public void addUser(User user){
      if(user != null){
            PreparedStatement statement;

            String query = "INSERT INTO client(userid,groupid,name,surname,username,email,"+
                                "avatar,title,registrationdate,loggedin) VALUES(?,?,?,?,?,?,?,?,?,?)";
            statement = dbconnect.getPreparedStatement(query);
            int i = 1;
              try {
                  statement.setString(i++, user.getUserID());
                  statement.setString(i++, user.getGroupID());
                  statement.setString(i++, user.getName());
                  statement.setString(i++, user.getSurname());
                  statement.setString(i++, user.getUsername());
                  statement.setString(i++, user.getEmail());
                  statement.setString(i++, user.getAvatar());
                  statement.setString(i++, user.getTitle());
                  statement.setDate(i++, user.getRegistration());
                  statement.setBoolean(i++, user.isLoggedIn());
                  statement.executeUpdate();
              } catch (SQLException ex) {
                  Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
              }finally{
                  try{
                      if(statement != null){
                          statement.close();
                      }
                  }catch(SQLException ex){
                      Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
          }
    }
    
    public java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }    
}
