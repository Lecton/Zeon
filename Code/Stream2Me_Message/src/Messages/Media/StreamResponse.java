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
public class StreamResponse extends Message {
    String streamID;
    boolean accept =false;

    public StreamResponse(int userID, String streamID, boolean accept) {
        this.userID =userID;
        this.streamID = streamID;
        this.accept =accept;
    }

    public void setStreamID(String streamID) {
        this.streamID = streamID;
    }

    public String getStreamID() {
        return streamID;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
    
    @Override
    public String getMessage() {
        return "Stream Response for stream ID: "+streamID+" with "+accept;
    }    

    @Override
    public MessageType handle() {
        return MessageType.streamReply;
    }
}
