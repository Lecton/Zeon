/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

import messages.Message;
import messages.StringMessage;

/**
 *
 * @author Bernhard
 */
public class StringMessageHandler {

    /**
     * Adds the message to the database and retrieves the groupID of the person
     * who sent the message if the message targetID is Message.ALL
     * @param msg - String message to be added
     * @return String message with possibly modified groupID.
     */
    public static Message handleStringMessage(StringMessage msg) {
        if (msg.getTargetID().equals(Message.ALL)) {
            msg.setTargetGroupID(UserHandler.getGroupID(msg.getUserID()));
        }
        return msg;
    }
}