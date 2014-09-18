/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication.handlers;

import messages.Message;
import messages.StringMessage;
import messages.media.AudioStreamMessage;
import messages.media.StreamNotifyMessage;
import messages.media.VideoStreamMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateNameMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import mvc.controller.ContactListControl;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    public boolean handle(Message msg) {
        System.out.println(msg.handle());
        switch (msg.handle()) {
            case console:
                return handleConsole(msg);
            case newUser:
                return handleNewUser((NewUserMessage)msg);
            case updateAvatar:
                return handleUpdateAvatar((UpdateAvatarMessage)msg);
            case updateName:
                return handleUpdateName((UpdateNameMessage)msg);
            case logout:
                return handleRemoveUser((LogoutMessage)msg);

            case string:
                return handleStringMessage((StringMessage)msg);
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
        System.out.println(msg.getMessage());
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
        
        ContactListControl.addPerson(userID, name, surname, email, avatar, title, aboutMe);
        return true;
    }

    private boolean handleUpdateAvatar(UpdateAvatarMessage msg) {
//        Colleague person =Model.INSTANCE.getColleagueList().get(msg.getUserID());
//        if (person != null) {
//            person.setAvatar(msg.getAvatar());
//            UpdateControl.INSTANCE.add(msg.getUserID(), UpdateControl.UPDATEAVATAR, msg.getAvatar());
//        }
        return true;
    }

    private boolean handleUpdateName(UpdateNameMessage msg) {
//        Colleague person =Model.INSTANCE.getColleagueList().get(msg.getUserID());
//        if (person != null) {
//            person.setName(msg.getName());
//            person.setSurname(msg.getSurname());
//            UpdateControl.INSTANCE.add(msg.getUserID(), UpdateControl.UPDATENAME, person.getFullname());
//        }
        return true;
    }

    private boolean handleRemoveUser(LogoutMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean handleStringMessage(StringMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean handleStreamNotification(StreamNotifyMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean handleAudioStream(AudioStreamMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean handleVideoStream(VideoStreamMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}