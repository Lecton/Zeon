/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.StringMessage;
import messages.media.AudioStreamMessage;
import messages.media.communication.StreamNotifyMessage;
import messages.media.VideoStreamMessage;
import messages.media.creation.StreamPropertyMessage;
import messages.media.creation.StreamTerminateMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateProfileMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import mvc.controller.ContactListControl;
import mvc.controller.MessageControl;
import mvc.controller.StreamControl;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    private final static Logger LOGGER = Logger.getLogger(MessageHandler.class.getName()); 
    
    public boolean handle(Message msg) throws UnsupportedOperationException {
//        LOGGER.log(Level.INFO, msg.handle()+"");
        switch (msg.handle()) {
            case console:
                return handleConsole(msg);
            case newUser:
                return handleNewUser((NewUserMessage)msg);
            case logout:
                return handleRemoveUser((LogoutMessage)msg);
                
            case updateAvatar:
                return handleUpdateAvatar((UpdateAvatarMessage)msg);
            case updateProfile:
                return handleUpdateProfile((UpdateProfileMessage)msg);

            case string:
                return handleStringMessage((StringMessage)msg);
                
            case streamProperty:
                return handleStreamProperty((StreamPropertyMessage)msg);
            case streamTerminate:
                return handleStreamTerminate((StreamTerminateMessage)msg);
            case streamNotify:
                return handleStreamNotification((StreamNotifyMessage)msg);
            case auido:
                return handleAudioStream((AudioStreamMessage)msg);
            case video:
                return handleVideoStream((VideoStreamMessage)msg);
            default:
                break;
        }
        return false;
    }
    
    boolean handleConsole(Message msg) {
        LOGGER.log(Level.INFO, msg.getMessage());
        return true;
    }

    private boolean handleNewUser(NewUserMessage msg) {
        String userID =msg.getUserID();
        String name =msg.getName();
        String surname =msg.getSurname();
        String email =msg.getEmail();
        String avatar =msg.getAvatar();
        String title =msg.getTitle();
        String aboutMe =msg.getAboutMe();
        
        ContactListControl.addColleague(userID, name, surname, email, avatar, title, aboutMe);
        return true;
    }

    private boolean handleRemoveUser(LogoutMessage msg) {
        ContactListControl.removeColleague(msg.getUserID());
        return true;
    }

    private boolean handleUpdateAvatar(UpdateAvatarMessage msg) {
        ContactListControl.updateContact(msg.getUserID(), null, null, msg.getAvatar(), null, null);
//        Colleague person =Model.INSTANCE.getColleagueList().get(msg.getUserID());
//        if (person != null) {
//            person.setAvatar(msg.getAvatar());
//            UpdateControl.INSTANCE.add(msg.getUserID(), UpdateControl.UPDATEAVATAR, msg.getAvatar());
//        }
        return true;
    }

    private boolean handleUpdateProfile(UpdateProfileMessage msg) {
        ContactListControl.updateContact(msg.getUserID(), msg.getName(), msg.getSurname(), null, msg.getTitle(), msg.getAboutMe());
//        Colleague person =Model.INSTANCE.getColleagueList().get(msg.getUserID());
//        if (person != null) {
//            person.setName(msg.getName());
//            person.setSurname(msg.getSurname());
//            UpdateControl.INSTANCE.add(msg.getUserID(), UpdateControl.UPDATENAME, person.getFullname());
//        }
        return true;
    }

    private boolean handleStringMessage(StringMessage msg) {
        String userID =msg.getUserID();
        String targetID =msg.getTargetID();
        String message =msg.getMessage();
        String timestamp =msg.getTimestamp();
        MessageControl.acceptMessage(userID, targetID, timestamp, message);
        return true;
    }

    private boolean handleStreamNotification(StreamNotifyMessage msg) {
        ContactListControl.acceptStream(msg.getStreamID(), msg.getUserID(), msg.getType(), msg.getAction());
        return true;
    }

    private boolean handleAudioStream(AudioStreamMessage msg) {
        StreamControl.handleAudioData(msg.getStreamID(), msg.getBuffer());
        return true;
    }

    private boolean handleVideoStream(VideoStreamMessage msg) {
        StreamControl.handleVideoData(msg.getStreamID(), msg.getImg());
        return true;
    }

    private boolean handleStreamProperty(StreamPropertyMessage msg) {
        StreamControl.handleStreamProperty(msg);
        return true;
    }

    private boolean handleStreamTerminate(StreamTerminateMessage msg) {
        StreamControl.handleStreamTerminate(msg);
        return true;
    }
    
    public List<Message> getPrivatePool() {
        return new ArrayList<>();
    }
    
    public void empty(List<Message> pool) {
        for (Message msg: pool) {
            handle(msg);
        }
    }
}