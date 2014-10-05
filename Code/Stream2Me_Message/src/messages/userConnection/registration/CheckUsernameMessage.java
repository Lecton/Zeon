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
public class CheckUsernameMessage extends Message {
    private String username;
    private boolean valid =false;
    private String error ="";

    public CheckUsernameMessage(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
    
    @Override
    public String getMessage() {
        return "Checking username: "+username+" for availability";
    }

    @Override
    public MessageType handle() {
        return MessageType.checkUsername;
    }
    
    
}
