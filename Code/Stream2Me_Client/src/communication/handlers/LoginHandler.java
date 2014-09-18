/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication.handlers;

import messages.Message;
import messages.userConnection.GreetingMessage;
import mvc.controller.LoginControl;
import mvc.controller.UpdateControl;

/**
 *
 * @author Bernhard
 */
public class LoginHandler extends MessageHandler {
    
    @Override
    public boolean handle(Message msg) {
        switch (msg.handle()) {
            case greeting:
                final GreetingMessage message =(GreetingMessage)msg;
                System.out.println("Login: "+message.isSuccessful());
                LoginControl.response(message.isSuccessful(), message.getResponse(), message);
                if (message.isSuccessful()) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            UpdateControl.INSTANCE.add(message.getUserID(), UpdateControl.LOGIN);
                        }
                    });
                }
                return true;
            case console:
                return handleConsole(msg);
            default:
                return false;
        }
    }
}
