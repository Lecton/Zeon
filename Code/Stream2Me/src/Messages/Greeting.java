/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.io.Serializable;

/**
 *
 * @author Lecton
 */
public class Greeting extends Message {
    public String name ="";
    public int ID =-1;
    
    public Greeting(String name, int ID, String Sender) {
        this.name =name;
        this.ID =ID;
        this.Sender =Sender;
        this.Title ="Greeting";
    }
    
    public String getMessage() {
        return "Greetings "+this.name+".\n"+"ID "+this.ID+".";
    }
    
    
}
