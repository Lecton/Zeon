/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.message;

import java.util.logging.Logger;
import mvc.controller.ChatControl;

/**
 *
 * @author Bernhard
 */
public class MessagePanel extends javax.swing.JPanel {
    private final static Logger LOGGER = Logger.getLogger(MessagePanel.class.getName());
    
    private String userID ="default";
        
    /**
     * Creates new form MessagePanel
     */
    public MessagePanel() {
        initComponents();
        btnSend.addActionListener(ChatControl.INSTANCE);
    }
    
    public String getMessage() {
        return txtMessage.getText();
    }
    
    public void clearMessage() {
        txtMessage.setText("");
    }

    public String getUserID() {
        return userID;
    }
    
    public void setUserID(String ownerID, String name, boolean owner) {
        messages.clear();
        btnToggleContent.setText(name+"'s Messages");
        this.userID =ownerID;
        if (owner) {
            btnSend.setActionCommand("sendMessageAll");
        } else {
            btnSend.setActionCommand("sendMessage");
        }
    }
    
    public void setUserName(String name) {
        btnToggleContent.setText(name+"'s Messages");
    }
    
    public void addMessage(int ID, boolean owner, String name, String timestamp, String message) {
        messages.addMessage(ID, owner, name, timestamp, message);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMessage = new javax.swing.JTextField();
        btnSend = new mvc.view.generalUI.Button();
        messageList = new javax.swing.JScrollPane();
        messages = new mvc.view.generalUI.message.MessageList();
        btnToggleContent = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(32767, 500));
        setPreferredSize(new java.awt.Dimension(312, 500));

        btnSend.setText("Send");
        btnSend.setActionCommand("sendMessageAll");

        messageList.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        messages.setBackground(new java.awt.Color(204, 204, 204));
        messageList.setViewportView(messages);

        btnToggleContent.setText("Group Messages");
        btnToggleContent.setToolTipText("Click to switch to Profile");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(messageList, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(btnToggleContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnToggleContent)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(messageList, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mvc.view.generalUI.Button btnSend;
    private javax.swing.JButton btnToggleContent;
    private javax.swing.JScrollPane messageList;
    private mvc.view.generalUI.message.MessageList messages;
    private javax.swing.JTextField txtMessage;
    // End of variables declaration//GEN-END:variables
}
