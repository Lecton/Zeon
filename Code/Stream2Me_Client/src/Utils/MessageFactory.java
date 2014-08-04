/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

/**
 *
 * @author Bernhard
 */
public class MessageFactory {
    public static Messages.UserConnection.Login generateLogin(String email, String passwordHash) {
        return new Messages.UserConnection.Login(email, passwordHash);
    }

    public static Messages.Message generateUpdateAvatar(int userID, String avatar) {
        Messages.Update.UpdateAvatar ua =new Messages.Update.UpdateAvatar(userID, avatar);
        ua.setTargetID(Messages.Message.ALL);
        return ua;
    }

    public static Messages.Update.UpdateUsername generateUpdateUsername(int userID, String username) {
        Messages.Update.UpdateUsername uu =new Messages.Update.UpdateUsername(userID, username);
        uu.setTargetID(Messages.Message.ALL);
        return uu;
    }

    public static Messages.Update.UpdateList generateRefreshListRequest(int userID) {
        return new Messages.Update.UpdateList(userID);
    }
    
    public static Messages.StringMessage generateStringMessage(int userID, int targetID, String username, String message) {
        return new Messages.StringMessage(userID, targetID, username, message);
    }
    
    public static Messages.UserConnection.Logout generateLogout(int userID) {
        return new Messages.UserConnection.Logout(userID);
    }
     
    public static Messages.Media.StreamUpdate generateStreamUpdate(int userID, int targetID, String streamID, int affectedUserID, int action) {
        return new Messages.Media.StreamUpdate(userID, targetID, streamID, affectedUserID, action);
    }
    
    public static Messages.Media.StreamProperty generateStreamProperty(int userID, int targetID, String streamID, boolean turnOn) {
        return new Messages.Media.StreamProperty(userID, targetID, streamID, (turnOn?1:0));
    }

    public static Messages.Media.StreamResponse generateStreamResponse(int userID, String videoStreamID, boolean response) {
        return new Messages.Media.StreamResponse(userID, videoStreamID, response);
    }
            
            
            
            
    
    public static Messages.Media.VideoStream clone(Messages.Media.VideoStream vs) {
        return new Messages.Media.VideoStream(vs);
    }

    public static Messages.Media.AudioStream clone(Messages.Media.AudioStream as) {
        return new Messages.Media.AudioStream(as);
    }
    
    
    
    
    
    public static Client.Colleague generateColleague(Messages.UserConnection.NewUser nu) {
        return new Client.Colleague(nu.getUserID(), nu.getUsername(), nu.getEmail(), nu.getAvatar());
    }
}
