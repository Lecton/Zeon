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
public class RegistrationResponseMessage extends Message {
    private boolean success =false;
    private boolean validate =false;
    
    public RegistrationResponseMessage(boolean success) {
        this.success =success;
        this.validate =false;
    }
    
    public RegistrationResponseMessage(boolean success, boolean validate) {
        this.success =success;
        this.validate =validate;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isValidate() {
        return validate;
    }
    
    @Override
    public String getMessage() {
        return "Registration successful: "+success+". Has additional validation: "+validate+".";
    }

    @Override
    public MessageType handle() {
        return MessageType.registrationResponse;
    }
    
}
