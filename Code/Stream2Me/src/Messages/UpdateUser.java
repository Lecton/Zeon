/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import client.Colleague;
import client.GUI.GUI;
import server.clientConnection;

/**
 *
 * @author Lecton
 */
public class UpdateUser extends Message {
    private MessageUtils.Update type;
    private String update;
    
    /**
     * Constructor that creates a message specifying to the system that a user of
     * a specific ID and name has been updated with new information
     * @param ID
     * @param update 
     * @param type 
     */
    public UpdateUser(int ID, int targetID, String update, MessageUtils.Update type) {
        this.ID = ID;
        this.to = targetID;
        this.update = update;
        this.type =type;
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
        System.out.println("User updated "+type);
//        userInterface.getContactPane().updateUser(this);
        Colleague person =userInterface.getContactPane().getColleague(ID);
        if (person != null) {
            switch (type) {
                case NAME:
                    person.setUsername(update);
                    break;
                default:
                    System.out.println("Unknown update");
                    break;
            }
        }
        userInterface.getContactPane().updateList();
    }
    
    private void nameUpdate(Colleague person) {
        person.setUsername(update);
    }

    @Override
    public Message repackage(clientConnection cc) {
        System.out.println("Update User received");
        return this;
    }
}
