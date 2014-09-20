/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.person;

import java.util.ArrayList;
import java.util.List;
import mvc.model.UserMessage;

/**
 *
 * @author Bernhard
 */
public class MessageUser extends Notifier {
    List<UserMessage> messageHist =new ArrayList<UserMessage>();

    public MessageUser(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        super(userID, name, surname, email, avatar, title, aboutMe);
    }
    
    public UserMessage addMessage(String userID, String targetID, String time, String message) {
        UserMessage um =new UserMessage(userID, targetID, time, message);
        messageHist.add(um);
        return um;
    }
    
    public List<UserMessage> getMessageHistory() {
        return messageHist;
    }
}
