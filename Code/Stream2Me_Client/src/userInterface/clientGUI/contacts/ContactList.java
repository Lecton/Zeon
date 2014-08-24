/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface.clientGUI.contacts;

import client.Colleague;
import userInterface.clientGUI.GUI;
import utils.Log;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Bernhard
 */
public class ContactList extends JPanel {
    private GUI userInterface =null;
    private GridBagConstraints gbcContactProfile;
    private ContactProfile selectedProfile ;
    private ArrayList<ContactProfile> list;
    
    public ContactList() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(new JPanel(), gbc);
        
        
        gbcContactProfile = new GridBagConstraints();
        gbcContactProfile.gridwidth = GridBagConstraints.REMAINDER;
        gbcContactProfile.weightx = 1;
        gbcContactProfile.fill = GridBagConstraints.HORIZONTAL;
        
        selectedProfile =null;
        list =new ArrayList<>();
    }
    
    private void update() {
        validate();
        repaint();
    }

    public void setUserInterface(GUI userInterface) {
        this.userInterface = userInterface;
    }

    protected GUI getUserInterface() {
        return userInterface;
    }
    
    public void addProfile(Colleague profile) {
        
        ContactProfile contact =new ContactProfile();
        contact.setColleague(profile);
        contact.setContactList(this);
        list.add(contact);
        add(contact, gbcContactProfile, list.size()-1);
        update();
        
        Log.write(this, "User added");
    }
    
    public void removeProfile(int userID) {
        ContactProfile profile =getContactProfile(userID);
        if (profile != null) {
            list.remove(profile);
            remove(profile);
            update();
            
            if (profile == selectedProfile) {
                userInterface.getChatArea().clear();
            }
        } else {
            Log.error(this, "User to be removed was not found in list");
        }
    }
    
    public ContactProfile getContactProfile(int userID) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).getUserID() == userID) {
                return list.get(i);
            }
        }
        return null;
    }
    
//    public Colleague getSelected() {
//        return selectedProfile.getOwner();
//    }
    
    public int getSelectedUserID() {
        if (selectedProfile == null) {
            return Messages.Message.IGNORE;
        } else if (selectedProfile.getUserID() == userInterface.getUserID()) {
            return Messages.Message.ALL;
        } else {
            return selectedProfile.getUserID();
        }
    }

    public ContactProfile getSelectedProfile() {
        return selectedProfile;
    }
    
    protected void select(ContactProfile profile) {
        if (selectedProfile != null) {
            selectedProfile.unselect();
        }
        
        if (profile != null) {
            selectedProfile =profile;
            selectedProfile.select();
            
            userInterface.getChatArea().setMessagesHistory(profile.getMessageHistory());
            
        }
    }
    
    protected void unselect(ContactProfile profile) {
        selectedProfile =null;
        userInterface.getChatArea().clear();
        
        if (profile != null) {
            profile.unselect();
        }
    }
}
