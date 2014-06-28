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
    public int ID =-1;
    public int size =0;
    
    public RemoveUser(int ID, int size, String Sender) {
        this.ID =ID;
        this.size =size;
        this.Sender =Sender;
        this.Title ="User left";
    }
    
    public String getMessage() {
        return ID + " left the chat.";
    }
}
