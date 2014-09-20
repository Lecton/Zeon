/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.handlers.MessageFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import messages.Message;
import mvc.model.UserMessage;
import mvc.view.generalUI.message.MessagePanel;

/**
 *
 * @author Bernhard
 */
public class ChatControl implements ActionListener {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("sendMessage")) {
            String message =view.getMessage();
            String targetID =view.getUserID();
            String userID =UserControl.getUserID();
            Message msg =MessageFactory.generateStringMessage(userID, targetID, message);
            Control.INSTANCE.writeMessage(msg);
            ContactControl.INSTANCE.addMessage(userID, targetID, msg.getTimestamp(), message);
            String name =UserControl.INSTANCE.getFullname(userID);
            view.addMessage(name, msg.getTimestamp(), message);
        } else if (e.getActionCommand().equals("sendMessageAll")) {
            String message =view.getMessage();
            String targetID =Message.ALL;
            String userID =UserControl.getUserID();
            Message msg =MessageFactory.generateStringMessage(userID, targetID, message);
            Control.INSTANCE.writeMessage(msg);
            UserControl.INSTANCE.addMessage(userID, targetID, msg.getTimestamp(), message);
            String name =UserControl.INSTANCE.getFullname(userID);
            view.addMessage(name, msg.getTimestamp(), message);
        } else {
            System.out.println("UNKNOWN: "+e.getActionCommand());
        }
    }

    void checkMessage(String userID, UserMessage msg) {
        if (view.getUserID().equals(userID)) {
            String name =ContactListControl.INSTANCE.getColleagueFullname(userID);
            view.addMessage(name, msg.getTime(), msg.getMessage());
        }
    }
}
