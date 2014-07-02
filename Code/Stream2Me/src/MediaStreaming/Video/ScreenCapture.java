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
    
    public ScreenCapture() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        x =0;
        y =0;
        width =size.width;
        height =size.height;
        try {
            r = new Robot();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        setSize();
    }
    
    public void setSize() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size.width, size.height);
    }
    
    public void setSize(Dimension d) {
        setSize(d.width, d.height);
    }
    
    public void setSize(int width, int height) {
        this.width =width;
        this.height =height;
        rect =new Rectangle(x, y, this.width, this.height);
    }
    
    public BufferedImage getScreenImage() {
        return r.createScreenCapture(rect);
    }
    
    public ImageIcon getScreenIcon() {
        return new ImageIcon(getScreenImage());
    }
    
    public byte[] getScreenByteArray() {
        return getScreenImage().toString().getBytes();
    }
    
    public void streamVideo(ObjectOutputStream oos, long fps, Object MessageHeader) {
        
    }
}