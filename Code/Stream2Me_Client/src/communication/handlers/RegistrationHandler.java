/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.handlers;

import java.util.logging.Logger;
import messages.ConsoleMessage;
import messages.Message;
import messages.userConnection.registration.CheckEmailMessage;
import messages.userConnection.registration.CheckUsernameMessage;
import messages.userConnection.registration.RegistrationResponseMessage;
import mvc.controller.authentication.RegisterControl;

/**
 *
 * @author Bernhard
 */
public class RegistrationHandler extends MessageHandler {
    private final static Logger LOGGER = Logger.getLogger(RegistrationHandler.class.getName()); 
    
    @Override
    public boolean handle(Message msg) {
        switch (msg.handle()) {
            case checkUsername:
                CheckUsernameMessage unMsg =(CheckUsernameMessage) msg;
                RegisterControl.usernameCheckResponse(unMsg.isValid(), unMsg.getError());
                return true;
            case checkEmail:
                CheckEmailMessage eMsg =(CheckEmailMessage) msg;
                RegisterControl.emailCheckResponse(eMsg.isValid(), eMsg.getError());
                return true;
            case registrationResponse:
                RegistrationResponseMessage rrMsg =(RegistrationResponseMessage) msg;
                RegisterControl.registrationResponse(rrMsg.isSuccess(), rrMsg.isValidate());
            case console:
                return handleConsole((ConsoleMessage)msg);
            default:
                return false;
        }
    }
}
