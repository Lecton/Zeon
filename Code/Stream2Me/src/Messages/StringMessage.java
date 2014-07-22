/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import client.GUI.GUI;
import java.util.Date;
import server.clientConnection;

/**
 *
 * @author Lecton
 */
public class StringMessage extends Message {
    public String mess ="";
    
    /**
     * Constructor that creates a string message to be sent.
     * @param mess - the message to be created/sent, as a string.
     * @param ID - the ID of the message to be created/sent.
     */
    public StringMessage(String mess, int ID) {
        this.mess =mess;
        this.ID =ID;
        this.Title ="String Message";
        Date d = new Date();
//        this.timestamp = d.getHours()+
//                         ":"+d.getMinutes();
    }
    
    /**
     * A function to retrieve the message and return the message being sent as a
     * string.
     * @return 
     */
    @Override
    public String getMessage() {
        return mess;
    }

    /**
     * 
     * @param userInterface - GUI reference for updating the ContactPane
     */
    @Override
    public void handle(GUI userInterface) {
        userInterface.getContactPane().acceptMessage(this);
    }

    @Override
    public Message repackage(clientConnection cc) {
        System.out.println("String message received");
        return this;
    }
}
