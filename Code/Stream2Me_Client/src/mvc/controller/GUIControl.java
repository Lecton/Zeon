/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.handlers.MessageFactory;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.logging.Logger;
import javax.swing.JComponent;
import mvc.model.Colleague;
import mvc.view.generalUI.GUI;
import mvc.view.generalUI.ProfilePanel;
import mvc.view.generalUI.message.MessagePanel;

/**
 *
 * @author Bernhard
 */
public class GUIControl implements WindowListener {
    private final static Logger LOGGER = Logger.getLogger(GUIControl.class.getName());
    
    private static GUI view;
    
    public static void register(GUI ui) {
        view =ui;
    }
    
    protected static void clear() {
        UpdateControl.clear();
        ContactListControl.clear();
        UserControl.clear();
        ChatControl.clear();
        ProfileControl.clear();
    }
    
    protected static void changeContent(int type, String userID, String name) {
        JComponent target =view.changeContent(type, userID, name);
        UpdateControl.INSTANCE.add(target, UpdateControl.UPDATECONTENT, type);
    }
    
    protected static void hideStreamAcceptors(String userID) {
        view.hideStreamAcceptors(userID);
    }
    
    protected static void updateStreamAcceptors(String userID) {
        Colleague person =ContactListControl.INSTANCE.getColleague(userID);
        view.updateStreamAcceptors(person.getVideoStream() != null, person.acceptedVideo(), person.getAudioStream() != null, person.acceptedAudio(), userID);
    }
    
    protected static void checkStreamAcceptors(String userID) {
        Colleague person =ContactListControl.INSTANCE.getColleague(userID);
        view.setStreamAcceptors(person.getVideoStream() != null, person.acceptedVideo(), person.getAudioStream() != null, person.acceptedAudio(), userID);
    }

    protected static void updateContent(Object target, int type) {
        switch (type) {
            case 0: //show contact profile
                ProfileControl.updateContact((ProfilePanel)target);
                break;
            case 1: //show contact messages
                ChatControl.updateContent((MessagePanel)target);
//                Message pp =(ProfilePanel)target;
//                ContactControl.updateContent(pp);
//                return messages;
                break;
            case 2: //show user profile
                ProfileControl.updateUser((ProfilePanel)target);
                break;
            case 3: //show group messages
                ChatControl.updateContent((MessagePanel)target);
//                cl.show(content, "message");
                break;
            default: //show group messages
                ChatControl.updateContent((MessagePanel)target);
//                cl.show(content, "message");
                break;
        }
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected static void toggleStreamVideo(boolean onOrOff) {
        view.toggleStreamVideo(onOrOff);
    }

    protected static void toggleStreamAudio(boolean onOrOff) {
        view.toggleStreamAudio(onOrOff);
    }

    static void setEnableStreamVideo(boolean enable) {
        view.setEnableStreamVideo(enable);
    }

    public static void logout() {
        Control.INSTANCE.writeMessage(MessageFactory.generateLogout(UserControl.getUserID()));
        Control.INSTANCE.initiate(0);
    }

    public static void settings() {
        StreamControl.INSTANCE.stop();
        Control.INSTANCE.initiate(3);
    }

    static void reset() {
        UpdateControl.clear();
        ContactListControl.clear();
        ChatControl.clear();
        ProfileControl.clear();
        view.changeContent(3, null, UserControl.INSTANCE.getFullname());
    }

    @Override
    public void windowOpened(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Control.INSTANCE.close();
    }

    @Override
    public void windowClosed(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected static void requestUpdateList() {
        ContactListControl.clear();
        Control.INSTANCE.writeMessage(MessageFactory.generateRefreshListRequest(UserControl.getUserID()));
    }
}
