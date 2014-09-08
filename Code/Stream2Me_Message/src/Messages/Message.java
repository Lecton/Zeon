/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package messages;

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
    
    protected int userID =IGNORE;
    protected String timestamp =(new Timestamp((new java.util.Date()).getTime())).toString();
    protected transient int SSN;
    protected int targetID =IGNORE;
    protected int targetGroupID =IGNORE;
    
    public abstract String getMessage();
    
    public int getUserID() {
        return userID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getTargetID() {
        return targetID;
    }

    public int getTargetGroupID() {
        return targetGroupID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setTargetID(int targetID) {
        this.targetID = targetID;
    }

    public void setTargetGroupID(int targetGroupID) {
        this.targetGroupID = targetGroupID;
    }
    
    public abstract MessageType handle();
}