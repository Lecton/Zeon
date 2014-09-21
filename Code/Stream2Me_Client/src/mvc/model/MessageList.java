/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class MessageList {
    private final static Logger LOGGER = Logger.getLogger(MessageList.class.getName());
    
    Map<String, List<UserMessage>> messageHistories;

    public MessageList() {
        messageHistories =new HashMap<>();
        messageHistories.put("default", new ArrayList<UserMessage>());
    }

    public int addMessage(String userID, String targetID, String name, String timestamp, String message, boolean group) {
        String key =group? "default" : userID;
        List<UserMessage> history =messageHistories.get(key);
        if (history != null) {
            UserMessage msg =new UserMessage(userID, targetID, name, timestamp, message);
            history.add(msg);
            return history.indexOf(msg);
        } else {
            return -1;
        }
    }

    public int addMessage(String userID, String targetID, String name, String timestamp, String message, boolean group, String key) {
        key =group? "default" : key;
        List<UserMessage> history =messageHistories.get(key);
        if (history != null) {
            UserMessage msg =new UserMessage(userID, targetID, name, timestamp, message);
            history.add(msg);
            return history.indexOf(msg);
        } else {
            return -1;
        }
    }

    public boolean addMessageHistory(String userID) {
        if (messageHistories.containsKey(userID)) {
            Logger.getLogger(MessageList.class.getName()).log(Level.WARNING, "Message history alread exists for user "+userID);
            return false;
        } else {
            messageHistories.put(userID, new ArrayList<UserMessage>());
            return true;
        }
    }

    public UserMessage getMessage(String key, int index) {
        List<UserMessage> hist =messageHistories.get(key);
        if (hist == null) {
            Logger.getLogger(MessageList.class.getName()).log(Level.SEVERE, "Message history not found");
            return null;
        } else {
            return hist.get(index);
        }
    }
}
