package userInterface.generalUI;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;
import messageUtils.ImageUtils;

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
    private boolean pressed =false;
    
    private Icon unclicked;
    private Icon clicked;

    public Button() {
    }

    @Override
    public void setIcon(Icon defaultIcon) {
        icon =defaultIcon;
        int _width =getBoundsWidth();
        int _height =getBoundsHeight();
        
        if (_width != 0 && _height != 0) {
            super.setIcon(ImageUtils.resizeConvert(ImageUtils.convert(defaultIcon),
                    new Dimension(_width, _height)));
        }
    }

    public void setUnclicked(Icon unclicked) {
        this.unclicked = unclicked;
    }

    public void setClicked(Icon clicked) {
        this.clicked = clicked;
    }
    
    public void togglePressed() {
        if (pressed && unclicked != null) {
            setIcon(unclicked);
        } else if (clicked != null) {
            setIcon(clicked);
        }
        
        pressed =!pressed;
    }

    public boolean isPressed() {
        return pressed;
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
            if (icon != null) {
                setIcon(icon);
            }
        }
        
        super.paint(g);
    }
}
