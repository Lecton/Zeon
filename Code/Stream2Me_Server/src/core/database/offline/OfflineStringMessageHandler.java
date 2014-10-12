/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.offline;

import core.database.*;
import core.database.abstractInterface.StringMessageHandler;
import messages.Message;
import messages.StringMessage;

/**
 *
 * @author Bernhard
 */
public class OfflineStringMessageHandler implements StringMessageHandler {

    @Override
    public Message handleStringMessage(StringMessage msg) {
        if (msg.getTargetID().equals(Message.ALL)) {
            msg.setTargetGroupID(DatabaseHandler.userHandler.getGroupID(msg.getUserID()));
            System.out.println("STRINGMESSAGEHANDLER: To all");
        }
        return msg;
    }
}