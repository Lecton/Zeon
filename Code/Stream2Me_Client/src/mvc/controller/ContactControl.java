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
import javax.swing.JMenuItem;
import mvc.model.Colleague;
import mvc.model.ColleagueList;
import mvc.model.person.Notifier;
import mvc.model.person.Person;
import mvc.model.person.Receiver;
import mvc.view.generalUI.ProfilePanel;
import mvc.view.generalUI.contacts.ContactPopup;
import mvc.view.generalUI.contacts.ContactProfile;

/**
 *
 * @author Bernhard
 */
public class ContactControl implements ActionListener, PropertyChangeListener {
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

    protected static void updateContent(ProfilePanel pp) {
        Person p =ContactListControl.INSTANCE.getColleague(pp.getUserID());
        pp.setProfile(p.getName(), p.getSurname(), p.getEmail(), p.getAvatar(), p.getTitle(), p.getAboutMe());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if (command.equals("viewProfile")) {
            ContactPopup cp =(ContactPopup)((JMenuItem) e.getSource()).getParent();
            GUIControl.changeContent(0, cp.getUserID());
        } else if (command.equals("viewMessages")) {
            System.out.println("Show messages");
            ContactPopup cp =(ContactPopup)((JMenuItem) e.getSource()).getParent();
            GUIControl.changeContent(1, cp.getUserID());
        } else if (command.equals("videoStream")) {
            System.out.println("Toggle video");
        } else if (command.equals("audioStream")) {
            System.out.println("Toggle audio");
        } else if (command.equals("viewMessagesSelected")) {
            ContactProfile cp =(ContactProfile)e.getSource();
            GUIControl.changeContent(0, cp.getUserID());
        } else {
            System.out.println("UNKNOWN: "+command);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("background")) {
            Color c =(Color)evt.getNewValue();
            if (c.equals(Color.WHITE)) {
                System.out.println("Unselected");
            } else {
                actionPerformed(new ActionEvent(evt.getSource(), 0, "viewMessagesSelected"));
            }
        } else {
            System.out.println("UNKNOWN: "+evt.getPropertyName());
        }
    }

    protected void addMessage(String userID, String targetID, String timestamp, String message) {
        Colleague c =ContactListControl.INSTANCE.getColleague(targetID);
        c.addMessage(userID, targetID, timestamp, message);
    }
}