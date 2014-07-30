package Messages.Media;

import Messages.Message;

/**
 *
 * @author Bernhard
 */
public class VideoStream extends Message {
    private String img;
    
    public VideoStream(String Sender, int userID, int targetID) {
        this.Sender =Sender;
        this.userID =userID;
        this.targetID =targetID;
    }
    
    public VideoStream(VideoStream clone) {
        this.Sender =clone.Sender;
        this.userID =clone.userID;
        this.targetID =clone.targetID;
    }

    @Override
    public String getMessage() {
        String value ="Video Message";
        
        return value;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public MessageType handle() {
        return MessageType.video;
    }
}