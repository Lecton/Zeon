/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages.Media;

import Messages.Message;

/**
 *
 * @author Bernhard
 */
public class StreamNotify extends Message {
    String streamID;
    int type;

    public StreamNotify(int userID, int targetID, String streamID, int type) {
        this.userID =userID;
        this.targetID =targetID;
        this.streamID = streamID;
        this.type = type;
    }

    public String getStreamID() {
        return streamID;
    }

    public int getType() {
        return type;
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
