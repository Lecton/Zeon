/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface.generalUI;

import client.Colleague;
import utils.MessageFactory;

/**
 *
 * @author Bernhard
 */
public class ProfilePanel extends javax.swing.JPanel {
    private boolean editable;
    private Colleague profile;
    private GUI userInterface;

    /**
     * Creates new form ProfilePanel
     */
    public ProfilePanel() {
        initComponents();
        txtName.setVisible(false);
        txtSurname.setVisible(false);
        txtEmail.setVisible(false);
        btnUpdateDetails.setVisible(false);
    }

    public void setProfile(Colleague profile) {
        this.profile = profile;
        this.lblEmail.setText(profile.getEmail());
        this.lblName.setText(profile.getName());
        this.lblSurname.setText(profile.getSurname());
        this.imgProfilePicture.setImage(profile.getAvatar(), false);
        
        if (editable) {
            txtName.setText(profile.getName());
            txtSurname.setText(profile.getSurname());
            txtEmail.setText(profile.getEmail());
            
            this.lblName.setText("Name");
            this.lblSurname.setText("Surname");
            this.lblEmail.setText("Email");
            this.imgProfilePicture.setImage(profile.getAvatar(), false);
        }
    }
    
    public void setUserInterface(GUI userInterface) {
        this.userInterface= userInterface;
    }
    
    public void setEditable(boolean editable) {
        this.editable =editable;
        txtName.setVisible(editable);
        txtSurname.setVisible(editable);
        txtEmail.setVisible(editable);
        btnUpdateDetails.setVisible(editable);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imgProfilePicture = new userInterface.generalUI.ImageContainer();
        lblName = new javax.swing.JLabel();
        lblSurname = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtSurname = new javax.swing.JTextField();
        txtEmail = new javax.swing.JLabel();
        btnUpdateDetails = new javax.swing.JButton();

        imgProfilePicture.setMaximumSize(new java.awt.Dimension(300, 300));
        imgProfilePicture.setMinimumSize(new java.awt.Dimension(300, 300));
        imgProfilePicture.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout imgProfilePictureLayout = new javax.swing.GroupLayout(imgProfilePicture);
        imgProfilePicture.setLayout(imgProfilePictureLayout);
        imgProfilePictureLayout.setHorizontalGroup(
            imgProfilePictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imgProfilePictureLayout.setVerticalGroup(
            imgProfilePictureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        lblName.setText("Name");

        lblSurname.setText("Surname");

        lblEmail.setText("Email");

        txtName.setText("Name");

        txtSurname.setText("Surname");

        txtEmail.setText("Email");

        btnUpdateDetails.setText("Update Details");
        btnUpdateDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgProfilePicture, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtName)
                    .addComponent(txtSurname)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName)
                            .addComponent(lblSurname)
                            .addComponent(lblEmail))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnUpdateDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgProfilePicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblName)
                .addGap(4, 4, 4)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSurname)
                .addGap(2, 2, 2)
                .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail)
                .addGap(40, 40, 40)
                .addComponent(btnUpdateDetails)
                .addContainerGap(219, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDetailsActionPerformed
        if (editable) {
            userInterface.getConnection().writeSafe(MessageFactory.generateUpdateName(profile.getUserID(), txtName.getText(), txtSurname.getText()));
        }
    }//GEN-LAST:event_btnUpdateDetailsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUpdateDetails;
    private userInterface.generalUI.ImageContainer imgProfilePicture;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JLabel txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables
}
