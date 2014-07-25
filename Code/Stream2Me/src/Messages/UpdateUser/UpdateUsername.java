package Messages.UpdateUser;

import Messages.Message;
import Utils.MessageUtils;

import client.Colleague;
import client.GUI.Contacts.ContactProfile;
import client.GUI.GUI;
import server.clientConnection;

/**
 *
 * @author Lecton
 */
public class UpdateUsername extends Message {
    
    /**
     * Constructor that creates a message specifying to the system that a user of
     * a specific ID and name has been updated with new information
     * @param ID
     * @param update 
     * @param type 
     */
    public UpdateUsername(int ID, int targetID, String username) {
        this.ID = ID;
        this.to = targetID;
        this.Sender = username;
        this.Title = "User Update";
    }
    
    /**
     * A function to retrieve the message and return the message being sent as a
     * string.
     * @return 
     */
    @Override
    public String getMessage() {
        return "My name is "+this.Sender+". My ID is "+this.ID+".";
    }

    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user has had information changed.
     * @param userInterface - GUI reference for updating the ContactPane
     */
    @Override
    public void handle(GUI userInterface) {
        System.out.println("User updated");
//        userInterface.getContactPane().updateUser(this);
        ContactProfile person =userInterface.getContactPane().getContactProfile(ID);
        if (person != null) {
            person.setUsername(Sender);
        }
        userInterface.getContactPane().updateList();
    }
    
    private void nameUpdate(Colleague person) {
        person.setUsername(Sender);
    }

    @Override
    public Message repackage(clientConnection cc) {
        System.out.println("Update User received");
        return this;
    }
}
