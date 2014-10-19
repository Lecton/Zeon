
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
public class UsersSaltyEncryption {
    static MessageDigest digest;
    
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String password ="cos301";
        String bernhard ="029a4095-4588-4c8a-b416-8e5ddbf7b585";
        
        digest =MessageDigest.getInstance("SHA-512");
        
        updateDB(bernhard, getPassword(password, bernhard));
    }
    
    private static String getPassword(String pass, String key) throws UnsupportedEncodingException {
        String pwd =key+pass;
        byte[] pwdDigest =digest.digest(pwd.getBytes());
        String temp =new String(pwdDigest);
        System.out.println(temp);
        return temp;
    }
    
    private static void updateDB(String userID, String password) {
        DatabaseHandler.setOnline();
        PreparedStatement statement;
        ResultSet result = null;
        String query = "UPDATE client " +
                        "SET password = ? " +
                        "WHERE userID = ?";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
        try {
            statement.setString(1, password);
            statement.setString(2, userID);
            int lines =statement.executeUpdate();
            System.out.println(lines);
        } catch (SQLException ex) {
            Logger.getLogger(UsersSaltyEncryption.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(UsersSaltyEncryption.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}
