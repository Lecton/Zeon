/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages.media.creation;

import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 */
public class StreamPropertyMessage extends Message {
    final String streamID;
    final String userID;
    final String streamName;
    final int type;
    final boolean success;
    String error;

    public StreamPropertyMessage(String streamID, String userID, String streamName, int type, boolean success) {
        this.streamID = streamID;
        this.userID = userID;
        this.streamName = streamName;
        this.type = type;
        this.success = success;
    }

    public String getStreamID() {
        return streamID;
    }

    public String getStreamName() {
        return streamName;
    }

    public int getType() {
        return type;
    }

    public boolean isSuccessful() {
        return success;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return "Stream property";
    }

    @Override
    public MessageType handle() {
        return MessageType.streamProperty;
    }
    
}
