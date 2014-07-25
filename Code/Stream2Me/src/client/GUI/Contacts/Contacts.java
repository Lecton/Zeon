/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.GUI.Contacts;

import Messages.Media.AudioResponse;
import Messages.StringMessage;
import Messages.UpdateUser.*;
import client.Colleague;
import client.GUI.GUI;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Contacts extends JPanel {
    private GUI userInterface;
    private int selectedIndex =-1;
    private ArrayList<ContactProfile> colleagues =new ArrayList<>();
    private GridBagConstraints gbcContactProfile;
    
    /**
     * Default constructor to create a blank list of contacts.
     */
    public Contacts() {
        setup();
    }
    
    /**
     * Constructor that creates a list of contacts.
     * @param userInterface 
     */
    public Contacts(GUI userInterface) {
        this.userInterface = userInterface;
        setup();
        
    }
    
    private void setup() {
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
    }
    
    /**
     * Returns the selected index or item in the list of contacts - i.e. selecting
     * a contact one wishes to send a message to. 
     * @return 
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
    /**
     * Returns the selected value or item in the list of contacts.
     * @return 
     */
    public ContactProfile getSelectedValue() {
        if (selectedIndex == -1) {
            return null;
        }
        return colleagues.get(selectedIndex);
    }
    
    public Colleague getSelectedColleague() {
        if (selectedIndex == -1) {
            return null;
        }
        return colleagues.get(selectedIndex).getColleague();
    }
    
    /**
     * Returns the selected ID or item in the list of contacts.
     * @return 
     */
    public int getSelectedID() {
        int index = getSelectedIndex();
        if (index == -1) {
            return -2;
        } else if (colleagues.get(index).getID() == userInterface.getID()) {
            return -1;
        } else {
            return colleagues.get(index).getID();
        }
    }
    
    /**
     * Finds the colleague with the same ID as provided and returns his index
     * @param ID - ID the colleague to find
     * @return the colleague index or -1 if no one has that ID
     */
    public int find(int ID) {
        for (int i=0; i<colleagues.size(); i++) {
            if (colleagues.get(i).getID() == ID) {
                return i;
            }
        }
        return -1;
    }
    
    public ContactProfile getContactProfile(int ID) {
        int index =find(ID);
        if (index != -1) {
            return colleagues.get(index);
        }
        return null;
    }
    
    /**
     * Returns the colleague with the same ID as provided.
     * @param ID - ID of the colleague to find. 
     * @return 
     */
    private Colleague getColleague(int ID) {
        ContactProfile cp =getContactProfile(ID);
        if (cp != null) {
            return cp.getColleague();
        }
        return null;
    }
    
    /**
     * Allows the user to add a contact to their list of contacts. 
     * @param ID - ID of the contact to be added
     * @param name - Name of the contact to be added
     */
    public ContactProfile addContact(int ID, String name) {
        System.out.println("User added");
        Colleague coll =new Colleague(ID, name);
        coll.initializeStreams(userInterface.getConnection(), userInterface.getUsername(), userInterface.getID());
        ContactProfile cp =new ContactProfile(this, coll);
        colleagues.add(cp);
        
        add(cp, gbcContactProfile, 0);
        
        updateList();
        return cp;
    }
    
    /**
     * Compares and removes a contact from the list according to its specified ID.
     * @param colleagueID - the ID of the colleague to be removed.
     */
    public void removeContact(int colleagueID) {
        System.out.println("User removed");
        ContactProfile toRemove =getContactProfile(colleagueID);
        if (toRemove != null) {
            colleagues.remove(toRemove);
        } else {
            System.err.println("Error removing colleague with ID: "+colleagueID);
        }
        remove(toRemove);
        
        updateList();
    }

    /**
     * Updates the information of a user in the contact list if/when their 
     * information changes.
     * @param uu - the user update message.
     */
    public void updateUser(UpdateUsername uu) {
        int index = -1;
        if ((index =find(uu.getID())) != -1) {
            colleagues.get(index).setUsername(uu.getSender());
        }
    }
    
    public void updateList() {
        System.out.println("Contacts Updated");
        validate();
        repaint();
    }
    
    /**
     * Allows the user to accept a string message from one of the colleagues on his/her 
     * contacts list.
     * @param sm - the string message to be accepted.
     */
    public void acceptMessage(StringMessage sm) {
        if(sm.getTo() == -1){
            Colleague me =getColleague(userInterface.getID());
            me.addMessage(sm);
            if (getSelectedID() == -1) {
                userInterface.appendChatMessage(sm);
            }
        } else if(userInterface.getID() == sm.getID()) {
            Colleague receiver = getColleague(sm.getTo());
            if(receiver != null){
                receiver.addMessage(sm);
                if(receiver.getID() == getSelectedID()){
                    userInterface.appendChatMessage(sm);
                }
            }
        } else {
            Colleague sender = getColleague(sm.getID());
            if (sender != null) {
                sender.addMessage(sm);
                if(sender.getID() == getSelectedID()){
                    userInterface.appendChatMessage(sm);
                }
            }
        }
    }
    
    protected void select(ContactProfile cp) {
        if (selectedIndex != -1) {
            colleagues.get(selectedIndex).unSelect();
        }
        
        selectedIndex =find(cp.getID());
        cp.select();
    }
    
    protected void unselect(ContactProfile cp) {
        int index =find(cp.getID());
        if (index == selectedIndex) {
            selectedIndex =-1;
        }
        cp.unSelect();
    }
    
    protected void setChatHistory(ArrayList<StringMessage> messageHistory) {
        userInterface.setChatHistory(messageHistory);
    }
    
    protected void audioResponse(boolean response, String streamID) {
        userInterface.getConnection().writeSafe(new AudioResponse(userInterface.getID(), streamID, response));
    }
    
    protected void videoResponse(boolean response, String streamID) {
//        userInterface.getConnection().writeSafe(new VideoResponse(userInterface.getID(), streamID, response));
    }
    
    protected void updateAvatar(String avatar) {
        userInterface.getConnection().writeSafe(new UpdateAvatar(userInterface.getID(),avatar));
    }
}
