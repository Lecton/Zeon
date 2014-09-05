/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface.generalUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;

/**
 *
 * @author Bernhard
 */
public class SeparatorBorder implements Border {
    Color colour;
    boolean left, top, right, bottom;
    Insets inset;
    
    boolean Opaque =false;

    public SeparatorBorder(Color colour, boolean left, boolean top, boolean right, boolean bottom) {
        this.colour = colour;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        
        inset =new Insets(0, 0, 0, 0);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        int xL =x;
        int xR =x+width-1;
        int yT =y;
        int yB =y+height-1;
        
        g.setColor(colour);
        if (left) {
            g.drawLine(xL, yT, xL, yB);
        }
        if (top) {
            g.drawLine(xL, yT, xR, yT);
        }
        if (right) {
            g.drawLine(xR, yT, xR, yB);
        }
        if (bottom) {
            g.drawLine(xL, yB, xR, yB);
        }
    }
    
    public void setInserts(int top, int left, int bottom, int right) {
        this.inset= new Insets(top, left, bottom, right);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return inset;
    }

    @Override
    public boolean isBorderOpaque() {
        return Opaque;
    }
    
}
