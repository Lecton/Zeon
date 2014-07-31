package Messages.Media;

import Messages.Message;

public class StreamProperty extends Message {
    int type =0; //1 on  -  0 off
    String streamID ="";

    public StreamProperty(int userID, int targetID, String streamID, int type) {
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
