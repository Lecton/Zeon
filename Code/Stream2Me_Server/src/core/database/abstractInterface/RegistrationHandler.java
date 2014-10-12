/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.abstractInterface;

import messages.Message;
import messages.userConnection.registration.CheckEmailMessage;
import messages.userConnection.registration.CheckUsernameMessage;
import messages.userConnection.registration.RegisterMessage;

/**
 *
 * @author Bernhard
 */
public interface RegistrationHandler {
    
    public abstract boolean checkUsername(CheckUsernameMessage msg);
    
    public abstract boolean checkEmail(CheckEmailMessage msg);

    public abstract Message register(RegisterMessage msg);
}
