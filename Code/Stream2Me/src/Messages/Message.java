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
    public String Title ="";
    public Date timestamp =null;
    public transient int SSN;
    
    /**
     * Returns the message being send as a String
     * @return message
     */
    public String getMessage() {
        return "Default Message";
    }
}
