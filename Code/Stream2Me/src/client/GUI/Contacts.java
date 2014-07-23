/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.GUI;

import Messages.StringMessage;
import Messages.UpdateUser;
import client.Colleague;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class Contacts extends JScrollPane {
    private ArrayList<Colleague> colleagues = new ArrayList<>();
    private GUI userInterface;
    private JList list;
    
    /**
     * Default constructor to create a blank list of contacts.
     */
    public Contacts() {}
    
    /**
     * Constructor that creates a list of contacts.
     * @param userInterface 
     */
    public Contacts(GUI userInterface) {
        this.userInterface = userInterface;
        list = new JList();
        list.setVisibleRowCount(10);
        list.setCellRenderer(new ContactProfiler(userInterface));
        setViewportView(list);
        list.setSelectionMode(list.getSelectionModel().SINGLE_SELECTION);
        list.addListSelectionListener(new ListSelection());
    }
    
    /**
     * Returns the selected index or item in the list of contacts - i.e. selecting
     * a contact one wishes to send a message to. 
     * @return 
     */
    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }
    
    /**
     * Returns the selected value or item in the list of contacts.
     * @return 
     */
    public Object getSelectedValue() {
        return list.getSelectedValue();
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
    
    /**
     * Returns the colleague with the same ID as provided.
     * @param ID - ID of the colleague to find. 
     * @return 
     */
    public Colleague getColleague(int ID) {
        int index =find(ID);
        if (index != -1) {
            return colleagues.get(index);
        }
        return null;
    }
    
    /**
     * Allows the user to add a contact to their list of contacts. 
     * @param ID - ID of the contact to be added
     * @param name - Name of the contact to be added
     */
    public void addContact(int ID, String name) {
        System.out.println("User added");
        Colleague coll =new Colleague(ID, name);
        coll.initializeStreams(userInterface.getConnection(), userInterface.getUsername(), userInterface.getID());
        colleagues.add(coll);
        updateList();
    }
    
    /**
     * Compares and removes a contact from the list according to its specified ID.
     * @param colleagueID - the ID of the colleague to be removed.
     */
    public void removeContact(int colleagueID) {
        System.out.println("User removed");
        Colleague toRemove =getColleague(colleagueID);
        if (toRemove != null) {
            colleagues.remove(toRemove);
        } else {
            System.err.println("Error removing colleague with ID: "+colleagueID);
        }
        updateList();
    }

    /**
     * Updates the information of a user in the contact list if/when their 
     * information changes.
     * @param uu - the user update message.
     */
    public void updateUser(UpdateUser uu) {
        int index = -1;
        if ((index =find(uu.getID())) != -1) {
            colleagues.get(index).setUsername(uu.getSender());
        }
    }
    
    public void updateList() {
        System.out.println("Contacts Updated");
        list.setListData(colleagues.toArray());
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
    
    /**
     * Allows the user to select a specific colleague or contact on his/her list
     * of contacts.
     */
    private class ListSelection implements ListSelectionListener {
        int lastSelected =-1;
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                if (list.getSelectedIndex() != -1) {
                    if (lastSelected != list.getSelectedIndex()) {
                        lastSelected =list.getSelectedIndex();
                        //update ChatText
                        Colleague selectedColleague =(Colleague)getSelectedValue();
                        System.out.println("Selected Person: "+selectedColleague.getUsername());
                        userInterface.setChatHistory(selectedColleague.getMessages());
                    }
                }
            }
        }
    }
}
