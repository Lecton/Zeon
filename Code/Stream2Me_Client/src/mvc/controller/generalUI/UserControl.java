/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller.generalUI;

import communication.handlers.MessageFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import mvc.controller.generalUI.message.ChatControl;
import mvc.controller.generalUI.contacts.ContactListControl;
import mvc.controller.Control;
import mvc.model.User;
import mvc.view.generalUI.UserPanel;

/**
 *
 * @author Bernhard
 */
public class UserControl implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(UserControl.class.getName());
    
    public static UserControl INSTANCE =new UserControl();
    private static UserPanel view;
    private static User model;

    public static void register(UserPanel viewUser) {
        view = viewUser;
    }

    public static void setUser(String userID, String username, String name, String surname, String email, String avatar, String title, String aboutMe) {
        model =new User(userID, username, name, surname, email, avatar, title, aboutMe);;
    }
    
    public static void clear() {
        model =null;
        view.clear();
    }

    public static boolean hasUser() {
        return model != null;
    }

    public static boolean streamingAudio() {
        return model.isStreamingAudio();
    }

    public static boolean streamingVideo() {
        return model.isStreamingVideo();
    }

    public static String getUserID() {
        return model.getUserID();
    }

    public void update() {
        view.setAvatar(model.getAvatar());
        view.setUsersName(model.getFullname());
        
        view.setTitle(model.getTitle());
        
        ChatControl.INSTANCE.setUserName(model.getFullname());
    }
    
    public void setName(String name) {
        view.setName(name);
    }
    
    public void setAvatar(String avatar) {
        model.setAvatar(avatar);
        view.setAvatar(avatar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if (command.equals("streamVideo")) {
            if (model.isStreamingVideo()) {
                stopVideo();
                //stop video
            } else {
                String namn =model.getVideoStreamName() == null ? 
                        "videoStream" : model.getVideoStreamName();
                Message msg =MessageFactory.generateStreamProperty(
                        UserControl.getUserID(), null, namn, true, 0);
                Control.INSTANCE.writeMessage(msg);
                model.setVideoStreamName(namn);
                
                //start video
            }
        } else if (command.equals("streamAudio")) {
            if (model.isStreamingAudio()) {
                stopAudio();
                //stop audio
            } else {
                String namn =model.getAudioStreamName() == null ? 
                        "audioStream" : model.getAudioStreamName();
                Message msg =MessageFactory.generateStreamProperty(
                        UserControl.getUserID(), null, namn, true, 1);
                Control.INSTANCE.writeMessage(msg);
                model.setAudioStreamName(namn);
                
                ContactListControl.resetAudioReceivers();
                //start audio
            }
            
            
        } else if (command.equals("viewProfile")) {
            GUIControl.changeContent(2, model.getUserID(), model.getFullname());
            LOGGER.log(Level.INFO, "View User Profile");
        } else if (command.equals("viewMessages")) {
            model.setMessage(false);
            GUIControl.changeContent(3, model.getUserID(), model.getFullname());
            LOGGER.log(Level.INFO, "View User Messages");
            
            
        } else {
            LOGGER.log(Level.WARNING, "UNKNOWN: "+command);
        }
    }

    public String getFullname() {
        return model.getFullname();
    }

    public User getUser() {
        return model;
    }
    
    public void stopAudio() {
        if (model.isStreamingAudio()) {
            List<String> users =ContactListControl.resetAudioReceivers();
            for (String uID: users) {
                Control.INSTANCE.writeMessage(MessageFactory
                        .generateStreamUpdate(model.getUserID(), 
                                "SERVER", model.getAudioStreamID(), uID, 1, false));
            }

            Message msg =MessageFactory.generateStreamProperty(
                    UserControl.getUserID(), model.getAudioStreamID(), 
                    model.getAudioStreamName(), false, 1);
            Control.INSTANCE.writeMessage(msg);
            model.setAudioStreamID(null);
        }
    }

    public void stopVideo() {
        if (model.isStreamingVideo()) {
            List<String> users =ContactListControl.resetVideoReceivers();

            for (String uID: users) {
                Control.INSTANCE.writeMessage(MessageFactory
                        .generateStreamUpdate(model.getUserID(), 
                                "SERVER", model.getVideoStreamID(), uID, 0, false));
            }

            Message msg =MessageFactory.generateStreamProperty(
                    UserControl.getUserID(), model.getVideoStreamID(), 
                    model.getVideoStreamName(), false, 0);
            Control.INSTANCE.writeMessage(msg);

            model.setVideoStreamID(null);
        }
    }

    public void setStreamName(String name, int type) {
        if (name == null || (name != null && name.isEmpty())) {
            name =model.getFullname();
        }
        if (type == 0) {
            model.setVideoStreamName(name);
        } else if (type == 1) {
            model.setAudioStreamName(name);
        } else {
            LOGGER.log(Level.WARNING, "Unknown setName action type");
        }
    }
}
