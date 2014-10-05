/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database;

import biz.source_code.base64Coder.Base64Coder;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.userConnection.registration.CheckEmailMessage;
import messages.userConnection.registration.CheckUsernameMessage;
import messages.userConnection.registration.RegisterMessage;
import messages.userConnection.registration.RegistrationResponseMessage;

/**
 *
 * @author Bernhard
 */
public class RegistrationHandler {
    
    public static boolean checkUsername(CheckUsernameMessage msg) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT username " +
                        "FROM client";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try {
            result = statement.executeQuery();

            while (result.next()) {
                String un =result.getString("username");
                if (un.equalsIgnoreCase(msg.getUsername())) {
                    msg.setError("Username already taken");
                    return false;
                }
            }
            return true;
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
        msg.setError("Username invalid");
        return false;
    }
    
    public static boolean checkEmail(CheckEmailMessage msg) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT email " +
                        "FROM client";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try {
            result = statement.executeQuery();

            while (result.next()) {
                String un =result.getString("email");
                if (un.equalsIgnoreCase(msg.getEmail())) {
                    msg.setError("Email already in use");
                    return false;
                }
            }
            return true;
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
        msg.setError("Email invalid");
        return false;
    }

    public static Message register(RegisterMessage msg) {
        boolean validUsername =checkUsername(new CheckUsernameMessage(msg.getUsername()));
        boolean validEmail =checkEmail(new CheckEmailMessage(msg.getEmail()));
        
        if (validUsername && validEmail) {
            try {
                String username =msg.getUsername();
                String name =msg.getName();
                String surname =msg.getSurname();
                String email =msg.getEmail();
                String password =Base64Coder.decodeString(msg.getPass());
                
                PreparedStatement statement =null;
                String query = "INSERT INTO client " +
                        "(userid, name, surname, username, password, email, registrationdate)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                statement = Database.INSTANCE.getPreparedStatement(query);
                String uID =UUID.randomUUID().toString();
                statement.setString(1, uID);
                statement.setString(2, name);
                statement.setString(3, surname);
                statement.setString(4, username);
                statement.setString(5, getPassword(password, uID));
                statement.setString(6, email);
                statement.setObject(7, new Date(Calendar.getInstance().getTimeInMillis()));
                int result =statement.executeUpdate();
//                int count =0;
//                while (result.next()) {
//                    count++;
//                }
                if (result == 1) {
                    return new RegistrationResponseMessage(true);
                } else {
//                    result.beforeFirst();
//                    while (result.next()) {
//                        result.deleteRow();
//                    }
                    System.out.println("Hell");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistrationHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(RegistrationHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new RegistrationResponseMessage(false);
    }
    
    private static String getPassword(String pass, String key) throws UnsupportedEncodingException {
        String pwd =key+pass;
        byte[] pwdDigest =UserHandler.digest.digest(pwd.getBytes("Latin1"));
        String temp =new String(pwdDigest, "Latin1");
        return temp;
    }
}
