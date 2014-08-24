/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.database;

import Messages.Message;
import java.util.ArrayList;

/**
 *
 * @author Zenadia
 */
public class MessageHistory {
    private int userID;
    private ArrayList<Message> messageHistory;
    
    public int getUserID() {
        return this.userID;
    }
    
    public ArrayList<Message> getMessageHistory(){
        return this.messageHistory;
    }
    
    public void addMessageToHistory(Message msg){
        messageHistory.add(msg);
    }
}
