/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;
import mvc.controller.UserControl;

/**
 *
 * @author Bernhard
 */
public class UserPanel extends javax.swing.JPanel {
    private final static Logger LOGGER = Logger.getLogger(UserPanel.class.getName());
    
    /**
     * Creates new form OwnerPanel
     */
    public UserPanel() {
        initComponents();
        
        setComponentPopupMenu(new UserPopup());
    }
    
    public void setName(String name) {
        lblName.setText(name);
    }
    
    public void setAvatar(String avatar) {
        imgAvatar.setImage(avatar, true);
    }
    
    public void setID(String userID) {
        lblID.setText(userID);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblName = new javax.swing.JLabel();
        imgAvatar = new mvc.view.generalUI.containers.ImageContainer();
        lblID = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(325, 69));
        setMinimumSize(new java.awt.Dimension(10, 69));
        setPreferredSize(new java.awt.Dimension(10, 69));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        lblName.setText("Name");

        javax.swing.GroupLayout imgAvatarLayout = new javax.swing.GroupLayout(imgAvatar);
        imgAvatar.setLayout(imgAvatarLayout);
        imgAvatarLayout.setHorizontalGroup(
            imgAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 69, Short.MAX_VALUE)
        );
        imgAvatarLayout.setVerticalGroup(
            imgAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblID.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imgAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblID))
                .addGap(0, 233, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imgAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblID)
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mvc.view.generalUI.containers.ImageContainer imgAvatar;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblName;
    // End of variables declaration//GEN-END:variables

    public void clear() {
        lblID.setText("");
        lblName.setText("");
        imgAvatar.clear();
    }
}