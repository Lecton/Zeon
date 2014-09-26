/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages.media.creation;

import messages.Message;

/**
 *
 * @author Bernhard
 */
public class StreamTerminateMessage extends Message {
    final String streamID;
    final String userID;
    final int type;
    String error;

    public StreamTerminateMessage(String streamID, String userID, int type) {
        this.streamID =streamID;
        this.userID =userID;
        this.type =type;
    }

    public String getStreamID() {
        return streamID;
    }

    public int getType() {
        return type;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return "Stream termination";
    }

    @Override
    public Message.MessageType handle() {
        return Message.MessageType.streamTerminate;
    }
}
