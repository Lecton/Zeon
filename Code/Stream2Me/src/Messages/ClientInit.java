/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

/**
 *
 * @author Lecton
 */
public class ClientInit extends Message {
    public String name = "";
    
    /**
     * Constructor to create and initialize a client.
     * @param name - the name of the client.
     */
    public ClientInit(String name) {
        this.name = name;
    }
    
    /**
     * A function to retrieve the message and return the message being sent as
     * string.
     * @return 
     */
    public String getMessage() {
        return "My name is "+name+".";
    }
}
