/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

/**
 *
 * @author Lecton
 */
public class StringMessage extends Message {
    public String mess ="";
    
    /**
     * Constructor that creates a string message to be sent.
     * @param mess - the message to be created/sent, as a string.
     * @param ID - the ID of the message to be created/sent.
     */
    public StringMessage(String mess, int ID) {
        this.mess =mess;
        this.ID =ID;
        this.Title ="String Message";
    }
    
    /**
     * A function to retrieve the message and return the message being sent as a
     * string.
     * @return 
     */
    public String getMessage() {
        return mess;
    }
}
