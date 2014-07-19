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
public class RemoveUser extends Message {
    public int size =0;
    
    /**
     * Constructor that creates a message specifying to the system that a user of
     * a specific ID, size and sender is to be removed or is no longer active.
     * @param ID - the ID of the message.
     * @param size - the size of the message.
     * @param Sender - the sender of this message.
     */
    public RemoveUser(int ID, int size, String Sender) {
        this.ID = ID;
        this.size = size;
        this.Sender = Sender;
        this.Title = "User left";
    }
    /**
     * A function to retrieve the message and return the sent message as a string.
     * @return 
     */
    @Override
    public String getMessage() {
        return ID + " left the chat.";
    }

    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user has been removed or is no longer active.
     * @param userInterface - GUI reference for updating the ContactPane
     */
    @Override
    public void handle(GUI userInterface) {
        userInterface.ContactPane.removeContact(this.ID);
        System.out.println("Removed User");
    }

    @Override
    public Message repackage(clientConnection cc) {
        throw new UnsupportedOperationException("Should not be received.");
    }
}
