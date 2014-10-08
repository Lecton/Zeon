
import core.database.Database;
import core.database.UserHandler;
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
public class UsersSaltyEncryption {
    static MessageDigest digest;
    
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String password ="cos301";
        String bernhard ="bca2414d-7ee0-40d9-a130-def36c44dc49";
        String lecton ="dd124987-5599-4adf-8b6a-e465b23cfc61";
        String xavier ="2f35aa55-3968-4669-b95c-c93668fa61b6";
        String zenadia ="9735d1f3-0503-40d4-a1c2-1e7c84dda390";
        
        digest =MessageDigest.getInstance("SHA1");
        
        
        updateDB(bernhard, getPassword(password, bernhard));
        
        
        
        updateDB(lecton, getPassword(password, lecton));
        updateDB(xavier, getPassword(password, xavier));
        updateDB(zenadia, getPassword(password, zenadia));
    }
    
    private static String getPassword(String pass, String key) throws UnsupportedEncodingException {
        String pwd =key+pass;
        byte[] pwdDigest =digest.digest(pwd.getBytes("Latin1"));
        String temp =new String(pwdDigest, "Latin1");
        return temp;
    }
    
    private static void updateDB(String userID, String password) {
        Database.INSTANCE.connect();
        PreparedStatement statement;
        ResultSet result = null;
        String query = "UPDATE client " +
                        "SET password = ? " +
                        "WHERE userID = ?";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, password);
            statement.setString(2, userID);
            int lines =statement.executeUpdate();
            System.out.println(lines);
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}
