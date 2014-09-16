package messages.media;

import messages.Message;

public class StreamPropertyMessage extends Message {
    int type =0; //1 on  -  0 off
    String streamName ="";

    public StreamPropertyMessage(String userID, String targetID, String streamName, int type) {
        this.userID =userID;
        this.targetID =targetID;
        this.streamName =streamName;
        this.type =type;
    }

    @Override
    public String getMessage() {
        return "Stream Property";
    }

    public String getStreamName() {
        return streamName;
    }
    
    public int getType() {
        return type;
    }

    @Override
    public MessageType handle() {
        return MessageType.streamProperty;
    }
}
