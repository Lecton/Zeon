/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

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
    public String getMessage() {
        return "My name is "+this.name+". My ID is "+this.ID+".";
    }
}
