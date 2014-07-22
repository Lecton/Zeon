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
    protected String Sender ="";
    protected int ID =-1;
    protected String Title ="";
    protected String timestamp =null;
    protected transient int SSN;
    protected String passwordHash ="";
    protected int to =-1; //-1 send to ALL
    
    public abstract String getMessage();
    
    public abstract void handle(GUI userInterface);
    
    public abstract Message repackage(clientConnection cc);
    
    public String getSender() {
        return Sender;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getTo() {
        return to;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
