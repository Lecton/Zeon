/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller.message;

import messages.Message;
import messages.userConnection.GreetingMessage;
import mvc.controller.LoginControl;
import mvc.controller.UpdateControl;
import mvc.controller.UserControl;
import mvc.model.Model;

/**
 *public void setUser(String userID, String username, String name, String surname, String email, String avatar, String title, String aboutMe)
 * @author Bernhard
 */
public class LoginHandler extends MessageHandler {
    
    @Override
    public boolean handle(Message msg) {
        switch (msg.handle()) {
            case greeting:
                final GreetingMessage message =(GreetingMessage)msg;
                System.out.println("Login: "+message.isSuccessful());
                LoginControl.response(message.isSuccessful(), message.getResponse());
                
                Model.INSTANCE.setUser(msg.getUserID(), message.getUsername(), 
                        message.getName(), message.getSurname(), message.getEmail(),
                        message.getAvatar(), message.getTitle(), message.getAboutMe());
                java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        while (UserControl.unregistered()) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                
                            }
                        }
                        UpdateControl.INSTANCE.add(message.getName()+" "
                                +message.getSurname(), UpdateControl.LOGIN, message.getAvatar());
                    }
                });
                return true;
            case console:
                return handleConsole(msg);
            default:
                return false;
        }
    }
}
