/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Bernhard
 */
public class Disconnect extends Message {

    public Disconnect(int userID, String Sender) {
        this.userID = userID;
        this.Sender = Sender;
        this.Title = "User left";
    }
    
    @Override
    public String getMessage() {
        return userID + " left the chat.";
    }

    @Override
    public Message.MessageType handle() {
        return Message.MessageType.disconnect;
    }
}
