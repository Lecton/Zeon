/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.settings;

import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 */
public class SettingsMessage extends Message {
    String groupID;
    String groupName;
    String ownerName;
    boolean owner;
    boolean empty;

    public SettingsMessage() {
        empty =true;
    }

    public SettingsMessage(String groupID, String groupName, String ownerName, boolean owner) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.ownerName = ownerName;
        this.owner = owner;
        empty =false;
    }
    
    public boolean isEmpty() {
        return empty;
    }

    @Override
    public String getMessage() {
        return "Settings for user: "+userID;
    }

    @Override
    public MessageType handle() {
        return MessageType.settings;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public boolean isOwner() {
        return owner;
    }
}
