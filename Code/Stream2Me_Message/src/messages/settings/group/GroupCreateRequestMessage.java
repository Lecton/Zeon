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
public class GroupCreateRequestMessage extends Message {
    String groupName;
    String password;

    public GroupCreateRequestMessage(String userID, String groupName, String password) {
        this.userID =userID;
        this.groupName = groupName;
        this.password = password;
    }

    @Override
    public String getMessage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MessageType handle() {
        return MessageType.settingsGroupCreateRequest;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getPassword() {
        return password;
    }
}
