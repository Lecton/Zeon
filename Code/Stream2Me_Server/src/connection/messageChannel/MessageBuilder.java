/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messageChannel;

import core.database.objects.User;
import messages.media.creation.StreamPropertyMessage;
import messages.media.creation.StreamTerminateMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.NewUserMessage;

/**
 *
 * @author Bernhard
 */
public class MessageBuilder {
    public static GreetingMessage generateGreeting(User person, boolean success, String response) {
        GreetingMessage msg =new GreetingMessage(success);
        if (success) {
            msg.setUserID(person.getUserID());
            msg.setUsername(person.getUsername());
            msg.setName(person.getName());
            msg.setSurname(person.getSurname());
            msg.setEmail(person.getEmail());
            msg.setTitle(person.getTitle());
            msg.setAboutMe(person.getAboutMe());
            msg.setAvatar(person.getAvatar());
        }
        msg.setResponse(response);
        return msg;
    }

    public static NewUserMessage generateNewUser(User person, String targetID) {
        NewUserMessage msg =new NewUserMessage(person.getUserID(), person.getName()
                , person.getSurname(), person.getEmail(), person.getAvatar()
                , person.getTitle(), person.getAboutMe());
        if (targetID != null) {
            msg.setTargetID(targetID);
        }
        return msg;
    }

    public static StreamPropertyMessage generateStreamResponse(String userID, String streamID, String streamName, int type, boolean success) {
        return new StreamPropertyMessage(streamID, userID, streamName, type, success);
    }

    public static StreamTerminateMessage generateStreamTerminate(String userID, String streamID, int type) {
        return new StreamTerminateMessage(streamID, userID, type);
    }
}