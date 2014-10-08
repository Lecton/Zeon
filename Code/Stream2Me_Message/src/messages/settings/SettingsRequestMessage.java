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
public class SettingsRequestMessage extends Message {

    public SettingsRequestMessage(String userID) {
        this.userID =userID;
    }

    @Override
    public String getMessage() {
        return "Requesting settings for user: "+userID;
    }

    @Override
    public MessageType handle() {
        return MessageType.settingsRequest;
    }
    
}
