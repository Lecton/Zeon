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
    public int ID =-1;
    public String name ="";
    
    public UpdateUser(int ID, String name) {
        this.ID =ID;
        this.name =name;
        this.Sender =name;
        this.Title ="User Update";
    }
    
    public String getMessage() {
        return "My name is "+this.name+". My ID is "+this.ID+".";
    }
}
