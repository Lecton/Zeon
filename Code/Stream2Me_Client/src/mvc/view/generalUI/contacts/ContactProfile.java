/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.contacts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;
import mvc.controller.ContactControl;

/**
 *
 * @author Bernhard
 */
public class ContactProfile extends javax.swing.JPanel {
    private final static Logger LOGGER = Logger.getLogger(ContactProfile.class.getName());
    
    private ContactList parent;
    private final String userID;
    
    /**
     * Creates new form ContactProfile
     */
    public ContactProfile(String userID) {
        this.userID =userID;
        initComponents();
        setComponentPopupMenu(new ContactPopup(this));
    }

    public void setProfile(String fullname, String avatar) {
        lblName.setText(fullname);
        imgAvatar.setImage(avatar, true);
    }
    
    public void setFullname(String fullname) {
        lblName.setText(fullname);
    }
    
    public void setAvatar(String avatar) {
        imgAvatar.setImage(avatar, true);
    }
    
    public String getUserID() {
        return userID;
    }
    
    public void setParent(ContactList parent) {
        this.parent =parent;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contactList1 = new mvc.view.generalUI.contacts.ContactList();
        imgAvatar = new mvc.view.generalUI.containers.ImageContainer();
        lblName = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        ntiMessage = new mvc.view.generalUI.containers.NotificationIcon();
        ntiAudio = new mvc.view.generalUI.containers.NotificationIcon();
        ntiVideo = new mvc.view.generalUI.containers.NotificationIcon();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(400, 69));
        setMinimumSize(new java.awt.Dimension(100, 69));
        setPreferredSize(new java.awt.Dimension(300, 69));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        add(imgAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lblName.setText("Name");
        add(lblName, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 21, -1, -1));

        lblID.setText("ID");
        add(lblID, new org.netbeans.lib.awtextra.AbsoluteConstraints(73, 46, -1, -1));

        ntiMessage.setMaximumSize(new java.awt.Dimension(15, 15));
        ntiMessage.setMinimumSize(new java.awt.Dimension(15, 15));
        ntiMessage.setPreferredSize(new java.awt.Dimension(15, 15));
        ntiMessage.setType(mvc.view.generalUI.containers.NotificationIcon.iconType.MESSAGE);

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

        add(ntiMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 5, -1, -1));

        ntiAudio.setMaximumSize(new java.awt.Dimension(15, 15));
        ntiAudio.setMinimumSize(new java.awt.Dimension(15, 15));
        ntiAudio.setPreferredSize(new java.awt.Dimension(15, 15));
        ntiAudio.setType(mvc.view.generalUI.containers.NotificationIcon.iconType.AUDIO);

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

        add(ntiAudio, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 5, -1, -1));

        ntiVideo.setMaximumSize(new java.awt.Dimension(15, 15));
        ntiVideo.setMinimumSize(new java.awt.Dimension(15, 15));
        ntiVideo.setPreferredSize(new java.awt.Dimension(15, 15));
        ntiVideo.setType(mvc.view.generalUI.containers.NotificationIcon.iconType.VIDEO);

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

        add(ntiVideo, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 5, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void showPopup(MouseEvent evt) {
        if (evt.isPopupTrigger()) {
            getComponentPopupMenu().show(this, evt.getX(), evt.getY());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mvc.view.generalUI.contacts.ContactList contactList1;
    private mvc.view.generalUI.containers.ImageContainer imgAvatar;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblName;
    private mvc.view.generalUI.containers.NotificationIcon ntiAudio;
    private mvc.view.generalUI.containers.NotificationIcon ntiMessage;
    private mvc.view.generalUI.containers.NotificationIcon ntiVideo;
    // End of variables declaration//GEN-END:variables

    public void unselect() {
        setBackground(new Color(255, 255, 255));
        update();
    }
    
    public void select() {
        setBackground(new Color(255,230,153));
        update();
    }
    
    protected void update() {
        revalidate();
        repaint();
    }
    
    

//    public void showMessages() {
//        parent.showMessages(this);
//    }
//    
//    public void showProfile() {
//        parent.showProfile(this);
//    }
//
//    public boolean hasVideoStream() {
//        return !videoStreamID.isEmpty();
//    }
//
//    public boolean hasAudioStream() {
//        return !audioStreamID.isEmpty();
//    }
//
//    public String getVideoStreamID() {
//        return videoStreamID;
//    }
//
//    public String getAudioStreamID() {
//        return audioStreamID;
//    }
//    
//    public void setIncoming(boolean incoming, String streamID) {
//        switch (MessageUtils.getStreamType(streamID)) {
//            case AUDIO:
//                incomingAudio(incoming, streamID);
//                break;
//            case VIDEO:
//                incomingVideo(incoming, streamID);
//                break;
//            case MEDIA:
//                break;
//            default:
//                Log.error(this.getClass(), "Unknown stream type from steamID: "+streamID);
//        }
//    }
//    
//    private void incomingVideo(boolean incoming, String streamID) {
//        ntiVideo.setVisible(incoming);
//        
//        if (incoming) {
//            videoStreamID =streamID;
//        } else {
//            videoStreamID ="";
//        }
//    }
//    
//    private void incomingAudio(boolean incoming, String streamID) {
//        ntiAudio.setVisible(incoming);
//        
//        if (incoming) {
//            audioStreamID =streamID;
//        } else {
//            audioStreamID ="";
//        }
//    }
//    
//    public void notifyVideo(boolean notification) {
//        if (hasVideoStream()) {
//            ntiVideo.setVisible(notification);
//        }
//    }
//    
//    public void notifyAudio(boolean notification) {
//        if (hasAudioStream()) {
//            ntiAudio.setVisible(notification);
//        }
//    }
//    
//    public void notifyMessageView() {
//        ntiMessage.setVisible(false);
//    }
//
//    void setStreamVideo(boolean outgoing) {
//        videoOut =outgoing;
//    }
//
//    void setStreamAudio(boolean outgoing) {
//        audioOut =outgoing;
//    }
//
//    public boolean isAudioOut() {
//        return audioOut;
//    }
//
//    public boolean isVideoOut() {
//        return videoOut;
//    }
//
//    public void setVideoOut(boolean videoOut) {
//        this.videoOut = videoOut;
//    }
//
//    public void setAudioOut(boolean audioOut) {
//        this.audioOut = audioOut;
//    }
//
//    public void updateAvatar(final String avatar) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                imgAvatar.setImage(avatar, true);
//                profile.setAvatar(avatar);
//            }
//        }).start();
//    }

    @Override
    public void paint(Graphics g) {
        ntiVideo.setVisible(ContactControl.hasVideoNotify(userID));
        ntiAudio.setVisible(ContactControl.hasAudioNotify(userID));
        ntiMessage.setVisible(ContactControl.hasMessageNotify(userID));
        
        super.paint(g);
    }
}