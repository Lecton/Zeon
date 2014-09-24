/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages.media.communication;

import messages.Message;

/**
 * Stream owner updating a stream property
 * @author Bernhard
 */
public class StreamUpdateMessage extends Message {
    private final String streamID;
    private final String affectedUserID;
    private final boolean action; //false remove; true add
    private final int type;

    public StreamUpdateMessage(String userID, String targetID, String streamID, String affectedUserID, int type, boolean action) {
        this.userID =userID;
        this.targetID =targetID;
        this.streamID = streamID;
        this.affectedUserID = affectedUserID;
        this.type =type;
        this.action =action;
    }

    public String getStreamID() {
        return streamID;
    }

    public String getAffectedUserID() {
        return affectedUserID;
    }

    public boolean getAction() {
        return action;
    }

    public int getType() {
        return type;
    }

    @Override
    public String getMessage() {
        return "Stream update of "+getStreamID()+" for "+getAffectedUserID()+" to "+action;
    }

    @Override
    public MessageType handle() {
        return MessageType.streamUpdate;
    }
    
    
}
