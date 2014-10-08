/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messages.settings.group;

import java.util.Map;
import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 */
public class GroupListMessage extends Message {
    Map<String, String> groups;
    
    public GroupListMessage(Map<String, String> groups) {
        this.groups = groups;
    }

    @Override
    public String getMessage() {
        return "Group List";
    }

    @Override
    public MessageType handle() {
        return MessageType.settingsGroupList;
    }

    public Map<String, String> getGroups() {
        return groups;
    }
}
