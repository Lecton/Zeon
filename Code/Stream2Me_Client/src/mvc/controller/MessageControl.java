/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import mvc.model.MessageList;
import mvc.model.UserMessage;

/**
 *
 * @author Bernhard
 */
public class MessageControl {
    private final static Logger LOGGER = Logger.getLogger(MessageControl.class.getName());
    
    protected static MessageControl INSTANCE =new MessageControl();
    private static MessageList model =new MessageList();

    public static boolean acceptMessage(String userID, String targetID, String timestamp, String message) {
        String name =ContactListControl.INSTANCE.getColleagueFullname(userID);
        int result =model.addMessage(userID, targetID, name, timestamp, 
                message, targetID.equals(Message.ALL));
        LOGGER.log(Level.INFO, "Message received from: "+name+". Message index: "+result);
        if (result != -1) {
            String target =(targetID.equals(Message.ALL) ? "default" : userID);
            UpdateControl.INSTANCE.add(target, UpdateControl.STRINGMESSAGE, result);
        }
        return result != -1;
    }

    protected static UserMessage getMessage(String target, int messageID) {
        return model.getMessage(target, messageID);
    }
    
    public boolean addMessage(String userID, String targetID, String name, String timestamp, String message) {
        int result =model.addMessage(userID, targetID, name , timestamp, 
                message, targetID.equals(Message.ALL), targetID);
        if (result != -1) {
            String target =(targetID.equals(Message.ALL) ? "default" : targetID);
            UpdateControl.INSTANCE.add(target, UpdateControl.STRINGMESSAGE, result);
        }
        return result != -1;
    }

    protected static void clear() {
        model =new MessageList();
    }
    
    protected void addMessageHist(String userID) {
        model.addMessageHistory(userID);
    }
}
