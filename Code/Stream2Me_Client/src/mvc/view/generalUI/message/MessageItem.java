/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

/**
 *
 * @author Bernhard
 */
public class MessageItem extends JPanel {
    private final static Logger LOGGER = Logger.getLogger(MessageItem.class.getName());
    
    Color background =new Color(80, 150, 180);
    
    JLabel lblName;
    JLabel lblTime;
    JScrollPane messageScroll;
    JTextArea txaMessage;
    
    final int ID;
    final boolean owner;

    MessageItem(int ID, boolean owner, String name, String time, String message) {
        if (!owner) {
            background =Color.WHITE;
        }
        this.ID =ID;
        this.owner =owner;
        
        initComponents(name, time, message);
    }
    
    private void initComponents(String name, String time, String message) {
        lblName =new JLabel(name);
        lblTime =new JLabel(time.substring(0, time.indexOf(".")));
        messageScroll =new JScrollPane();
        messageScroll.setBackground(background);
        messageScroll.setBorder(null);
        
        messageScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        messageScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        txaMessage =new JTextArea(message);
        txaMessage.setBorder(null);
        txaMessage.setLineWrap(true);
        txaMessage.setWrapStyleWord(true);
        txaMessage.setBackground(background);
        
        messageScroll.setViewportView(txaMessage);
        
        JPanel header =new JPanel(new BorderLayout());
        header.add(lblName, BorderLayout.WEST);
        header.add(lblTime, BorderLayout.EAST);
        header.setBackground(background);
        header.setMaximumSize(new Dimension(3000, 16));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addComponent(lblTime))
                    .addComponent(messageScroll))
                .addGap(8, 8, 8))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(lblTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageScroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8))
        );
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        qualityHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHints(qualityHints);
        int width = getWidth()-2;
        int height = getHeight()-2;
        GeneralPath path1 = new GeneralPath();
        path1.moveTo(5, 10);
        path1.curveTo(5, 10, 7, 5, 2, 2);  //back curve
        path1.curveTo(2, 2, 12, 2, 12, 5); //down curve
        path1.curveTo(12, 5, 12, 2, 20, 2); //dip back up
        path1.lineTo(width - 10, 2);
        path1.curveTo(width - 10, 2, width - 2, 2, width - 2, 10);
        path1.lineTo(width - 2, height - 10);
        path1.curveTo(width - 2, height - 10, width - 2, height - 2, width - 10, height - 2);
        path1.lineTo(15, height - 2);
        path1.curveTo(15, height - 2, 5, height - 2, 5, height - 10);
        path1.lineTo(5, 15);
        path1.closePath();
        
        
        GeneralPath path2 = new GeneralPath();
        path2.moveTo(width-5, height-10);
        path2.curveTo(width-5, 10, width-7, 5, width-2, 2);  //back curve
        path2.curveTo(width-2, 2, width-12, 2, width-12, 5); //down curve
        path2.curveTo(width-12, 5, width-12, 2, width-20, 2); //dip back up
        path2.lineTo(10, 2);
        path2.curveTo(10, 2, 2, 2, 2, 10);
        path2.lineTo(2, height-10);
        path2.curveTo(2, height-10, 2, height-2, 10, height-2);
        path2.lineTo(width-15, height-2);
        path2.curveTo(width-15, height-2, width-5, height-2, width-5, height-10);
        path2.lineTo(width-5, 15);
        path2.closePath();
        
        graphics2D.setPaint(background);
        GeneralPath path =owner? path2 :path1;
        graphics2D.fill(path);
        graphics2D.setColor(Color.BLACK);
        graphics2D.draw(path);
    }
}
