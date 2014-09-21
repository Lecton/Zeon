/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.userConnection.GreetingMessage;
import mvc.controller.LoginControl;

/**
 *
 * @author Bernhard
 */
public class LoginHandler extends MessageHandler {
    private final static Logger LOGGER = Logger.getLogger(LoginHandler.class.getName()); 
    
    @Override
    public boolean handle(Message msg) {
        switch (msg.handle()) {
            case greeting:
                final GreetingMessage message =(GreetingMessage)msg;
                LOGGER.log(Level.INFO, "Login: "+message.isSuccessful());
                LoginControl.response(message.isSuccessful(), message.getResponse(), message);
                return true;
            case console:
                return handleConsole(msg);
            default:
                return false;
        }
    }
}
