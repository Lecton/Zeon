/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface.generalUI.contacts;

import client.Colleague;
import java.awt.Color;
import java.awt.event.MouseEvent;
import messageUtils.MessageUtils;
import utils.Log;

/**
 *
 * @author Bernhard
 */
public class ContactProfile extends javax.swing.JPanel {
    private Colleague profile;
    private ContactList parent;
    
    private boolean videoOut =false;
    private boolean audioOut =false;
    
    private String videoStreamID ="";
    private String audioStreamID ="";

    /**
     * Creates new form ContactProfile
     */
    public ContactProfile() {
        initComponents();
        setComponentPopupMenu(new ContactPopup(this));
        
        ntiMessage.setVisible(false);
        ntiAudio.setVisible(false);
        ntiVideo.setVisible(false);
    }

    public void setProfile(Colleague profile) {
        this.profile = profile;
        lblName.setText(profile.getFullName());
        lblID.setText(profile.getUserID()+"");
        
        imgAvatar.setImage(profile.getAvatar(), true);
    }

    public Colleague getProfile() {
        return profile;
    }
    
    public int getUserID() {
        return profile.getUserID();
    }
    
    public void setParent(ContactList parent) {
        this.parent =parent;
    }

    public ContactList getParent() {
        return parent; //To change body of generated methods, choose Tools | Templates.
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
        lblName = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        ntiMessage = new userInterface.generalUI.NotificationIcon();
        ntiAudio = new userInterface.generalUI.NotificationIcon();
        ntiVideo = new userInterface.generalUI.NotificationIcon();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(335, 69));
        setMinimumSize(new java.awt.Dimension(335, 69));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        imgAvatar.setMaximumSize(new java.awt.Dimension(69, 69));
        imgAvatar.setMinimumSize(new java.awt.Dimension(69, 69));
        imgAvatar.setPreferredSize(new java.awt.Dimension(69, 69));

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

        lblName.setText("Name");

        lblID.setText("ID");

        ntiMessage.setMaximumSize(new java.awt.Dimension(15, 15));
        ntiMessage.setMinimumSize(new java.awt.Dimension(15, 15));
        ntiMessage.setPreferredSize(new java.awt.Dimension(15, 15));
        ntiMessage.setType(userInterface.generalUI.NotificationIcon.iconType.MESSAGE);

        javax.swing.GroupLayout ntiMessageLayout = new javax.swing.GroupLayout(ntiMessage);
        ntiMessage.setLayout(ntiMessageLayout);
        ntiMessageLayout.setHorizontalGroup(
            ntiMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        ntiMessageLayout.setVerticalGroup(
            ntiMessageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        ntiAudio.setMaximumSize(new java.awt.Dimension(15, 15));
        ntiAudio.setMinimumSize(new java.awt.Dimension(15, 15));
        ntiAudio.setPreferredSize(new java.awt.Dimension(15, 15));
        ntiAudio.setType(userInterface.generalUI.NotificationIcon.iconType.AUDIO);

        javax.swing.GroupLayout ntiAudioLayout = new javax.swing.GroupLayout(ntiAudio);
        ntiAudio.setLayout(ntiAudioLayout);
        ntiAudioLayout.setHorizontalGroup(
            ntiAudioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        ntiAudioLayout.setVerticalGroup(
            ntiAudioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        ntiVideo.setMaximumSize(new java.awt.Dimension(15, 15));
        ntiVideo.setMinimumSize(new java.awt.Dimension(15, 15));
        ntiVideo.setPreferredSize(new java.awt.Dimension(15, 15));
        ntiVideo.setType(userInterface.generalUI.NotificationIcon.iconType.VIDEO);

        javax.swing.GroupLayout ntiVideoLayout = new javax.swing.GroupLayout(ntiVideo);
        ntiVideo.setLayout(ntiVideoLayout);
        ntiVideoLayout.setHorizontalGroup(
            ntiVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );
        ntiVideoLayout.setVerticalGroup(
            ntiVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imgAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblID)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addComponent(ntiVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ntiAudio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ntiMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(imgAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(ntiMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ntiVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ntiAudio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(lblID)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        parent.mouseClicked(evt, this);
    }//GEN-LAST:event_formMouseClicked

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        
    }//GEN-LAST:event_formMousePressed

    private void showPopup(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            getComponentPopupMenu().show(this, evt.getX(), evt.getY());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private userInterface.generalUI.ImageContainer imgAvatar;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblName;
    private userInterface.generalUI.NotificationIcon ntiAudio;
    private userInterface.generalUI.NotificationIcon ntiMessage;
    private userInterface.generalUI.NotificationIcon ntiVideo;
    // End of variables declaration//GEN-END:variables

    public void unselect() {
        setBackground(new Color(255, 255, 255));
        update();
    }
    
    public void select() {
        setBackground(new Color(255,230,153));
        update();
    }
    
    private void update() {
        revalidate();
        repaint();
    }

    public void showMessages() {
        parent.showMessages(this);
    }
    
    public void showProfile() {
        parent.showProfile(this);
    }

    public boolean hasVideoStream() {
        return !videoStreamID.isEmpty();
    }

    public boolean hasAudioStream() {
        return !audioStreamID.isEmpty();
    }

    public String getVideoStreamID() {
        return videoStreamID;
    }

    public String getAudioStreamID() {
        return audioStreamID;
    }
    
    public void setIncoming(boolean incoming, String streamID) {
        switch (MessageUtils.getStreamType(streamID)) {
            case AUDIO:
                incomingAudio(incoming, streamID);
                break;
            case VIDEO:
                incomingVideo(incoming, streamID);
                break;
            case MEDIA:
                break;
            default:
                Log.error(this.getClass(), "Unknown stream type from steamID: "+streamID);
        }
    }
    
    private void incomingVideo(boolean incoming, String streamID) {
        ntiVideo.setVisible(incoming);
        
        if (incoming) {
            videoStreamID =streamID;
        } else {
            videoStreamID ="";
        }
    }
    
    private void incomingAudio(boolean incoming, String streamID) {
        ntiAudio.setVisible(incoming);
        
        if (incoming) {
            audioStreamID =streamID;
        } else {
            audioStreamID ="";
        }
    }
    
    public void notifyVideo(boolean notification) {
        if (hasVideoStream()) {
            ntiVideo.setVisible(notification);
        }
    }
    
    public void notifyAudio(boolean notification) {
        if (hasAudioStream()) {
            ntiAudio.setVisible(notification);
        }
    }
    
    public void notifyMessageView() {
        ntiMessage.setVisible(false);
    }

    void setStreamVideo(boolean outgoing) {
        videoOut =outgoing;
    }

    void setStreamAudio(boolean outgoing) {
        audioOut =outgoing;
    }

    public boolean isAudioOut() {
        return audioOut;
    }

    public boolean isVideoOut() {
        return videoOut;
    }

    public void setVideoOut(boolean videoOut) {
        this.videoOut = videoOut;
    }

    public void setAudioOut(boolean audioOut) {
        this.audioOut = audioOut;
    }

    public void updateAvatar(final String avatar) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                imgAvatar.setImage(avatar, true);
                profile.setAvatar(avatar);
            }
        }).start();
    }

}