/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view.connection;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Bernhard
 */
public class Delete extends JPanel {
    private static ImageIcon delete;
    private boolean active =false;

    public Delete() {
        try {
            BufferedImage image =(ImageIO.read(getClass().getResource("/delete.png")));
            delete =new ImageIcon(image.getScaledInstance(10, 10, BufferedImage.SCALE_SMOOTH));
        } catch (Exception e) {}
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void paint(Graphics g) {
//        super.paint(g);
        String temp ="Delete";
        g.drawChars(temp.toCharArray(), 0, temp.length(), 10, 12);
    }
}
