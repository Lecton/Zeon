package messages.media.creation;

import messages.Message;
import messages.MessageType;

/**
 * User requesting creation of stream
 * @author Bernhard
 */
public class StreamPropertyRequestMessage extends Message {
    boolean action =false; //on - off
    int type =-1; //0 - video; 1 - audio
    String streamName ="";
    String streamID ="";

    public StreamPropertyRequestMessage(String userID, String streamName, String streamID, boolean action, int type) {
        this.userID =userID;
        this.targetID =Message.SERVER;
        this.streamName =streamName;
        this.streamID =streamID;
        this.action =action;
        this.type =type;
    }

    @Override
    public String getMessage() {
        return "Stream Property Request for "
                +(type == 0 ? "video" : "audio")+" to "
                +(action ? "start" : "stop")+"";
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
    public MessageType handle() {
        return MessageType.streamPropertyRequest;
    }
}
