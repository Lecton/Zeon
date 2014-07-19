/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import client.GUI.GUI;
import server.clientConnection;

/**
 *
 * @author Lecton
 */
public class NewUser extends Message {
    public int size =0;
    public int ID =-1;
    public String name ="";
    
    /**
     * COnstructor to define the message notifying the system that a new user
     * has been created.
     * @param size - the size of the message.
     * @param ID - the ID of the message. 
     * @param name - the name of this particular user.
     * @param Sender - the sender of this message.
     */
    public NewUser(int size, int ID, String name, String Sender) {
        this.size = size;
        this.ID = ID;
        this.name = name;
        this.Sender = Sender;
        this.Title = "New User";
    }
    
    /**
     * A function to retrieve the message and return the message being sent as
     * a string.
     * @return 
     */
    @Override
    public String getMessage() {
        return "New User " + ":" + size;
    }

    
    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user has been added and has connected.
     * @param userInterface - GUI reference for updating the ContactPane
     */
    @Override
    public void handle(GUI userInterface) {
        if (this.ID != userInterface.ID) {
            userInterface.ContactPane.addContact(this.ID, this.name, this.name);
        }
    }

    @Override
    public Message repackage(clientConnection cc) {
        throw new UnsupportedOperationException("Should not be received.");
    }
}
