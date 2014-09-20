/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI;

/**
 *
 * @author Bernhard
 */
public class ProfilePanel extends javax.swing.JPanel {
    private boolean owner;
    private String userID;

    /**
     * Creates new form ProfilePanel
     */
    public ProfilePanel() {
        initComponents();
    }
    
    public void setUserID(String userID, boolean owner) {
        this.owner =owner;
        this.userID =userID;
        
        txtName.setVisible(owner);
        txtSurname.setVisible(owner);
        txtEmail.setVisible(owner);
        txtTitle.setVisible(owner);
        txaAboutMe.setEditable(owner);
        btnUpdateDetails.setVisible(owner);
        
        this.revalidate();
        this.repaint();
    }
    
    public String getUserID() {
        return userID;
    }

    public void setProfile(String name, String surname, String email, String avatar, String title, String aboutMe) {
        if (owner) {
            txtName.setText(name);
            txtSurname.setText(surname);
            txtEmail.setText(email);
    //        this.imgProfilePicture.setImage(avatar, false);
            txtTitle.setText(title);
            
            lblName.setText("Name");
            lblSurname.setText("Surname");
            lblEmail.setText("Email");
            lblTitle.setText("Title");
        } else {
            lblName.setText(name);
            lblSurname.setText(surname);
            lblEmail.setText(email);
            lblTitle.setText(title);
    //        this.imgProfilePicture.setImage(avatar, false);
        }
        txaAboutMe.setText(aboutMe);
        txaAboutMe.setText(aboutMe);
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
        imageContainer1 = new mvc.view.generalUI.ImageContainer();
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
        btnUpdateDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateDetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout imageContainer1Layout = new javax.swing.GroupLayout(imageContainer1);
        imageContainer1.setLayout(imageContainer1Layout);
        imageContainer1Layout.setHorizontalGroup(
            imageContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );
        imageContainer1Layout.setVerticalGroup(
            imageContainer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        txtEmail.setText("Email");

        lblTitle.setText("Title");

        txtTitle.setText("Title");

        lblAboutMe.setText("About Me");

        txaAboutMe.setEditable(false);
        txaAboutMe.setColumns(20);
        txaAboutMe.setRows(3);
        txaAboutMe.setWrapStyleWord(true);
        txaAboutMe.setBorder(null);
        txaAboutMe.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txaAboutMe.setFocusable(false);
        txaAboutMe.setRequestFocusEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(imageContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(btnUpdateDetails, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txaAboutMe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(imageContainer1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnUpdateDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateDetailsActionPerformed
//        if (owner) {
//            userInterface.getConnection().writeSafe(MessageFactory.generateUpdateName(profile.getUserID(), txtName.getText(), txtSurname.getText()));
//        }
    }//GEN-LAST:event_btnUpdateDetailsActionPerformed

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
        System.out.println("property: "+evt.getPropertyName());
    }//GEN-LAST:event_formPropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUpdateDetails;
    private mvc.view.generalUI.ImageContainer imageContainer1;
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
}
