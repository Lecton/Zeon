package messages.media;

import messages.Message;

/**
 *
 * @author Bernhard
 */
public class VideoStreamMessage extends Message {
    private String img;
    private String streamName ="";
    
    public VideoStreamMessage(int userID, int targetID, String streamName) {
        this.userID =userID;
        this.targetID =targetID;
        this.streamName =streamName;
    }
    
    public VideoStreamMessage(VideoStreamMessage clone) {
        this.userID =clone.userID;
        this.targetID =clone.targetID;
        this.streamName =clone.streamName;
    }

    @Override
    public String getMessage() {
        String value ="Video Message";
        
        return value;
    }

    public String getStreamName() {
        return streamName;
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