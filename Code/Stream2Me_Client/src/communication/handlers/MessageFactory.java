/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication.handlers;

import java.util.logging.Logger;
import messages.Message;
import messages.StringMessage;
import messages.media.StreamPropertyMessage;
import messages.media.StreamResponseMessage;
import messages.media.StreamUpdateMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateListMessage;
import messages.update.UpdateProfileMessage;
import messages.userConnection.LoginMessage;
import messages.userConnection.LogoutMessage;

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

    public static UpdateProfileMessage generateUpdateName(String userID, 
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
     
    public static StreamUpdateMessage generateStreamUpdate(String userID, String targetID, String streamID, String affectedUserID, int action) {
        return new StreamUpdateMessage(userID, targetID, streamID, affectedUserID, action);
    }
    
    public static StreamPropertyMessage generateStreamProperty(String userID, String targetID, String streamID, boolean turnOn) {
        return new StreamPropertyMessage(userID, targetID, streamID, (turnOn?1:0));
    }

    public static StreamResponseMessage generateStreamResponse(String userID, String videoStreamID, boolean response) {
        return new StreamResponseMessage(userID, videoStreamID, response);
    }
}