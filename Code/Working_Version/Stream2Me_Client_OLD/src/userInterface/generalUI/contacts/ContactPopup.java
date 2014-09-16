/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface.generalUI.contacts;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import messages.Message;
import userInterface.generalUI.GUI;
import utils.MessageFactory;


/**
 *
 * @author Bernhard
 */
public class ContactPopup extends JPopupMenu {
    JMenuItem viewProfile, showMessages;
    JMenuItem audioControl, videoControl;
    final ContactProfile parent;
    
    public ContactPopup(ContactProfile parent) {
        this.parent =parent;
        
        showMessages =new JMenuItem("Show Messages");
        viewProfile =new JMenuItem("View Profile");
        
        showMessages.addActionListener(new showMessageEvent());
        viewProfile.addActionListener(new showProfileEvent());
        
        add(viewProfile);
        add(showMessages);
        
        audioControl =new JMenuItem("No audio stream");
        audioControl.addActionListener(new audioAction());
        audioControl.setEnabled(false);
        
        videoControl =new JMenuItem("No video stream");
        videoControl.addActionListener(new videoAction());
        videoControl.setEnabled(false);
        
        add(audioControl);
        add(videoControl);
    }

    @Override
    public void show(Component invoker, int x, int y) {
        super.show(invoker, x, y);
        
        if (parent.getParent().getUserInterface().isAudioStream()) {
            audioControl.setEnabled(true);
            if (parent.isAudioOut()) {
                audioControl.setText("Remove from audio");
            } else {
                audioControl.setText("Invite to audio");
            }
        } else {
            audioControl.setText("No audio stream");
            audioControl.setEnabled(false);
        }
        
        if (parent.getParent().getUserInterface().isVideoStream()) {
            videoControl.setEnabled(true);
            if (parent.isVideoOut()) {
                videoControl.setText("Remove from video");
            } else {
                videoControl.setText("Invite to video");
            }
        } else {
            videoControl.setText("No audio stream");
            videoControl.setEnabled(false);
        }
    }
    
    class showMessageEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.showMessages();
        }
        
    }
    class showProfileEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            parent.showProfile();
        }
    }
    
    class audioAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int action;
            if (parent.isAudioOut()) {
                action =0;
            } else {
                action =1;
            }
            GUI UI =parent.getParent().getUserInterface();
            UI.getConnection().writeSafe(
                    MessageFactory.generateStreamUpdate(UI.getUserID(), 
                            Message.SERVER, UI.getAudioStreamID(),
                            parent.getUserID(), action));
            
            parent.setAudioOut(action == 1);
        }
    }
    
    private class videoAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int action;
            if (parent.isVideoOut()) {
                action =0;
            } else {
                action =1;
            }
            GUI UI =parent.getParent().getUserInterface();
            UI.getConnection().writeSafe(
                    MessageFactory.generateStreamUpdate(UI.getUserID(), 
                            Message.SERVER, UI.getVideoStreamID(),
                            parent.getUserID(), action));
            
            parent.setVideoOut(action == 1);
        }
    }
}
