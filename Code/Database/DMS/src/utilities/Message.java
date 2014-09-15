/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Date;

/**
 *
 * @author Lecton
 */
public class Message {
    String messageID;
    String userID;
    String targetID;
    Date timestamp;
    String message;

    public Message() {
    }

    public Message(String messageID, String userID, String targetID, Date timestamp, String message) {
        this.messageID = messageID;
        this.userID = userID;
        this.targetID = targetID;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getTargetID() {
        return targetID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
