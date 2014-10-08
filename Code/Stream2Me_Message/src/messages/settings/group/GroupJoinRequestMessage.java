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
public class GroupJoinRequestMessage extends Message {
    String groupID;
    String password;

    public GroupJoinRequestMessage(String groupID, String userID, String password) {
        this.groupID = groupID;
        this.userID =userID;
        this.password = password;
    }

    @Override
    public String getMessage() {
        return "Group Join request";
    }

    @Override
    public MessageType handle() {
        return MessageType.settingsGroupJoinRequest;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getPassword() {
        return password;
    }
}
