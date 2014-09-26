/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.containers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JButton;
import utils.ImageUtils;


/**
 *
 * @author Bernhard
 */
public class Button extends JButton {
    private final static Logger LOGGER = Logger.getLogger(Button.class.getName());
    
    private String PATH ="./assets/";
    private int width =-1;
    private int height =-1;
    private Icon icon;
    private boolean pressed =false;
    
    private Icon unclicked;
    private Icon clicked;
    
    private String ownerID;

    public Button() {
    }

    @Override
    public void setIcon(Icon defaultIcon) {
        icon =defaultIcon;
        int width =getBoundsWidth();
        int height =getBoundsHeight();
        if (width != 0 && height != 0) {
            super.setIcon(ImageUtils.resizeConvert(
                    ImageUtils.convert(defaultIcon),
                    new Dimension(width, height)));
        }
    }

    public void setUnclicked(Icon unclicked) {
        this.unclicked = unclicked;
    }

    public void setClicked(Icon clicked) {
        this.clicked = clicked;
    }
    
    public void togglePressed() {
        togglePressed(!pressed);
    }
    
    public void togglePressed(boolean pressed) {
        this.pressed =pressed;
        if (pressed) {
            if (clicked != null) {
                setIcon(clicked);
            } else {
                setBackground(Color.red);
            }
        } else {
            if (unclicked != null) {
                setIcon(unclicked);
            } else {
                setBackground(Color.blue);
            }
        }
    }

    public void setPressed(boolean pressed) {
        if (pressed != this.pressed) {
            togglePressed(pressed);
        }
    }

    public boolean isPressed() {
        return pressed;
    }
    
    private int getBoundsWidth() {
        return getBounds().width-getInsets().left-getInsets().right-10;
    }
    
    private int getBoundsHeight() {
        return getBounds().height-getInsets().top-getInsets().bottom-10;
    }

    @Override
    public void paint(Graphics g) {
        if (width != getBoundsWidth() && height != getBoundsHeight()) {
            width =getBoundsWidth();
            height =getBoundsHeight();
            if (icon != null) {
                setIcon(icon);
            }
        }
        
        super.paint(g);
    }

    public void setOwnerID(String ownerID) {
        this.ownerID =ownerID;
    }

    public String getOwnerID() {
        return ownerID;
    }
}
