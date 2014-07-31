/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages.Update;

import Messages.Message;

/**
 *
 * @author Bernhard
 */
public class UpdateList extends Message {
    
    public UpdateList(int userID) {
        this.userID =userID;
        this.targetID =Message.SERVER;
    }

    @Override
    public String getMessage() {
        return "Contact list request";
    }

    @Override
    public MessageType handle() {
        return MessageType.updateList;
    }
    
}
