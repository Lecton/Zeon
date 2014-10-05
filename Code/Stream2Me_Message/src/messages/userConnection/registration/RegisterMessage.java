/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.userConnection.registration;

import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 */
public class RegisterMessage extends Message {
    String username;
    String name;
    String surname;
    String email;
    String pw;

    public RegisterMessage(String username, String name, String surname, String email, String pw) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.pw = pw;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPass() {
        return pw;
    }
    
    @Override
    public String getMessage() {
        return "Registration.";
    }

    @Override
    public MessageType handle() {
        return MessageType.register;
    }
    
}
