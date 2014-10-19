package mvc.model.mediaStreaming.video;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Bernhard
 */
public class ScreenCapture {
    private final static Logger LOGGER = Logger.getLogger(ScreenCapture.class.getName());
    
    public final static ScreenCapture INSTANCE =new ScreenCapture();
     
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void testSize() { 
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        if (width != size.width || height != size.height) {
            setSize(size);
        }
    }
    
    public BufferedImage getScreenImage() {
        BufferedImage bi =r.createScreenCapture(rect);
        try {
            BufferedImage cursor =ImageIO.read(getClass().getResource("/cursor.png"));
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = a.getLocation();
            int x = (int) b.getX();
            int y = (int) b.getY();
            Graphics2D graphics2D = bi.createGraphics();
            graphics2D.drawImage(cursor, x, y, 16, 16, null);
            cursor =null;
            System.gc();
        } catch (Exception e) {
        }
        return bi;
    }
    
    public ImageIcon getScreenIcon() {
        return new ImageIcon(getScreenImage());
    }
    
    public byte[] getScreenByteArray() {
        return getScreenImage().toString().getBytes();
    }
}
