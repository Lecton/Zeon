/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.controller.generalUI.ProfileControl;

/**
 *
 * @author Bernhard
 */
public class ProfilePanel extends javax.swing.JPanel {
    private final static Logger LOGGER = Logger.getLogger(ProfilePanel.class.getName());
    
    private boolean owner;
    private String userID ="";

    /**
     * Creates new form ProfilePanel
     */
    public ProfilePanel() {
        initComponents();
        btnUpdateDetails.addActionListener(ProfileControl.INSTANCE);
    }
    
    public void setUserID(String userID, String name, boolean owner) {
        this.owner =owner;
        if (owner) {
            btnUpdateDetails.setActionCommand("updateUserDetails");
        } else {
            btnUpdateDetails.setActionCommand("updateDetails");
        }
        this.userID =userID;
        
        txtName.setEditable(owner);
        txtSurname.setEditable(owner);
        txtEmail.setEditable(owner);
        txtTitle.setEditable(owner);
        txaAboutMe.setEditable(owner);
        btnUpdateDetails.setVisible(owner);
        
        this.revalidate();
        this.repaint();
    }
    
    public String getUserID() {
        return userID;
    }

    public void setProfile(String name, String surname, String email, String avatar, String title, String aboutMe) {
        txtName.setText(name);
        txtSurname.setText(surname);
        txtEmail.setText(email);
        txtTitle.setText(title);

        imgProfilePic.setImage(avatar, false);
        txaAboutMe.setText(aboutMe);
    }

    public String getFirstname() {
        return txtName.getText();
    }
    
    public String getSurname() {
        return txtSurname.getText();
    }
    
    public String getEmail() {
        return txtEmail.getText();
    }
    
    public String getTitle() {
        return txtTitle.getText();
    }
    
    public String getAboutMe() {
        return txaAboutMe.getText();
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
        lblSurname = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtSurname = new javax.swing.JTextField();
        btnUpdateDetails = new javax.swing.JButton();
        imgProfilePic = new mvc.view.generalUI.containers.ImageContainer();
        txtEmail = new javax.swing.JTextField();
        lblTitle = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        lblAboutMe = new javax.swing.JLabel();
        txaAboutMe = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(255, 255, 255));
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });

        lblName.setText("Name");

        lblSurname.setText("Surname");

        lblEmail.setText("Email");

        txtName.setText("Name");

        txtSurname.setText("Surname");

        btnUpdateDetails.setText("Update Details");
        btnUpdateDetails.setActionCommand("updateUserDetails");

        imgProfilePic.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgProfilePicMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout imgProfilePicLayout = new javax.swing.GroupLayout(imgProfilePic);
        imgProfilePic.setLayout(imgProfilePicLayout);
        imgProfilePicLayout.setHorizontalGroup(
            imgProfilePicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        imgProfilePicLayout.setVerticalGroup(
            imgProfilePicLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        txtEmail.setText("Email");

        lblTitle.setText("Title");

        txtTitle.setText("Title");

        lblAboutMe.setText("About Me");

        txaAboutMe.setColumns(1);
        txaAboutMe.setLineWrap(true);
        txaAboutMe.setRows(4);
        txaAboutMe.setText("About Me");
        txaAboutMe.setWrapStyleWord(true);
        txaAboutMe.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        txaAboutMe.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txaAboutMe.setMaximumSize(new java.awt.Dimension(260, 1000));
        txaAboutMe.setMinimumSize(new java.awt.Dimension(260, 64));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(imgProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                        .addComponent(lblSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                        .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                        .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                        .addComponent(lblAboutMe)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txaAboutMe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdateDetails, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(imgProfilePic, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSurname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAboutMe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txaAboutMe, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUpdateDetails)
                .addContainerGap())
        );

        txaAboutMe.getAccessibleContext().setAccessibleName("");

        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
        LOGGER.log(Level.INFO, "property: "+evt.getPropertyName());
    }//GEN-LAST:event_formPropertyChange

    private void imgProfilePicMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgProfilePicMouseClicked
        if (owner) {
            BufferedImage image =ProfileControl.INSTANCE.imgProfilePicMouseClicked(evt, owner, userID);
            if (image != null) {
                imgProfilePic.setImage(image, true);
            }
        }
    }//GEN-LAST:event_imgProfilePicMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUpdateDetails;
    private mvc.view.generalUI.containers.ImageContainer imgProfilePic;
    private javax.swing.JLabel lblAboutMe;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextArea txaAboutMe;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSurname;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables

    public void clear() {
        txtName.setText("Name");
        txtSurname.setText("Surname");
        txtEmail.setText("Email");
//        imgProfilePicture.setImage(avatar, false);
        txtTitle.setText("Title");

        lblName.setText("Name");
        lblSurname.setText("Surname");
        lblEmail.setText("Email");
        lblTitle.setText("Title");
        txaAboutMe.setText("AboutMe");
    }
}
