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
    public String name = "";
    public int[] colleagueIDs;
    public String[] colleagueNames;
    public int size;
    
    /**
     * Constructor to create and initialize a message of the type Greeting.
     * @param name - name of the greeting message.
     * @param ID - ID of this specific greeting message.
     * @param size - the message size.
     * @param colleagueIDs - the IDs of the client's colleagues.
     * @param colleagueNames - the names of the client's colleagues.
     * @param Sender - the sender of the message.
     */
    public Greeting(String name, int ID, int size, int[] colleagueIDs, String[] colleagueNames, String Sender) {
        this.name = name;
        this.ID = ID;
        this.size = size;
        this.Sender = Sender;
        this.Title = "Greeting";
        this.colleagueIDs = colleagueIDs;
        this.colleagueNames = colleagueNames;
    }
    
    /**
     * A function to retrieve the message type (greeting) and all associated information,
     * such as the message name and ID.
     * @return 
     */
    public String getMessage() {
        return "Greetings "+this.name+".\n"+"ID "+this.ID+".";
    }
    
    
}
