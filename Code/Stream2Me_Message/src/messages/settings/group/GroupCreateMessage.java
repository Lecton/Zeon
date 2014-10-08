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
public class GroupCreateMessage extends Message {
    boolean success;
    String error ="";
    String groupID;

    public GroupCreateMessage(boolean success, String groupID) {
        this.success = success;
        this.groupID =groupID;
    }

    public boolean isSuccessful() {
        return success;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public String getError() {
        return error;
    }

    public String getGroupID() {
        return groupID;
    }

    @Override
    public String getMessage() {
        return "Group create response.";
    }

    @Override
    public MessageType handle() {
        return MessageType.settingsGroupCreate;
    }
    
}
