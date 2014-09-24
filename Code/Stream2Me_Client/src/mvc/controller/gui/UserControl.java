/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.handlers.MessageFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
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

    protected static void setUser(String userID, String username, String name, String surname, String email, String avatar, String title, String aboutMe) {
        model =new User(userID, username, name, surname, email, avatar, title, aboutMe);;
    }
    
    protected static void clear() {
        model =null;
    }

    protected static boolean hasUser() {
        return model != null;
    }

    public static boolean streamingAudio() {
        return model.isStreamingAudio();
    }

    public static boolean streamingVideo() {
        return model.isStreamingVideo();
    }

    protected static String getUserID() {
        return model.getUserID();
    }

    protected void update(String userID) {
        view.setAvatar(model.getAvatar());
        view.setName(model.getFullname());
        
        view.setID(userID);
        
        ChatControl.INSTANCE.setUserName(model.getFullname());
        
        Control.INSTANCE.writeMessage(MessageFactory.generateRefreshListRequest(userID));
    }
    
    protected void setName(String name) {
        view.setName(name);
    }
    
    protected void setAvatar(String avatar) {
        view.setAvatar(avatar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if (command.equals("streamVideo")) {
            if (model.isStreamingVideo()) {
                Message msg =MessageFactory.generateStreamProperty(
                        UserControl.getUserID(), model.getVideoStreamID(), 
                        model.getVideoStreamName(), false, 0);
                Control.INSTANCE.writeMessage(msg);
                model.setVideoStreamID(null);
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
                Message msg =MessageFactory.generateStreamProperty(
                        UserControl.getUserID(), model.getAudioStreamID(), 
                        model.getAudioStreamName(), false, 1);
                Control.INSTANCE.writeMessage(msg);
                model.setAudioStreamID(null);
                //stop audio
            } else {
                String namn =model.getAudioStreamName() == null ? 
                        "audioStream" : model.getAudioStreamName();
                Message msg =MessageFactory.generateStreamProperty(
                        UserControl.getUserID(), null, namn, true, 1);
                Control.INSTANCE.writeMessage(msg);
                model.setAudioStreamName(namn);
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

    protected String getFullname() {
        return model.getFullname();
    }

    protected User getUser() {
        return model;
    }
}
