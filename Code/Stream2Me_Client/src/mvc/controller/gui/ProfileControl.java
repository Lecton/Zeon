/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.handlers.MessageFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import mvc.model.person.Person;
import mvc.view.generalUI.ProfilePanel;

/**
 *
 * @author Bernhard
 */
public class ProfileControl implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(ProfileControl.class.getName());
    
    public static ProfileControl INSTANCE =new ProfileControl();
    
    private static ProfilePanel view;

    protected static void register(ProfilePanel profilePanel) {
        view =profilePanel;
    }

    protected static void updateContact(ProfilePanel panel) {
        Person person =ContactListControl.INSTANCE.getColleague(panel.getUserID());
        if (person != null) {
            view.setProfile(person.getName(), person.getSurname(), 
                    person.getEmail(), person.getAvatar(), person.getTitle(), 
                    person.getAboutMe());
//            throw new UnsupportedOperationException("Not supported yet.");
//            protected static void updateContent(ProfilePanel pp) {
//                Person p =ContactListControl.INSTANCE.getColleague(pp.getUserID());
//                pp.setProfile(p.getName(), p.getSurname(), p.getEmail(), p.getAvatar(), p.getTitle(), p.getAboutMe());
//            }
        }
    }
    
    protected static void updateUser(ProfilePanel panel) {
        Person user =UserControl.INSTANCE.getUser();
        view.setProfile(user.getName(), user.getSurname(), user.getEmail(), 
                user.getAvatar(), user.getTitle(), user.getAboutMe());
//        throw new UnsupportedOperationException("Not supported yet.");
//        protected static void updateContent(ProfilePanel userPanel) {
//            userPanel.setProfile(model.getName(), model.getSurname(), 
//                    model.getEmail(), model.getAvatar(), model.getTitle(), 
//                    model.getAboutMe());
//        }
    }

    protected static void clear() {
        view.clear();
    }

    protected void update(Person person) {
        if (person.getUserID().equals(view.getUserID())) {
            view.setProfile(person.getName(), person.getSurname(), 
                    person.getEmail(), person.getAvatar(), 
                    person.getTitle(), person.getAboutMe());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if (command != null) {
            if (command.equals("updateDetails")) {
                String userID =view.getUserID();
                String name =view.getFirstname();
                String surname =view.getSurname();
                String email =view.getEmail();
                String title =view.getTitle();
                String aboutMe =view.getAboutMe();
                
                Person person =ContactListControl.INSTANCE.getColleague(userID);
                if (person != null) {
                    person.setName(name);
                    person.setSurname(surname);
                    person.setEmail(email);
                    person.setTitle(title);
                    person.setAboutMe(aboutMe);
                    Control.INSTANCE.writeMessage(MessageFactory.generateUpdateProfile(userID, name, surname, email, title, aboutMe));
                }
            } else if (command.equals("updateAvatar")) {
                String userID =view.getUserID();
//                String avatar =view.getAvatar();
                
            } else if (command.equals("updateUserDetails")) {
                String userID =view.getUserID();
                String name =view.getFirstname();
                String surname =view.getSurname();
                String email =view.getEmail();
                String title =view.getTitle();
                String aboutMe =view.getAboutMe();
                
                Person person =UserControl.INSTANCE.getUser();
                if (person != null) {
                    person.setName(name);
                    person.setSurname(surname);
                    person.setEmail(email);
                    person.setTitle(title);
                    person.setAboutMe(aboutMe);
                    UserControl.INSTANCE.update(userID);
                    Control.INSTANCE.writeMessage(MessageFactory.generateUpdateProfile(userID, name, surname, email, title, aboutMe));
                }
            } else if (command.equals("updateUserAvatar")) {
                
            }
        }
    }
}
