/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.contacts;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import mvc.controller.ContactControl;
import mvc.controller.UserControl;


/**
 *
 * @author Bernhard
 */
public class ContactPopup extends JPopupMenu {
    JMenuItem viewProfile, viewMessages;
    JMenuItem audioControl, videoControl;
    final ContactProfile parent;
    
    public ContactPopup(ContactProfile parent) {
        this.parent =parent;
        
        viewMessages =new JMenuItem("View Messages");
        viewProfile =new JMenuItem("View Profile");
        
        viewMessages.setActionCommand("viewMessages");
        viewProfile.setActionCommand("viewProfile");
        
        viewMessages.addActionListener(ContactControl.INSTANCE);
        viewProfile.addActionListener(ContactControl.INSTANCE);
        
        add(viewProfile);
        add(viewMessages);
        
        audioControl =new JMenuItem("No audio stream");
        audioControl.addActionListener(new audioAction());
        audioControl.setVisible(false);
        
        videoControl =new JMenuItem("No video stream");
        videoControl.addActionListener(new videoAction());
        videoControl.setVisible(false);
        
        add(audioControl);
        add(videoControl);
    }
    
    public String getUserID() {
        return parent.getUserID();
    }

    @Override
    public void show(Component invoker, int x, int y) {
        super.show(invoker, x, y);
        if (UserControl.streamingAudio()) {
            audioControl.setVisible(true);
            if (ContactControl.receivingAudio(parent.getUserID())) {
                audioControl.setText("Remove from audio");
            } else {
                audioControl.setText("Invite to audio");
            }
        } else {
            audioControl.setVisible(false);
        }
        
        if (UserControl.streamingVideo()) {
            videoControl.setVisible(true);
            if (ContactControl.receivingVideo(parent.getUserID())) {
                videoControl.setText("Remove from video");
            } else {
                videoControl.setText("Invite to video");
            }
        } else {
            videoControl.setVisible(false);
        }
        pack();
    }
    
    class showMessageEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            parent.showMessages();
        }
        
    }
    class showProfileEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            parent.showProfile();
        }
    }
    
    class audioAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int action;
//            if (parent.isAudioOut()) {
//                action =0;
//            } else {
//                action =1;
//            }
//            GUI UI =parent.getParent().getUserInterface();
//            UI.getConnection().writeSafe(
//                    MessageFactory.generateStreamUpdate(UI.getUserID(), 
//                            Message.SERVER, UI.getAudioStreamID(),
//                            parent.getUserID(), action));
//            
//            parent.setAudioOut(action == 1);
        }
    }
    
    private class videoAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int action;
//            if (parent.isVideoOut()) {
//                action =0;
//            } else {
//                action =1;
//            }
//            GUI UI =parent.getParent().getUserInterface();
//            UI.getConnection().writeSafe(
//                    MessageFactory.generateStreamUpdate(UI.getUserID(), 
//                            Message.SERVER, UI.getVideoStreamID(),
//                            parent.getUserID(), action));
//            
//            parent.setVideoOut(action == 1);
        }
    }
}
