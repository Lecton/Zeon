/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface.ClientGUI.Contacts;

import Interface.ClientGUI.GUI;
import Utils.MessageFactory;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Bernhard
 */
public class ContactPopupMenu extends JPopupMenu {
    JMenuItem audioControl;
    JMenuItem videoControl;
    ContactProfile parent;
    
    public ContactPopupMenu(ContactProfile parent) throws HeadlessException {
        this.parent =parent;
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
        
        if (parent.getParent().getUserInterface().getUserProfile().isAudioStream()) {
            audioControl.setEnabled(true);
            if (parent.inAudio()) {
                audioControl.setText("Remove from audio");
            } else {
                audioControl.setText("Invite to audio");
            }
        } else {
            audioControl.setText("No audio stream");
            audioControl.setEnabled(false);
        }
        
        if (parent.getParent().getUserInterface().getUserProfile().isVideoStream()) {
            videoControl.setEnabled(true);
            if (parent.inVideo()) {
                videoControl.setText("Remove from video");
            } else {
                videoControl.setText("Invite to video");
            }
        } else {
            videoControl.setText("No audio stream");
            videoControl.setEnabled(false);
        }
    }
    
    private class audioAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int action;
            if (parent.inAudio()) {
                action =0;
            } else {
                action =1;
            }
            GUI UI =parent.getParent().getUserInterface();
            UI.getConnection().writeSafe(
                    MessageFactory.generateStreamUpdate(UI.getUserID(), 
                            Messages.Message.SERVER, UI.getUserProfile().getAudioStreamID(),
                            parent.getUserID(), action));
            
            parent.setInAudio(action == 1);
        }
    }
    
    private class videoAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int action;
            if (parent.inVideo()) {
                action =0;
            } else {
                action =1;
            }
            GUI UI =parent.getParent().getUserInterface();
            UI.getConnection().writeSafe(
                    MessageFactory.generateStreamUpdate(UI.getUserID(), 
                            Messages.Message.SERVER, UI.getUserProfile().getVideoStreamID(),
                            parent.getUserID(), action));
            
            parent.setInVideo(action == 1);
        }
    }
}
