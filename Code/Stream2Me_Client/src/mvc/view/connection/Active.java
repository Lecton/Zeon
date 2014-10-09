/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view.connection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Bernhard
 */
public class Active extends JPanel {
    private static ImageIcon activeIcon;
    private static ImageIcon inactiveIcon;
    private boolean active =false;

    public Active() {
        try {
            BufferedImage image =(ImageIO.read(getClass().getResource("/active.png")));
            activeIcon =new ImageIcon(image.getScaledInstance(10, 10, BufferedImage.SCALE_SMOOTH));
            image =(ImageIO.read(getClass().getResource("/inactive.png")));
            inactiveIcon =new ImageIcon(image.getScaledInstance(10, 10, BufferedImage.SCALE_SMOOTH));
        } catch (Exception e){}
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void paint(Graphics g) {
        if (active) {
            setBackground(Color.ORANGE);
        } else {
            setBackground(Color.lightGray);
        }
        super.paint(g);
        if (activeIcon != null && inactiveIcon != null) {
            this.setEnabled(active);
            Image i;
            if (active) {
                i =activeIcon.getImage();
            } else {
                i =inactiveIcon.getImage();
            }
            g.drawImage(i.getScaledInstance(10, 10, Image.SCALE_FAST), (this.getWidth()-10)/2, (this.getHeight()-10)/2, this);
        }
    }
}
