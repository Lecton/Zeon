/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class UpdateUser extends Message {
    public String name = "";
    
    /**
     * Constructor that creates a message specifying to the system that a user of
     * a specific ID and name has been updated with new information
     * @param ID
     * @param to
     * @param name 
     */
    public UpdateUser(int ID, int to, String name) {
        this.ID =ID;
        this.to =to;
        this.name =name;
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
}
