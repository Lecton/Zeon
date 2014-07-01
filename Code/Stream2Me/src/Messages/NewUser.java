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
    public int ID =-1;
    public String name ="";
    
    public NewUser(int size, int ID, String name, String Sender) {
        this.size =size;
        this.ID =ID;
        this.name =name;
        this.Sender =Sender;
        this.Title ="New User";
    }
    
    public String getMessage() {
        return "New User " + ":" + size;
    }
}
