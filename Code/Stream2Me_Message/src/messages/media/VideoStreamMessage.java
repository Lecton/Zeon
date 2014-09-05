package messages.media;

import messages.Message;

/**
 *
 * @author Bernhard
 */
public class VideoStreamMessage extends Message {
    private String img;
    private String streamID ="";
    
    public VideoStreamMessage(int userID, int targetID, String streamID) {
        this.userID =userID;
        this.targetID =targetID;
        this.streamID =streamID;
    }
    
    public VideoStreamMessage(VideoStreamMessage clone) {
        this.userID =clone.userID;
        this.targetID =clone.targetID;
        this.streamID =clone.streamID;
    }

    @Override
    public String getMessage() {
        String value ="Video Message";
        
        return value;
    }

    public String getStreamID() {
        return streamID;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    @Override
    public MessageType handle() {
        return MessageType.video;
    }
}