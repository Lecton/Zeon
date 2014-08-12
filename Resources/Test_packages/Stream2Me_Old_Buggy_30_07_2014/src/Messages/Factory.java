/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages;

import Messages.Media.*;
import Messages.UserConnection.*;

/**
 *
 * @author Bernhard
 */
public class Factory {
    public static Login getLogin(String email, String passwordHash) {
        return new Login(email, passwordHash);
    }
    
    public static AudioStream getAudioStream(String Sender, int userID, int targetID) {
        return new AudioStream(Sender, userID, targetID);
    }
    
    public static AudioStream cloneAudioStream(AudioStream as) {
        return new AudioStream(as);
    }
    
    public static VideoStream getVideoStream(String Sender, int userID, int targetID) {
        return new VideoStream(Sender, userID, targetID);
    }
    
    public static VideoStream cloneVideoStream(VideoStream vs) {
        return new VideoStream(vs);
    }

    public static StringMessage getStringMessage(int userID, int targetID, String message) {
        return new StringMessage(userID, targetID, message);
    }

    public static StreamResponse getStreamResponse(int userID, String streamID, boolean response) {
        return new StreamResponse(userID, streamID, response);
    }

    public static Message getUpdateAvatar(int userID, String avatar) {
        return new UpdateAvatar(userID, avatar);
    }
    
    public static StreamProperty getStreamProperty(int userID, int targetID, String streamID, int type, int[] allowed) {
        StreamProperty prop =new StreamProperty(userID, targetID, streamID, type);
        prop.setAllowed(allowed);
        return prop;
    }
}
