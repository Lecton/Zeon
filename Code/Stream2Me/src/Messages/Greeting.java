/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import client.GUI.GUI;
import server.clientConnection;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class Greeting extends Message {
    private int[] colleagueIDs;
    private String[] colleagueNames;
    private int size;
    
    /**
     * Constructor to create and initialize a message of the type Greeting.
     * @param name - name of the greeting message.
     * @param ID - ID of this specific greeting message.
     * @param size - the message size.
     * @param colleagueIDs - the IDs of the client's colleagues.
     * @param colleagueNames - the names of the client's colleagues.
     * @param Sender - the sender of the message.
     */
    public Greeting(String name, int ID, int size, int[] colleagueIDs, String[] colleagueNames) {
        this.Sender = name;
        this.ID = ID;
        this.size = size;
        this.Title = "Greeting";
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
        return "Greetings "+this.Sender+".\n"+"ID "+this.ID+".";
    }

    
    /**
     * Updates the system and colleague information as the colleague is created,
     * and assigns the colleague a space in the interface.
     * @param userInterface - GUI reference for updating of the ContactPane
     */
    @Override
    public void handle(GUI userInterface) {
        System.out.println(getMessage());
        
        userInterface.setID(this.ID);
        userInterface.setupMyContactData();

        for (int i = 0; i < this.size; i++) {
            userInterface.getContactPane().addContact(this.colleagueIDs[i], this.colleagueNames[i]);
        }
    }
    
    @Override
    public Message repackage(clientConnection cc) {
        throw new UnsupportedOperationException("Should not be received.");
    }
    
    
}
