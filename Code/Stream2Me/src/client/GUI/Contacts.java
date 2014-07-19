/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.GUI;

import Messages.StringMessage;
import Messages.UpdateUser;
import client.Colleague;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class Contacts extends JScrollPane {
    public ArrayList<Colleague> colleagues = new ArrayList<>();
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
        list.setCellRenderer(new CellRenderer());
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
        } else if (colleagues.get(index).ID == userInterface.ID) {
            return -1;
        } else {
            return colleagues.get(index).ID;
        }
    }
    
    
    
    /**
     * Finds the colleague with the same ID as provided and returns his index
     * @param ID - ID the colleague to find
     * @return the colleague index or -1 if no one has that ID
     */
    private int find(int ID) {
        for (int i=0; i<colleagues.size(); i++) {
            if (colleagues.get(i).ID == ID) {
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
    private Colleague getColleague(int ID) {
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
     * @param localname - Local name of the contact to be added
     */
    public void addContact(int ID, String name, String localname) {
        System.out.println("User added");
        colleagues.add(new Colleague(ID, name, localname));
        list.setListData(colleagues.toArray());
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
        list.setListData(colleagues.toArray());
    }

    /**
     * Updates the information of a user in the contact list if/when their 
     * information changes.
     * @param uu - the user update message.
     */
    public void updateUser(UpdateUser uu) {
        System.out.println("User updated");
        int index = -1;
        if ((index =find(uu.ID)) != -1) {
            colleagues.get(index).setName(uu.name);
        }
        list.setListData(colleagues.toArray());
    }
    
    /**
     * Allows the user to accept a string message from one of the colleagues on his/her 
     * contacts list.
     * @param sm - the string message to be accepted.
     */
    public void acceptMessage(StringMessage sm) {
        if(sm.to == -1){
            Colleague me =getColleague(userInterface.ID);
            me.addMessage(sm);
            if (getSelectedID() == -1) {
                userInterface.appendChatMessage(sm);
            }
        }
        else if(userInterface.ID == sm.ID){
            Colleague receiver = getColleague(sm.to);
            if(receiver != null){
                receiver.addMessage(sm);
                if(receiver.ID == getSelectedID()){
                    userInterface.appendChatMessage(sm);
                }
            }
        }
        else{
            Colleague sender = getColleague(sm.ID);
            if (sender != null) {
                sender.addMessage(sm);
                if(sender.ID == getSelectedID()){
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
                if (list.getSelectedIndex() == -1) {
                    //Do nothing. 
                } 
                else {
//                    System.out.println(lastSelected+" --> "+list.getSelectedIndex());
                    
                    if (lastSelected == list.getSelectedIndex()) {
                        lastSelected =-1;
                        list.clearSelection();
                    } 
                    else {
                        lastSelected =list.getSelectedIndex();
                        //update ChatText
                        Colleague selectedColleague =(Colleague)getSelectedValue();
                        System.out.println("Selected Person: "+selectedColleague.name);
                        userInterface.setChatHistory(selectedColleague.getMessages());
                    }
                }
            }
        }
    }
    

    private class CellRenderer extends JPanel implements ListCellRenderer<Colleague> {
        private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

        private JLabel name;
        private JLabel avatar;
        private JButton button1;
        private JButton button2;
        private GroupLayout layout;

        /**
        * Renders the cells in the list whenever a change is made to the list of
        * contacts.
        */
        public CellRenderer() {
            setOpaque(true);
            this.setLayout(new BorderLayout());
            name =new JLabel("User");
            avatar =new JLabel();
            layout = new GroupLayout(this);
            
            button1 = new JButton();
            button2 = new JButton();

            name.setText("Name");

            button1.setBackground(new Color(51, 255, 0));
            button2.setBackground(new Color(255, 0, 0));

            setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(avatar, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(button1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 29, Short.MAX_VALUE))))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(avatar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            );
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Colleague> list, Colleague value,
            int index, boolean isSelected, boolean cellHasFocus) {
                name.setText(value.name);
                avatar.setIcon(new ImageIcon("./default.png"));
//                name.setText(value.getTitle());
//                name.setIcon(value.getImage());
                if (isSelected) {
                    setBackground(HIGHLIGHT_COLOR);
                    setForeground(Color.white);
                } else {
                    setBackground(Color.white);
                    setForeground(Color.black);
                }
                return this;
            }
        }
}
