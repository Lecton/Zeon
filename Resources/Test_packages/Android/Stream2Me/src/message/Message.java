package message;


import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

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

