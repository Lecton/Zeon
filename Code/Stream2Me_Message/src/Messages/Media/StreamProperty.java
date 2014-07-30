package Messages.Media;

import Messages.Message;

public class StreamProperty extends Message {
    int type =0; //1 on  -  0 off
    String streamID ="";
    int[] allowed =null;

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

    public void setAllowed(int[] allowed) {
        this.allowed =allowed;
    }

    public int[] getAllowed() {
        return allowed;
    }

    public String getStreamID() {
        return streamID;
    }
    
    public int getType() {
        return type;
    }
    
    public boolean contained(int ID) {
        if (allowed == null) return false;
        
        for (int i=0; i<allowed.length; i++) {
            if (allowed[i] == ID) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MessageType handle() {
        return MessageType.streamProperty;
    }
}
