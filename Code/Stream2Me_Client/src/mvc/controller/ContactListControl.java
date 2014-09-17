/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.model.Colleague;
import mvc.model.ColleagueList;
import mvc.view.generalUI.contacts.ContactList;
import mvc.view.generalUI.contacts.ContactProfile;

/**
 *
 * @author Bernhard
 */
public class ContactListControl {
    protected static ContactListControl INSTANCE =new ContactListControl();
    private static ContactList view;
    
    public static void register(ContactList viewList) {
        view =viewList;
    }
    
    public static void addPerson(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        
    }
    
//    public ContactProfile getContact(String userID) {
//        return view.getContactProfile(userID);
//    }
//    
//    public void addPerson(Colleague person, int position) {
//        view.addProfile(person.getUserID(), person.getFullname(), person.getAvatar(), position);
//    }
//    
//    public void removePerson(String userID) {
//        view.removeProfile(userID);
//    }
}
