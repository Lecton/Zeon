/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.model.Colleague;
import mvc.model.ColleagueList;
import mvc.model.UserMessage;
import mvc.model.person.Person;
import mvc.view.generalUI.contacts.ContactList;

/**
 *
 * @author Bernhard
 */
public class ContactListControl {
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
            UpdateControl.INSTANCE.add(userID, UpdateControl.NEWUSER);
        }
    }

    public static void removeColleague(String userID) {
        System.out.println("User disconnect");
        model.remove(userID);
       
        UpdateControl.INSTANCE.add(userID, UpdateControl.REMOVEUSER);
    }

    public static void addMessage(String userID, String targetID, String timestamp, String message) {
        UserMessage um =INSTANCE.getColleague(userID).addMessage(userID, targetID, timestamp, message);
        UpdateControl.INSTANCE.add(userID, UpdateControl.STRINGMESSAGE, um);
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

    String getColleagueFullname(String userID) {
        return model.get(userID).getFullname();
    }
}
