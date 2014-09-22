/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gui.utils;

import messages.Message;
import messages.StringMessage;
import messages.media.AudioStreamMessage;
import messages.media.StreamPropertyMessage;
import messages.media.StreamResponseMessage;
import messages.media.StreamUpdateMessage;
import messages.media.VideoStreamMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateListMessage;
import messages.update.UpdateNameMessage;
import messages.userConnection.LoginMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;

/**
 *
 * @author Bernhard
 */
public class MessageFactory {
    public static LoginMessage generateLogin(String email, String passwordHash) {
        return new LoginMessage(email, passwordHash);
    }

    public static UpdateAvatarMessage generateUpdateAvatar(String userID, String avatar) {
        UpdateAvatarMessage ua =new UpdateAvatarMessage(userID, avatar);
        ua.setTargetID(Message.ALL);
        return ua;
    }

    public static UpdateNameMessage generateUpdateUsername(String userID, String name, String surname) {
        UpdateNameMessage uu =new UpdateNameMessage(userID, name, surname);
        uu.setTargetID(Message.ALL);
        return uu;
    }

    public static UpdateListMessage generateRefreshListRequest(String userID) {
        return new UpdateListMessage(userID);
    }
    
    public static StringMessage generateStringMessage(String userID, String targetID, String message) {
        return new StringMessage(userID, targetID, message);
    }
    
    public static LogoutMessage generateLogout(String userID) {
        return new LogoutMessage(userID);
    }
     
    public static StreamUpdateMessage generateStreamUpdate(String userID, String targetID, String streamID, String affectedUserID, int action) {
        return new StreamUpdateMessage(userID, targetID, streamID, affectedUserID, action);
    }
    
    public static StreamPropertyMessage generateStreamProperty(String userID, String targetID, String streamID, boolean turnOn) {
        return new StreamPropertyMessage(userID, targetID, streamID, (turnOn?1:0));
    }

    public static StreamResponseMessage generateStreamResponse(String userID, String videoStreamID, boolean response) {
        return new StreamResponseMessage(userID, videoStreamID, response);
    }
    
    public static VideoStreamMessage clone(VideoStreamMessage vs) {
        return new VideoStreamMessage(vs);
    }

    public static AudioStreamMessage clone(AudioStreamMessage as) {
        return new AudioStreamMessage(as);
    }
    
}
