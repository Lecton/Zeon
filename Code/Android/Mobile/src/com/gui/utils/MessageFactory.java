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

    public static UpdateAvatarMessage generateUpdateAvatar(int userID, String avatar) {
        UpdateAvatarMessage ua =new UpdateAvatarMessage(userID, avatar);
        ua.setTargetID(Message.ALL);
        return ua;
    }

    public static UpdateNameMessage generateUpdateUsername(int userID, String name, String surname) {
        UpdateNameMessage uu =new UpdateNameMessage(userID, name, surname);
        uu.setTargetID(Message.ALL);
        return uu;
    }

    public static UpdateListMessage generateRefreshListRequest(int userID) {
        return new UpdateListMessage(userID);
    }
    
    public static StringMessage generateStringMessage(int userID, int targetID, String username, String message) {
        return new StringMessage(userID, targetID, username, message);
    }
    
    public static LogoutMessage generateLogout(int userID) {
        return new LogoutMessage(userID);
    }
     
    public static StreamUpdateMessage generateStreamUpdate(int userID, int targetID, String streamID, int affectedUserID, int action) {
        return new StreamUpdateMessage(userID, targetID, streamID, affectedUserID, action);
    }
    
    public static StreamPropertyMessage generateStreamProperty(int userID, int targetID, String streamID, boolean turnOn) {
        return new StreamPropertyMessage(userID, targetID, streamID, (turnOn?1:0));
    }

    public static StreamResponseMessage generateStreamResponse(int userID, String videoStreamID, boolean response) {
        return new StreamResponseMessage(userID, videoStreamID, response);
    }
    
    public static VideoStreamMessage clone(VideoStreamMessage vs) {
        return new VideoStreamMessage(vs);
    }

    public static AudioStreamMessage clone(AudioStreamMessage as) {
        return new AudioStreamMessage(as);
    }
    
}
