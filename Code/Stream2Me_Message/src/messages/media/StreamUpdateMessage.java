/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages.media;

import messages.Message;

/**
 *
 * @author Bernhard
 */
public class StreamUpdateMessage extends Message {
    private final String streamID;
    private final int affectedUserID;
    private final int action; //0 remove; 1 add

    public StreamUpdateMessage(int userID, int targetID, String streamID, int affectedUserID, int action) {
        this.userID =userID;
        this.targetID =targetID;
        this.streamID = streamID;
        this.affectedUserID = affectedUserID;
        this.action =action;
    }

    public String getStreamID() {
        return streamID;
    }

    public int getAffectedUserID() {
        return affectedUserID;
    }

    public int getAction() {
        return action;
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
