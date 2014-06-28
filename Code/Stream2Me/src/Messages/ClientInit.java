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
    public String name ="";
    
    public ClientInit(String name) {
        this.name =name;
    }
    
    public String getMessage() {
        return "My name is "+name+".";
    }
}
