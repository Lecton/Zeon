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
public class Log {
    String logID;
    Date timestamp;
    String userID;
    String messageType;

    public Log() {
    }

    public Log(String logID, Date timestamp, String userID, String messageType) {
        this.logID = logID;
        this.timestamp = timestamp;
        this.userID = userID;
        this.messageType = messageType;
    }

    public String getLogID() {
        return logID;
    }

    public String getMessageType() {
        return messageType;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserID() {
        return userID;
    }

    public void setLogID(String logID) {
        this.logID = logID;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
