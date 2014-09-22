/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.model.person.Person;
import mvc.view.generalUI.ProfilePanel;

/**
 *
 * @author Bernhard
 */
public class ProfileControl {
    private static ProfilePanel view;
    protected static ProfileControl INSTANCE =new ProfileControl();

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

    static void clear() {
        view.clear();
    }

    protected void update(Person person) {
        if (person.getUserID().equals(view.getUserID())) {
            view.setProfile(person.getName(), person.getSurname(), 
                    person.getEmail(), person.getAvatar(), 
                    person.getTitle(), person.getAboutMe());
        }
    }
}
