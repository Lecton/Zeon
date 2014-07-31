/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

/**
 *
 * @author Lecton
 */
public class NewUser extends Message {
    public int size =0;
    
    /**
     * COnstructor to define the message notifying the system that a new user
     * has been created.
     * @param size - the size of the message.
     * @param ID - the ID of the message. 
     * @param name - the name of this particular user.
     * @param Sender - the sender of this message.
     */
    public NewUser(int size, int ID, String name) {
        this.size = size;
        this.ID = ID;
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
}
