package messages.media;

import messages.Message;

public class StreamPropertyMessage extends Message {
    int type =0; //1 on  -  0 off
    String streamID ="";

    public StreamPropertyMessage(int userID, int targetID, String streamID, int type) {
        this.userID =userID;
        this.targetID =targetID;
        this.streamID =streamID;
        this.type =type;
    }

    @Override
    public String getMessage() {
        return "Stream Property";
    }

    public String getStreamID() {
        return streamID;
    }
    
    public int getType() {
        return type;
    }

    @Override
    public MessageType handle() {
        return MessageType.streamProperty;
    }
}
