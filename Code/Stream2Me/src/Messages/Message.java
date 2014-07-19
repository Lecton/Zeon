/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import client.GUI.GUI;
import java.io.Serializable;
import server.clientConnection;

/**
 *
 * @author Lecton
 */
public abstract class Message implements Serializable {
    public String Sender ="";
    public int ID =-1;
    public String Title ="";
    public String timestamp =null;
    public transient int SSN;
    public String passwordHash ="";
    public int to =-1; //-1 send to ALL
    
    public abstract String getMessage();
    
    public abstract void handle(GUI userInterface);
    
    public abstract Message repackage(clientConnection cc);
}
