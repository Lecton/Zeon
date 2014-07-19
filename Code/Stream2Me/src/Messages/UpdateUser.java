/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import client.GUI.GUI;
import java.util.ArrayList;
import server.clientConnection;

/**
 *
 * @author Lecton
 */
public class UpdateUser extends Message {
    public String name = "";
    
    /**
     * Constructor that creates a message specifying to the system that a user of
     * a specific ID and name has been updated with new information
     * @param ID
     * @param name 
     */
    public UpdateUser(int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.Sender = name;
        this.Title = "User Update";
    }
    
    /**
     * A function to retrieve the message and return the message being sent as a
     * string.
     * @return 
     */
    @Override
    public String getMessage() {
        return "My name is "+this.name+". My ID is "+this.ID+".";
    }

    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user has had information changed.
     * @param userInterface - GUI reference for updating the ContactPane
     */
    @Override
    public void handle(GUI userInterface) {
        userInterface.ContactPane.updateUser(this);
    }

    @Override
    public Message repackage(clientConnection cc) {
        System.out.println("Update User received");
        return this;
    }
}
