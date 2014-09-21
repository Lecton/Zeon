/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.handlers.MessageFactory;
import io.netty.util.ResourceLeakDetector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import mvc.model.UserMessage;
import mvc.view.generalUI.message.MessagePanel;

/**
 *
 * @author Bernhard
 */
public class ChatControl implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(ChatControl.class.getName());
    
    public static ChatControl INSTANCE =new ChatControl();
    private static MessagePanel view;
    
    public static void register(MessagePanel viewMessages) {
        view =viewMessages;
    }
    
    protected String getMessage() {
        return view.getMessage();
    }
    
    protected String getViewOwnerID() {
        return view.getUserID();
    }
    
    protected void setUserName(String name) {
        view.setUserName(name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("sendMessage")) {
            String message =view.getMessage();
            String targetID =view.getUserID();
            String userID =UserControl.getUserID();
            String name =UserControl.INSTANCE.getFullname();
            disperseMessage(userID, targetID, name, message);
        } else if (e.getActionCommand().equals("sendMessageAll")) {
            String message =view.getMessage();
            String targetID =Message.ALL;
            String userID =UserControl.getUserID();
            String name =UserControl.INSTANCE.getFullname();
            disperseMessage(userID, targetID, name, message);
        } else {
            LOGGER.log(Level.WARNING, "UNKNOWN: "+e.getActionCommand());
        }
    }
    
    private void disperseMessage(String userID, String targetID, String name, String message) {
            Message msg =MessageFactory.generateStringMessage(
                    userID, targetID, message);
            Control.INSTANCE.writeMessage(msg);
            
            MessageControl.INSTANCE.addMessage(userID, targetID, name, 
                    msg.getTimestamp(), message);
    }

    void checkMessage(String target, int messageID) {
        if (view.getUserID().equals(target)) {
            UserMessage msg =MessageControl.getMessage(target, messageID);
            view.addMessage(messageID, msg.getUserID().equals(
                    UserControl.getUserID()), msg.getName(), 
                    msg.getTime(), msg.getMessage());
        } else {
            LOGGER.log(Level.INFO, "Not my problem. View userID: "+view.getUserID()+" target: "+target);
        }
    }
}
