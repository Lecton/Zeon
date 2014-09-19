/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.handlers.MessageFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import mvc.model.User;
import mvc.view.generalUI.UserPanel;

/**
 *
 * @author Bernhard
 */
public class UserControl implements ActionListener {
    public static UserControl INSTANCE =new UserControl();
    private static UserPanel view;
    private static User model;

    public static void register(UserPanel viewUser) {
        view = viewUser;
    }

    protected static void setUser(String userID, String username, String name, String surname, String email, String avatar, String title, String aboutMe) {
        model =new User(userID, username, name, surname, email, avatar, title, aboutMe);
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
                //stop video
                model.setStreamingVideo(false);
            } else {
                //start video
                model.setStreamingVideo(true);
            }
        } else if (command.equals("streamAudio")) {
            if (model.isStreamingAudio()) {
                //stop audio
                model.setStreamingAudio(false);
            } else {
                //start audio
                model.setStreamingAudio(true);
            }
        } else if (command.equals("viewProfile")) {
            GUIControl.changeContent(2, model.getUserID());
            System.out.println("View User Profile");
        } else if (command.equals("viewMessages")) {
            GUIControl.changeContent(3, model.getUserID());
            System.out.println("View User Messages");
        } else {
            System.out.println("UNKNOWN: "+command);
        }
    }
}
