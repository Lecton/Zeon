/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.model.Colleague;
import mvc.model.ColleagueList;
import mvc.model.person.Notifier;
import mvc.model.person.Person;
import mvc.view.generalUI.contacts.ContactList;

/**
 *
 * @author Bernhard
 */
public class ContactListControl {
    private final static Logger LOGGER = Logger.getLogger(ContactListControl.class.getName());
    
    protected static ContactListControl INSTANCE =new ContactListControl();
    private static ContactList view;
    private static ColleagueList model =new ColleagueList();
    
    protected static void register(ContactList viewList) {
        view =viewList;
    }

    protected static void clear() {
        model =new ColleagueList();
    }
    
    public static void addColleague(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        if (!userID.equals(UserControl.getUserID())) {
            Colleague person =new Colleague(userID, name, surname, email, avatar, title, aboutMe);
            model.add(person);
            MessageControl.INSTANCE.addMessageHist(userID);
            UpdateControl.INSTANCE.add(userID, UpdateControl.NEWUSER);
        }
    }

    public static void removeColleague(String userID) {
        LOGGER.log(Level.INFO, "User disconnect");
        model.remove(userID);
       
        UpdateControl.INSTANCE.add(userID, UpdateControl.REMOVEUSER);
    }

    public static void updateContact(String userID, String name, String surname, String avatar, String title, String aboutMe) {
        Person person =INSTANCE.getColleague(userID);
        if (person != null) {
            if (name != null) {
                person.setName(name);
            }
            if (surname != null) {
                person.setSurname(surname);
            }
            if (avatar != null) {
                person.setAvatar(avatar);
            }
            if (title != null) {
                person.setTitle(title);
            }
            if (aboutMe != null) {
                person.setAboutMe(aboutMe);
            }
            UpdateControl.INSTANCE.add(userID, UpdateControl.UPDATEDETAILS);
        }
    }
    
    protected void addProfile(String userID) {
        Person person =model.get(userID);
        view.addProfile(person.getUserID(), person.getFullname(), person.getAvatar());
    }
    
    protected Colleague getColleague(String userID) {
        return model.get(userID);
    }
    
    protected void removeProfile(String userID) {
        view.removeProfile(userID);
    }

    protected String getColleagueFullname(String userID) {
        return model.get(userID).getFullname();
    }

    protected boolean alertMessage(String userID) {
        Notifier person =getColleague(userID);
        if (person != null) {
            person.setMessage(true);
            view.alert(userID);
            return true;
        }
        return false;
    }

    protected void settleMessageAlert(String userID) {
        Notifier person =getColleague(userID);
        if (person != null) {
            person.setMessage(false);
        }
    }

    protected void update(String userID) {
        Person person =model.get(userID);
        view.updateProfile(person.getUserID(), person.getFullname(), person.getAvatar());
        ProfileControl.INSTANCE.update(person);
    }
}