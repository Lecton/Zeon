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
    public static enum MessageType {login, logout, newUser,
                                    updateProfile, updateAvatar, updateList, 
                                    string, streamPropertyRequest, streamUpdate, 
                                    streamNotify, auido, video, streamProperty, 
                                    streamReply, greeting, console, streamTerminate};
    
    public static String SERVER ="SERVER";
    public static String ALL ="ALL";
    public static String ERROR ="ERROR";
    public static String IGNORE ="IGNORE";
    
    protected String userID =IGNORE;
    protected String timestamp =(new Timestamp((new java.util.Date()).getTime())).toString();
    protected transient int SSN;
    protected String targetID =IGNORE;
    protected String targetGroupID =IGNORE;
    
    public abstract String getMessage();
    
    public String getUserID() {
        return userID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTargetID() {
        return targetID;
    }

    public String getTargetGroupID() {
        return targetGroupID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public void setTargetGroupID(String targetGroupID) {
        this.targetGroupID = targetGroupID;
    }
    
    public abstract MessageType handle();
}