/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MediaStreaming.Video;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author Bernhard
 */
public class ScreenCapture {
    private Robot r =null;
    private Rectangle rect =null;
    private int width, height, x, y;
    
    /**
     * Constructor which defines the dimensions and size of the screen image to 
     * be captured.
     */
    public ScreenCapture() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        x = 0;
        y = 0;
        width = size.width;
        height = size.height;
        try {
            r = new Robot();
        } 
        catch(Exception ex) {
            ex.printStackTrace();
        }
        setSize();
    }
    
    /**
     * Determines the size of the screen dimensions and calls the function
     * to apply them to the window.
     */
    public void setSize() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size.width, size.height);
    }
    
    /**
     * Applies the predetermined size to the window when a dimension is provided
     * as an argument.
     * @param d - the dimensions of the width or height.
     */
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }
    
    /**
     * Applies the predetermined size to the window when the width and height
     * is provided.
     * @param width
     * @param height 
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        rect = new Rectangle(x, y, this.width, this.height);
    }
    
    /**
     * Captures the screen as an image and returns it as a buffered image.
     * @return 
     */
    public BufferedImage getScreenImage() {
        return r.createScreenCapture(rect);
    }
    
    /**
     * Casts and returns the captured screen image as an image icon.
     * @return 
     */
    public ImageIcon getScreenIcon() {
        return new ImageIcon(getScreenImage());
    }
    
    /**
     * Retrieves and returns the captured screen image as an array of bytes.
     * @return 
     */
    public byte[] getScreenByteArray() {
        return getScreenImage().toString().getBytes();
    }
    
    /**
     * The function by which the video data is streamed using a specific object
     * output stream.
     * @param oos - the object output stream.
     * @param fps - the frames per second.
     * @param MessageHeader - the header.
     */
    public void streamVideo(ObjectOutputStream oos, long fps, Object MessageHeader) {
        
    }
}
