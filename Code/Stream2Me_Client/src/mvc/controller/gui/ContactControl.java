/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import mvc.model.Colleague;
import mvc.model.person.Notifier;
import mvc.model.person.Receiver;
import mvc.view.generalUI.contacts.ContactPopup;
import mvc.view.generalUI.contacts.ContactProfile;

/**
 *
 * @author Bernhard
 */
public class ContactControl implements ActionListener, PropertyChangeListener {
    private final static Logger LOGGER = Logger.getLogger(ContactControl.class.getName());
    
    public static ContactControl INSTANCE =new ContactControl();
    
    public static boolean receivingVideo(String userID) {
        Colleague person =ContactListControl.INSTANCE.getColleague(userID);
        if (person != null) {
            return person.isReceivingVideo();
        }
        return false;
    }

    public static boolean receivingAudio(String userID) {
        Receiver person =ContactListControl.INSTANCE.getColleague(userID);
        if (person != null) {
            return person.isReceivingAudio();
        }
        return false;
    }

    public static boolean hasVideoNotify(String userID) {
        Notifier person =ContactListControl.INSTANCE.getColleague(userID);
        if (person != null) {
            return person.hasVideo();
        }
        return false;
    }

    public static boolean hasAudioNotify(String userID) {
        Notifier person =ContactListControl.INSTANCE.getColleague(userID);
        if (person != null) {
            return person.hasAudio();
        }
        return false;
    }

    public static boolean hasMessageNotify(String userID) {
        Notifier person =ContactListControl.INSTANCE.getColleague(userID);
        if (person != null) {
            return person.hasMessage();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if (command.equals("viewProfile")) {
            ContactPopup cp =(ContactPopup)((JMenuItem) e.getSource()).getParent();
            GUIControl.changeContent(0, cp.getUserID(),  ContactListControl
                    .INSTANCE.getColleagueFullname(cp.getUserID()));
        } else if (command.equals("viewMessages")) {
            LOGGER.log(Level.INFO, "Show messages");
            ContactPopup cp =(ContactPopup)((JMenuItem) e.getSource()).getParent();
            ContactListControl.INSTANCE.settleMessageAlert(cp.getUserID());
            GUIControl.changeContent(1, cp.getUserID(), ContactListControl
                    .INSTANCE.getColleagueFullname(cp.getUserID()));
        } else if (command.equals("viewMessagesSelected")) {
            ContactProfile cp =(ContactProfile)e.getSource();
            ContactListControl.INSTANCE.settleMessageAlert(cp.getUserID());
            GUIControl.changeContent(1, cp.getUserID(), ContactListControl
                    .INSTANCE.getColleagueFullname(cp.getUserID()));
        } else {
            LOGGER.log(Level.WARNING, "UNKNOWN: "+command);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("background")) {
            Color c =(Color)evt.getNewValue();
            if (c.equals(Color.WHITE)) {
                LOGGER.log(Level.INFO, "Unselected");
                GUIControl.hideStreamAcceptors(((ContactProfile)evt.getSource()).getUserID());
            } else {
                actionPerformed(new ActionEvent(evt.getSource(), 0, "viewMessagesSelected"));
                GUIControl.checkStreamAcceptors(((ContactProfile)evt.getSource()).getUserID());
            }
        } else {
            LOGGER.log(Level.WARNING, "UNKNOWN: "+evt.getPropertyName());
        }
    }
}