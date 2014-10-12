
import core.database.DatabaseHandler;
import core.database.online.OnlineDatabase;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Bernhard
 */
public class GroupsSaltyEncryption {
    static MessageDigest digest;
    
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String password ="1234";
        String ownerID ="dd124987-5599-4adf-8b6a-e465b23cfc61";
        String groupID ="f90c78bd-5d1e-424d-9243-ebe7a971eb15";
        
        digest =MessageDigest.getInstance("SHA1");
        
        
        System.out.println(getPassword(password, groupID));
        
        
    }
    
    private static String getPassword(String pass, String key) throws UnsupportedEncodingException {
        String pwd =key+pass;
        byte[] pwdDigest =digest.digest(pwd.getBytes("Latin1"));
        String temp =new String(pwdDigest, "Latin1");
        return temp;
    }
    
    private static void updateDB(String userID, String password) {
        DatabaseHandler.setOnline();
        PreparedStatement statement;
        ResultSet result = null;
        String query = "UPDATE client " +
                        "SET password = ? " +
                        "WHERE userID = ?";
        statement = DatabaseHandler.database.getPreparedStatement(query);
        try {
            statement.setString(1, password);
            statement.setString(2, userID);
            int lines =statement.executeUpdate();
            System.out.println(lines);
        } catch (SQLException ex) {
            Logger.getLogger(GroupsSaltyEncryption.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(GroupsSaltyEncryption.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}
