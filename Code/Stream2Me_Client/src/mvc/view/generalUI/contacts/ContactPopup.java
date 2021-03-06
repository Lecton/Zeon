/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.contacts;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import mvc.controller.generalUI.contacts.ContactControl;
import mvc.controller.generalUI.contacts.ContactListControl;
import mvc.controller.generalUI.UserControl;


/**
 *
 * @author Bernhard
 */
public class ContactPopup extends JPopupMenu {
    private final static Logger LOGGER = Logger.getLogger(ContactPopup.class.getName());
    
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
        
        
        //UserControl on getUserID
        audioControl =new JMenuItem("No audio stream");
        audioControl.addActionListener(ContactListControl.INSTANCE);
        audioControl.setVisible(false);
        
        videoControl =new JMenuItem("No video stream");
        videoControl.addActionListener(ContactListControl.INSTANCE);
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
        if (UserControl.streamingVideo()) {
            videoControl.setVisible(true);
            if (ContactControl.receivingVideo(parent.getUserID())) {
                videoControl.setText("Remove from video");
                videoControl.setActionCommand("removeVideo");
            } else {
                videoControl.setText("Invite to video");
                videoControl.setActionCommand("inviteVideo");
            }
        } else {
            videoControl.setVisible(false);
        }
        
        if (UserControl.streamingAudio()) {
            audioControl.setVisible(true);
            if (ContactControl.receivingAudio(parent.getUserID())) {
                audioControl.setText("Remove from audio");
                audioControl.setActionCommand("removeAudio");
            } else {
                audioControl.setText("Invite to audio");
                audioControl.setActionCommand("inviteAudio");
            }
        } else {
            audioControl.setVisible(false);
        }
        pack();
    }
}
