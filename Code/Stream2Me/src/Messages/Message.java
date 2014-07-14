/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Lecton
 */
public class Message implements Serializable {
    public String Sender ="";
    public int ID =-1;
    public String Title ="";
    public String timestamp =null;
    public transient int SSN;
    public String passwordHash ="";
    public int to =-1; //-1 send to ALL
    
    /**
     * Retrieves the message and returns the message being sent as a String
     * @return message
     */
    public String getMessage() {
        return "Default Message";
    }
}
