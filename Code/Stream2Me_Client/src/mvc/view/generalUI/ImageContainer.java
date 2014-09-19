/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import utils.ImageUtils;

/**
 *
 * @author Bernhard
 */
public class ImageContainer extends javax.swing.JPanel {
    ImageIcon icon;
    boolean fill;
    
    public ImageContainer() {
        initComponents();
        setBorder(new SeparatorBorder(Color.BLACK, true, true, true, true));
    }
    
    public void setImage(String base64Image, boolean fill) {
        if (base64Image != null && !base64Image.isEmpty()) {
            setImage(ImageUtils.decodeToImage(base64Image), fill);
        }
    }
    
    public void setImage(URL filename, boolean fill) throws IOException {
        if (filename != null) {
            setImage(ImageIO.read(filename), fill);
        }
    }
    
    public void clear() {
        this.icon =null;
        validate();
        repaint();
    }

    public void setImage(BufferedImage bi, boolean fill) {
//        this.fill = fill;
//        
        int width =getWidth();
        int height =getHeight();
//        
        if (width == 0 || height == 0)
        {
            width = getPreferredSize().width;
            height = getPreferredSize().height;
        }
        
//        System.out.println("W: "+width);
//        System.out.println("H: "+height);
        
//        Dimension d;
//        if (fill)
//            d =ImageUtils.getFillDimension(new java.awt.Dimension(bi.getWidth(), bi.getHeight()), new java.awt.Dimension(width, height));
//        else
//            d =ImageUtils.getScaledDimension(new java.awt.Dimension(bi.getWidth(), bi.getHeight()), new java.awt.Dimension(width, height));
//        
//        this.icon =bi.getScaledInstance(d.width, d.height, BufferedImage.SCALE_SMOOTH);
//        
        this.icon =ImageUtils.resizeConvert(bi, new Dimension(width, height));
//        ImageIcon ii =ImageUtils.resizeConvert(bi, new Dimension(width, height));
        
//        getGraphics().drawImage(ii.getImage(), (this.getWidth()-ii.getIconWidth())/2, 
//            (this.getHeight()-ii.getIconHeight())/2, ii.getImageObserver());
//        System.out.println("Drawing");
        revalidate();
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(15, 15));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 14, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        
        if (icon != null) {
//            if (fill) {
//            int iw = icon.getWidth(this);
//            int ih = icon.getHeight(this);
//            int ix1, iy1, ix2, iy2;
//            if (iw < ih) {
//                ix1 = 0;
//                iy1 = (ih-iw)/2;
//                
//                ix2 = iw;
//                iy2 = iy1+iw;
//            } else {
//                ix1 = (iw-ih)/2;
//                iy1 = 0;
//                
//                ix2 = ix1+ih;
//                iy2 = ih;
//            }
//            g.drawImage(icon, 0, 0, getWidth(), getHeight(), ix1, iy1, ix2, iy2, this);
//            } else {
                g.drawImage(icon.getImage(), (this.getWidth()-icon.getIconWidth())/2, 
                    (this.getHeight()-icon.getIconHeight())/2, icon.getImageObserver());
//            }
        }
    }
}