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

public abstract class Message implements Serializable {
    public static enum MessageType {logon, logout, disconnect, newUser, 
                                    updateName, updateAvatar, string, 
                                    streamProperties, auido, video, 
                                    streamReply, greeting};
    
    protected String Sender ="";
    protected int userID =-1;
    protected String Title ="";
    protected String timestamp =null;
    protected transient int SSN;
    protected int targetID =-1; //-1 send to ALL
    
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