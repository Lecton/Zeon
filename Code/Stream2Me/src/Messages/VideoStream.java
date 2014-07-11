/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.awt.image.BufferedImage;

/**
 *
 * @author Lecton
 */
public class VideoStream extends Message {
    public long count =-1;
    public BufferedImage img =null;
    
    /**
     * 
     * @param Sender - the name of the sender of this message
     * @param title - the title of the stream.
     * @param passwordHash - the password to permit the streaming.
     * @param count - a counter.
     */
    public VideoStream(String Sender, String title, String passwordHash, long count) {
        this.Sender =Sender;
        this.Title =title;
        this.passwordHash =passwordHash;
        this.count =count;
    }
    
    /**
     * Constructor to create and initialize a video stream
     * @param clone 
     */
    public VideoStream(VideoStream clone) {
        this.Sender =clone.Sender;
        this.Title =clone.Title;
        this.passwordHash =clone.passwordHash;
        this.count =clone.count+1;
    }
}