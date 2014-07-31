/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import Messages.*;
import client.GUI.GUI;
import java.io.IOException;

/**
 *
 * @author Lecton
 * @author Bernhard
 * @author Zenadia
 */
public class inStream implements Runnable {
    private Connection con = null;
    private GUI userInterface =null;

    /**
     * Constructor that initializes and creates an incoming stream and specifies 
     * it an interface.
     * @param userInterface 
     */
    public inStream(GUI userInterface) {
        this.userInterface =userInterface;
    }
    
    /**
     * Sets the object input stream for this specific incoming stream via a connection.
     * @param ois - the object input stream.
     */
    public void setInputStream(Connection con) {
        this.con =con;
    }

    /**
     * Runs the incoming stream via the connection, determines the type of message 
     * and performs the required action for each different message type.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Message m =con.read();
                m.handle(userInterface);
                
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.err.println("RUN - IOException");
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
                System.err.println("RUN - ClassNotFoundException");
            }
        }
    }
}