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
    public int[] colleagueIDs;
    public String[] colleagueNames;
    
    /**
     * Constructor to create and initialize a message of the type Greeting.
     * @param ID - ID of the sender of this specific greeting message.
     * @param to - ID of the recipient of this specific greeting message.
     * @param colleagueIDs - the IDs of the client's colleagues.
     * @param colleagueNames - the names of the client's colleagues.
     */
    public Greeting(int ID, int to, int[] colleagueIDs, String[] colleagueNames) {
        this.ID = ID;
        this.to = to;
        this.colleagueIDs = colleagueIDs;
        this.colleagueNames = colleagueNames;
    }
    
    /**
     * A function to retrieve the message type (greeting) and all associated information,
     * such as the message name and ID.
     * @return 
     */
    @Override
    public String getMessage() {
        return "Greetings.\n"+"ID "+this.ID+".";
    }
    
    
}
