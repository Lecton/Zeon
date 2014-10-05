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
    public static MessageType messageType;
    
    public static final String SERVER ="SERVER";
    public static final String ALL ="ALL";
    public static final String ERROR ="ERROR";
    public static final String IGNORE ="IGNORE";
    
    private String timestamp =(new Timestamp((new java.util.Date()).getTime())).toString();
    private transient int SSN;
    private String targetGroupID =IGNORE;
    
    protected String userID =IGNORE;
    protected String targetID =IGNORE;
    
    public abstract String getMessage();
    
    public abstract MessageType handle();
    
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
}