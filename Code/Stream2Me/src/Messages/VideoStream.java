/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Bernhard
 */
public class VideoStream extends Message {
    public long count =-1;
    public String img;
    
    public VideoStream(String Sender, int ID, long count, int to) throws IOException {
        this.Sender =Sender;
        this.ID =ID;
        this.count =count;
        this.to =to;
    }
    
    public VideoStream(VideoStream clone) {
        this.Sender =clone.Sender;
        this.ID =clone.ID;
        this.count =clone.count+1;
        this.to =clone.to;
    }
}