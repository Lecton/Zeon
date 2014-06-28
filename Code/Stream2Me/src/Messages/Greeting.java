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
    public int[] colleagueIDs;
    public String[] colleagueNames;
    public int size;
    
    public Greeting(String name, int ID, int size, int[] colleagueIDs, String[] colleagueNames, String Sender) {
        this.name =name;
        this.ID =ID;
        this.size =size;
        this.Sender =Sender;
        this.Title ="Greeting";
        this.colleagueIDs =colleagueIDs;
        this.colleagueNames =colleagueNames;
    }
    
    public String getMessage() {
        return "Greetings "+this.name+".\n"+"ID "+this.ID+".";
    }
    
    
}
