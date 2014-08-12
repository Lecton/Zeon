
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernhard
 */
public class Button extends JButton {
    private String PATH ="./assets/";
    private int width =-1;
    private int height =-1;
    private Icon icon;

    public Button() {
    }

    @Override
    public void setIcon(Icon defaultIcon) {
        icon =defaultIcon;
        int width =getBoundsWidth();
        int height =getBoundsHeight();
        
        if (width != 0 && height != 0) {
//            System.out.println("width: "+width+" - height: "+height);
            super.setIcon(ImageUtils.resizeConvert(ImageUtils.convert(defaultIcon),
                    new Dimension(width, height)));
//            System.out.println("Set Icon width: "+getIcon().getIconWidth()+" height: "+getIcon().getIconHeight());
        }
    }
    
    private int getBoundsWidth() {
        return getBounds().width-getInsets().left-getInsets().right;
    }
    
    private int getBoundsHeight() {
        return getBounds().height-getInsets().top-getInsets().bottom;
    }

    @Override
    public void paint(Graphics g) {
        if (width != getBoundsWidth() && height != getBoundsHeight()) {
            width =getBoundsWidth();
            height =getBoundsHeight();
            setIcon(icon);
        }
        
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
    }
}
