/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.model.Colleague;
import mvc.model.ColleagueList;
import mvc.model.Model;
import mvc.model.person.Person;
import mvc.view.generalUI.contacts.ContactList;
import mvc.view.generalUI.contacts.ContactProfile;

/**
 *
 * @author Bernhard
 */
public class ContactListControl {
    protected static ContactListControl INSTANCE =new ContactListControl();
    private static ContactList view;
    private static ColleagueList model =new ColleagueList();
    
    public static void register(ContactList viewList) {
        view =viewList;
    }
    
    public static void addPerson(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        Colleague person =new Colleague(userID, name, surname, email, avatar, title, aboutMe);
        model.add(person);
        UpdateControl.INSTANCE.add(userID, UpdateControl.NEWUSER);
    }

    static void clear() {
        model =new ColleagueList();
    }
    
    protected void addPerson(String userID) {
        Person person =model.get(userID);
        view.addProfile(person.getUserID(), person.getFullname(), person.getAvatar());
    }
    
    protected Colleague getPerson(String userID) {
        return model.get(userID);
    }
}
