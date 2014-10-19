/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.online;

import biz.source_code.base64Coder.Base64Coder;
import core.database.abstractInterface.RegistrationHandler;
import java.io.UnsupportedEncodingException;
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
public class OnlineRegistrationHandler implements RegistrationHandler {
    
    @Override
    public boolean checkUsername(CheckUsernameMessage msg) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT username " +
                        "FROM client";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
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
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        msg.setError("Username invalid");
        return false;
    }
    
    @Override
    public boolean checkEmail(CheckEmailMessage msg) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "SELECT email " +
                        "FROM client";
        statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
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
            Logger.getLogger(OnlineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(OnlineUserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        msg.setError("Email invalid");
        return false;
    }

    @Override
    public Message register(RegisterMessage msg) {
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
                statement = OnlineDatabase.INSTANCE.getPreparedStatement(query);
                String uID =UUID.randomUUID().toString();
                statement.setString(1, new String(uID.getBytes()));
                statement.setString(2, new String(name.getBytes()));
                statement.setString(3, new String(surname.getBytes()));
                statement.setString(4, new String(username.getBytes()));
                statement.setString(5, OnlineDatabase.INSTANCE.getPasswordForDatabase(uID, password));
                statement.setString(6, new String(email.getBytes()));
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
                Logger.getLogger(OnlineRegistrationHandler.class.getName()).log(Level.SEVERE, "SQLException", ex);
            }
        }
        return new RegistrationResponseMessage(false);
    }
}
