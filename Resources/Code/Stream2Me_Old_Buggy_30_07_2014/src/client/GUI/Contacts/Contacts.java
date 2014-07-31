package client.GUI.Contacts;

import Messages.Factory;
import Messages.StringMessage;
import Messages.UpdateUsername;
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
    
    public Contacts() {
        setup();
    }
    
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
    
    public int getSelectedIndex() {
        return selectedIndex;
    }
    
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
    
    public int getSelectedID() {
        int index = getSelectedIndex();
        System.out.println("Index: "+index);
        if (index == -1) {
            return -2;
        } else if (colleagues.get(index).getID() == userInterface.getID()) {
            return -1;
        } else {
            return colleagues.get(index).getID();
        }
    }
    
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
    
    private Colleague getColleague(int ID) {
        ContactProfile cp =getContactProfile(ID);
        if (cp != null) {
            return cp.getColleague();
        }
        return null;
    }
    
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

    public void updateUser(UpdateUsername uu) {
        int index = -1;
        if ((index =find(uu.getUserID())) != -1) {
            colleagues.get(index).setUsername(uu.getSender());
        }
    }
    
    public void updateList() {
        System.out.println("Contacts Updated");
        validate();
        repaint();
    }
    
    public void acceptMessage(StringMessage sm) {
        if(sm.getTargetID()== -1){
            System.out.println("Target was -1");
            userInterface.getMyContactData().getColleague().addMessage(sm);
            if (getSelectedID() == -1) {
                userInterface.appendChatMessage(sm);
            }
        } else if(userInterface.getID() == sm.getUserID()) {
            Colleague receiver = getColleague(sm.getTargetID());
            if(receiver != null){
                receiver.addMessage(sm);
                if(receiver.getID() == getSelectedID()){
                    userInterface.appendChatMessage(sm);
                }
            }
        } else {
            Colleague sender = getColleague(sm.getUserID());
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
        userInterface.getConnection().writeSafe(Factory.getStreamResponse(userInterface.getID(), streamID, response));
    }
    protected void videoResponse(boolean response, String streamID) {
        userInterface.getConnection().writeSafe(Factory.getStreamResponse(userInterface.getID(), streamID, response));
    }
    
    protected void updateAvatar(String avatar) {
        userInterface.getConnection().writeSafe(Factory.getUpdateAvatar(userInterface.getID(),avatar));
    }
}
