package messages.media;

import messages.Message;

/**
 *
 * @author Bernhard
 */
public class VideoStreamMessage extends Message {
    private String img;
    private String streamID ="";
    
    public VideoStreamMessage(String userID, String streamID) {
        this.userID =userID;
        this.targetID =Message.SERVER;
        this.streamID =streamID;
    }
    
    public VideoStreamMessage(VideoStreamMessage clone) {
        this.userID =clone.userID;
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

    public void setStreamID(String streamID) {
        this.streamID = streamID;
    }
}