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
    
    public StringMessage(String mess, String Sender, int ID) {
        this.mess =mess;
        this.Sender =Sender;
        this.ID =ID;
        this.Title ="String Message";
    }
    
    public String getMessage() {
        return mess;
    }
}