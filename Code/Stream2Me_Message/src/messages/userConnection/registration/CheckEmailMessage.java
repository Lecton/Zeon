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
public class CheckEmailMessage extends Message {
    private String email;
    private boolean valid =false;
    private String error ="";

    public CheckEmailMessage(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "Checking Email: "+email+" for availability";
    }

    @Override
    public MessageType handle() {
        return MessageType.checkEmail;
    }
    
    
}
