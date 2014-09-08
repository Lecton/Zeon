package server.database.objects;

import Messages.Message;
import java.util.ArrayList;

/**
 *
 * @author Zenadia
 */
public class UserMessageHistory {
    private int userID;
    private ArrayList<Message> messageHistory;
    
    public UserMessageHistory(){
        messageHistory = new ArrayList<>(); 
    }
    
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
