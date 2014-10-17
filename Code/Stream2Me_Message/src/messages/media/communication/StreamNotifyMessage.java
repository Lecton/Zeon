/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages.media.communication;

import messages.Message;
import messages.MessageType;

/**
 * User being informed of a stream invite
 * @author Bernhard
 */
public class StreamNotifyMessage extends Message {
    String streamID;
    String streamName;
    int type;
    boolean action;

    public StreamNotifyMessage(String userID, String targetID, String streamID, String streamName, int type, boolean action) {
        this.userID =userID;
        this.targetID =targetID;
        this.streamID = streamID;
        this.streamName =streamName;
        this.type = type;
        this.action =action;
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

    public boolean getAction() {
        return action;
    }
    
    @Override
    public String getMessage() {
        return "Stream notification of "+streamID+" for "+targetID+" on "+type;
    }

    @Override
    public MessageType handle() {
        return MessageType.streamNotify;
    }
    
}
