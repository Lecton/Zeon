/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import Messages.UpdateUser.UpdateUsername;
import client.GUI.GUI;
import server.clientConnection;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class ClientInit extends Message {
    
    /**
     * Constructor to create and initialize a client.
     * @param name - the name of the client.
     */
    public ClientInit(String name) {
        this.Sender = name;
    }
    
    /**
     * A function to retrieve the message and return the message being sent as
     * string.
     * @return 
     */
    @Override
    public String getMessage() {
        return "ClientInit: "+Sender+".";
    }

    @Override
    public void handle(GUI userInterface) {
        throw new UnsupportedOperationException("Should not be received.");
    }

    @Override
    public Message repackage(clientConnection cc) {
        cc.setName(Sender);
        return new UpdateUsername(cc.getID(), -1, Sender);
    }
}
