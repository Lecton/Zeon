/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.io.IOException;

/**
 *
 * @author Bernhard
 */
public class VideoStream extends Message {
    public long count =-1;
    public String img;
    
    /**
     * Constructor to create and specify the variables of a video stream, including
     * name of sender, ID, and recipient.
     * @param Sender - the name of the sender of this message
     * @param ID - the ID of the sender of this message
     * @param count - a counter.
     * @param to - the ID of the recipient of this message.
     */
    public VideoStream(int ID, long count, int to) throws IOException {
        this.ID =ID;
        this.count =count;
        this.to =to;
    }
    
    /**
     * Constructor to create and initialize a video stream by cloning an existing one.
     * @param clone 
     */
    public VideoStream(VideoStream clone) {
        this.ID =clone.ID;
        this.count =clone.count+1;
        this.to =clone.to;
    }
}