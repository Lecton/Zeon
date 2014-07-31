/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

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
    public RemoveUser(int ID, int size) {
        this.ID = ID;
        this.size = size;
    }
    /**
     * A function to retrieve the message and return the sent message as a string.
     * @return 
     */
    public String getMessage() {
        return ID + " left the chat.";
    }
}
