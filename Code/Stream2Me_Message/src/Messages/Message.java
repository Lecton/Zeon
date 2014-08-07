/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Lecton
 */

public abstract class Message implements Serializable {
    public static enum MessageType {login, logout, newUser, notify,
                                    updateName, updateAvatar, updateList, 
                                    string, streamProperty, streamUpdate, 
                                    streamNotify, auido, video, 
                                    streamReply, greeting};
    public static int SERVER =-10;
    public static int ALL =-11;
    public static int ERROR =-15;
    public static int IGNORE =-1;
    
    protected String Sender ="";
    protected int userID =IGNORE;
    protected String Title ="";
    protected String timestamp =(new Timestamp((new java.util.Date()).getTime())).toString();
    protected transient int SSN;
    protected int targetID =IGNORE;
    
    
    
    public abstract String getMessage();
    
    public String getSender() {
        return Sender;
    }

    public int getUserID() {
        return userID;
    }

    public String getTitle() {
        return Title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getTargetID() {
        return targetID;
    }

    public void setSender(String Sender) {
        this.Sender = Sender;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }
    
    public abstract MessageType handle();
}