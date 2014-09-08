/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messageLogCore;

import messages.Message;
import java.sql.Timestamp;

/**
 *
 * @author Bernhard
 */
public class TimeStampedMessage {
    Message msg;
    Timestamp timestamp;

    public TimeStampedMessage(Message msg) {
        this.msg =msg;
        this.timestamp =new Timestamp((new java.util.Date()).getTime());
    }

    public String getMessage() {
        return msg.getMessage();
    }
}
