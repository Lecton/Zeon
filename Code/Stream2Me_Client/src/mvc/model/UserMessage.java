/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

/**
 *
 * @author Bernhard
 */
public class UserMessage {
    final String userID;
    final String targetID;
    final String time;
    final String message;

    public UserMessage(String userID, String targetID, String time, String message) {
        this.userID = userID;
        this.targetID = targetID;
        this.time = time;
        this.message = message;
    }

    public String getUserID() {
        return userID;
    }

    public String getTargetID() {
        return targetID;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
