/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.settings.group;

import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 */
public class GroupListRequestMessage extends Message {

    public GroupListRequestMessage(String userID) {
        this.userID =userID;
    }

    @Override
    public String getMessage() {
        return "Requesting group list.";
    }

    @Override
    public MessageType handle() {
        return MessageType.settingsGroupListRequest;
    }
}
