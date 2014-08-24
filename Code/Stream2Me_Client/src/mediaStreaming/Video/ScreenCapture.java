package mediaStreaming.video;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
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
        x = 0;
        y = 0;
        width = size.width;
        height = size.height;
        try {
            r = new Robot();
        } catch(AWTException ex) {
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
        setSize(0, 0, width, height);
    }
    
    public void setSize(int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        this.x =x;
        this.y =y;
        rect = new Rectangle(this.x, this.y, this.width, this.height);
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
}
