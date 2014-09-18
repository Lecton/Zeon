/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;
import mvc.model.Colleague;
import mvc.model.person.Notifier;
import mvc.model.person.Receiver;

/**
 *
 * @author Bernhard
 */
public class ContactControl implements ActionListener, PropertyChangeListener {
    public static ContactControl INSTANCE =new ContactControl();
    
    public static boolean receivingVideo(String userID) {
        Colleague person =ContactListControl.INSTANCE.getPerson(userID);
        if (person != null) {
            return person.isReceivingVideo();
        }
        return false;
    }

    public static boolean receivingAudio(String userID) {
        Receiver person =ContactListControl.INSTANCE.getPerson(userID);
        if (person != null) {
            return person.isReceivingAudio();
        }
        return false;
    }

    public static boolean hasVideoNotify(String userID) {
        Notifier person =ContactListControl.INSTANCE.getPerson(userID);
        if (person != null) {
            return person.hasVideo();
        }
        return false;
    }

    public static boolean hasAudioNotify(String userID) {
        Notifier person =ContactListControl.INSTANCE.getPerson(userID);
        if (person != null) {
            return person.hasAudio();
        }
        return false;
    }

    public static boolean hasMessageNotify(String userID) {
        Notifier person =ContactListControl.INSTANCE.getPerson(userID);
        if (person != null) {
            return person.hasMessage();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if (command.equals("viewProfile")) {
            System.out.println("View Profile");
        } else if (command.equals("viewMessages")) {
            System.out.println("View Messages");
        } else if (command.equals("videoStream")) {
            System.out.println("Toggle video");
        } else if (command.equals("audioStream")) {
            System.out.println("Toggle audio");
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
                System.out.println("Selected");
                actionPerformed(new ActionEvent(evt.getSource(), 0, "viewMessages"));
            }
        } else {
            System.out.println("UNKNOWN: "+evt.getPropertyName());
        }
    }
    
}
