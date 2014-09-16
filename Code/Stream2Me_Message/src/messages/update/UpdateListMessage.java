/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages.update;

import messages.Message;

/**
 *
 * @author Bernhard
 */
public class UpdateListMessage extends Message {
    
    public UpdateListMessage(String userID) {
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
