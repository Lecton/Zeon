/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication.handlers;

import java.util.logging.Logger;
import messages.Message;
import messages.StringMessage;
import messages.media.creation.StreamPropertyRequestMessage;
import messages.media.communication.StreamResponseMessage;
import messages.media.communication.StreamUpdateMessage;
import messages.settings.group.GroupJoinRequestMessage;
import messages.settings.group.GroupListRequestMessage;
import messages.settings.SettingsRequestMessage;
import messages.settings.group.GroupCreateMessage;
import messages.settings.group.GroupCreateRequestMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateListMessage;
import messages.update.UpdateProfileMessage;
import messages.userConnection.LoginMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.registration.CheckEmailMessage;
import messages.userConnection.registration.CheckUsernameMessage;
import messages.userConnection.registration.RegisterMessage;

/**
 *
 * @author Bernhard
 */
public class MessageFactory {
    private final static Logger LOGGER = Logger.getLogger(MessageFactory.class.getName()); 
    
    public static LoginMessage generateLogin(String email, String password) {
        return new LoginMessage(email, password);
    }

    public static UpdateAvatarMessage generateUpdateAvatar(String userID, String avatar) {
        UpdateAvatarMessage ua =new UpdateAvatarMessage(userID, avatar);
        ua.setTargetID(Message.ALL);
        return ua;
    }

    public static UpdateProfileMessage generateUpdateProfile(String userID, 
            String name, String surname, String email, String title, String aboutMe) {
        UpdateProfileMessage uu =new UpdateProfileMessage(userID, name, 
                surname, email, title, aboutMe);
        uu.setTargetID(Message.ALL);
        return uu;
    }

    public static UpdateListMessage generateRefreshListRequest(String userID) {
        return new UpdateListMessage(userID);
    }
    
    public static StringMessage generateStringMessage(String userID, String targetID, String message) {
        targetID =targetID.equals(userID) ? Message.ALL : targetID;
        return new StringMessage(userID, targetID, message);
    }
    
    public static LogoutMessage generateLogout(String userID) {
        return new LogoutMessage(userID);
    }
     
    public static StreamUpdateMessage generateStreamUpdate(String userID, String targetID, String streamID, String affectedUserID, int type, boolean action) {
        return new StreamUpdateMessage(userID, targetID, streamID, affectedUserID, type, action);
    }
    
    public static StreamPropertyRequestMessage generateStreamProperty(String userID, String streamID, String streamName, boolean turnOn, int type) {
        return new StreamPropertyRequestMessage(userID, streamName, streamID, turnOn, type);
    }

    public static StreamResponseMessage generateStreamResponse(String userID, String videoStreamID, boolean response) {
        return new StreamResponseMessage(userID, videoStreamID, response);
    }
    
    public static CheckUsernameMessage generateCheckUsername(String username) {
        return new CheckUsernameMessage(username);
    }

    public static CheckEmailMessage generateCheckEmail(String email) {
        return new CheckEmailMessage(email);
    }

    public static RegisterMessage generateRegistration(String username, String name, String surname, String email, String pw) {
        return new RegisterMessage(username, name, surname, email, pw);
    }

    public static SettingsRequestMessage generateSettingsRequestMessage(String userID) {
        return new SettingsRequestMessage(userID);
    }

    public static GroupListRequestMessage generateSettingsGroupListRequest(String userID) {
        return new GroupListRequestMessage(userID);
    }

    public static GroupJoinRequestMessage generateSettingsGroupJoin(String groupID, String userID, String password) {
        return new GroupJoinRequestMessage(groupID, userID, password);
    }

    public static GroupCreateRequestMessage generateSettingsCreateGroup(String userID, String groupName, String password) {
        return new GroupCreateRequestMessage(userID, groupName, password);
    }
}