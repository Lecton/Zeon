/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface.generalUI.messages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;
import messages.StringMessage;
import userInterface.generalUI.SeparatorBorder;

/**
 *
 * @author Bernhard
 */
public class MessageItem extends JPanel implements ListCellRenderer<StringMessage> {
    private boolean multiperson =false;
    private int userID =-1;

    /**
     * Creates new form MessageItem
     */
    public MessageItem() {
        initComponents();
        setBorder(new SeparatorBorder(Color.BLACK, true, true, true, true));
    }
    
    public void setText(String text) {
        txaMessage.setText(text);
        Rectangle r =txaMessage.getBounds();
        txaMessage.setBounds(r.x, r.y, r.width, txaMessage.getPreferredSize().height);
    }
    
    public void setMultiperson(boolean multiperson) {
        this.multiperson =multiperson;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imgAvatar = new userInterface.generalUI.ImageContainer();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMessage = new javax.swing.JEditorPane();

        imgAvatar.setBorder(null);
        imgAvatar.setToolTipText("");
        imgAvatar.setMaximumSize(new java.awt.Dimension(53, 53));
        imgAvatar.setMinimumSize(new java.awt.Dimension(53, 53));
        imgAvatar.setPreferredSize(new java.awt.Dimension(53, 53));

        javax.swing.GroupLayout imgAvatarLayout = new javax.swing.GroupLayout(imgAvatar);
        imgAvatar.setLayout(imgAvatarLayout);
        imgAvatarLayout.setHorizontalGroup(
            imgAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );
        imgAvatarLayout.setVerticalGroup(
            imgAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 53, Short.MAX_VALUE)
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(txaMessage);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imgAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 11, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private userInterface.generalUI.ImageContainer imgAvatar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JEditorPane txaMessage;
    // End of variables declaration//GEN-END:variables

    @Override
    public Component getListCellRendererComponent(JList<? extends StringMessage> list, StringMessage value, int index, boolean isSelected, boolean cellHasFocus) {
//        this.setToolTipText(value.getSender());
//        this.setToolTipText(value.getTimestamp());
        if (multiperson) {
            if (value.getUserID() != userID) {
                int r =255;
                int g =230-userID;
                int b =153+userID;
                
                if (g < 150) {
                    g =255;
                }
                if (b > 255) {
                    b =153;
                }
                
                setBackground(new Color(r,g,b));
            }
        } else if (value.getUserID() == userID) {
            setBackground(new Color(255,230,153));
        }
        setText(value.getMessage());
        return this;
    }
}
