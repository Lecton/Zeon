/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;
import mvc.controller.ContactListControl;
import mvc.controller.Control;
import mvc.controller.GUIControl;
import mvc.controller.UserControl;
import mvc.view.generalUI.contacts.ContactList;

/**
 *
 * @author Bernhard
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        
        setupGUI();
    }
    
    private void printBounds(String name, Rectangle r) {
        System.out.println(name+": ");
        System.out.println("\tX: "+r.x);
        System.out.println("\tY: "+r.y);
        System.out.println("\tWidth: "+r.width);
        System.out.println("\tHeight: "+r.height);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftArrow = new mvc.view.generalUI.HideArrow();
        rightArrow = new mvc.view.generalUI.HideArrow();
        controls = new javax.swing.JPanel();
        streamVideo = new mvc.view.generalUI.Button();
        streamAudio = new mvc.view.generalUI.Button();
        acceptAudio = new mvc.view.generalUI.Button();
        acceptVideo = new mvc.view.generalUI.Button();
        logout = new mvc.view.generalUI.Button();
        settings = new mvc.view.generalUI.Button();
        people = new javax.swing.JPanel();
        user = new mvc.view.generalUI.UserPanel();
        contactsScroll = new javax.swing.JScrollPane();
        contacts = new mvc.view.generalUI.contacts.ContactList();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Stream2Me");
        setResizable(false);

        leftArrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftArrowActionPerformed(evt);
            }
        });

        rightArrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightArrowActionPerformed(evt);
            }
        });

        controls.setBackground(new java.awt.Color(255, 255, 255));
        controls.setMaximumSize(new java.awt.Dimension(62, 500));
        controls.setMinimumSize(new java.awt.Dimension(62, 500));
        controls.setName(""); // NOI18N
        controls.setPreferredSize(new java.awt.Dimension(62, 500));

        streamVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/streaming_Icons/unclicked-camera.png"))); // NOI18N
        streamVideo.setToolTipText("");
        streamVideo.setActionCommand("streamVideo");
        streamVideo.setMargin(new java.awt.Insets(2, 2, 2, 2));
        streamVideo.setMaximumSize(new java.awt.Dimension(58, 58));
        streamVideo.setMinimumSize(new java.awt.Dimension(58, 58));
        streamVideo.setName(""); // NOI18N
        streamVideo.setPreferredSize(new java.awt.Dimension(58, 58));

        streamAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/streaming_Icons/unclicked-microphone.png"))); // NOI18N
        streamAudio.setActionCommand("streamAudio");
        streamAudio.setMargin(new java.awt.Insets(2, 2, 2, 2));
        streamAudio.setMaximumSize(new java.awt.Dimension(58, 58));
        streamAudio.setMinimumSize(new java.awt.Dimension(58, 58));
        streamAudio.setName(""); // NOI18N
        streamAudio.setPreferredSize(new java.awt.Dimension(58, 58));

        acceptAudio.setMargin(new java.awt.Insets(2, 2, 2, 2));
        acceptAudio.setMaximumSize(new java.awt.Dimension(58, 58));
        acceptAudio.setMinimumSize(new java.awt.Dimension(58, 58));
        acceptAudio.setPreferredSize(new java.awt.Dimension(58, 58));

        acceptVideo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/notification_Icons/video_icon.png"))); // NOI18N
        acceptVideo.setMargin(new java.awt.Insets(2, 2, 2, 2));
        acceptVideo.setMaximumSize(new java.awt.Dimension(58, 58));
        acceptVideo.setMinimumSize(new java.awt.Dimension(58, 58));
        acceptVideo.setPreferredSize(new java.awt.Dimension(58, 58));

        logout.setMargin(new java.awt.Insets(2, 2, 2, 2));
        logout.setMaximumSize(new java.awt.Dimension(58, 58));
        logout.setMinimumSize(new java.awt.Dimension(58, 58));
        logout.setPreferredSize(new java.awt.Dimension(58, 58));

        settings.setMargin(new java.awt.Insets(2, 2, 2, 2));
        settings.setMaximumSize(new java.awt.Dimension(58, 58));
        settings.setMinimumSize(new java.awt.Dimension(58, 58));
        settings.setPreferredSize(new java.awt.Dimension(58, 58));

        javax.swing.GroupLayout controlsLayout = new javax.swing.GroupLayout(controls);
        controls.setLayout(controlsLayout);
        controlsLayout.setHorizontalGroup(
            controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlsLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(acceptVideo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(streamVideo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(streamAudio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(acceptAudio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(settings, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 2, 2))
        );
        controlsLayout.setVerticalGroup(
            controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(streamVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(streamAudio, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(acceptVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(acceptAudio, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(settings, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );

        user.setBackground(new java.awt.Color(255, 255, 255));

        contactsScroll.setBackground(new java.awt.Color(255, 255, 255));
        contactsScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contactsScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        contactsScroll.setViewportView(contacts);

        javax.swing.GroupLayout peopleLayout = new javax.swing.GroupLayout(people);
        people.setLayout(peopleLayout);
        peopleLayout.setHorizontalGroup(
            peopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contactsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        peopleLayout.setVerticalGroup(
            peopleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peopleLayout.createSequentialGroup()
                .addComponent(user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contactsScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE))
        );

        content.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 312, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(leftArrow, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(controls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(people, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(rightArrow, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftArrow, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(rightArrow, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(controls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(people, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(content, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void leftArrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftArrowActionPerformed
        Point p =getLocation();
        Dimension currentSize =getSize();
        if (leftArrow.getDependant().isVisible()) {
            leftArrow.getDependant().setVisible(false);
            currentSize.width -=leftArrow.getDependant().getWidth();
            p.x +=leftArrow.getDependant().getWidth();
        } else {
            leftArrow.getDependant().setVisible(true);
            leftArrow.getDependant().validate();
            currentSize.width +=leftArrow.getDependant().getPreferredSize().width;
            p.x -=leftArrow.getDependant().getPreferredSize().width;
            if (p.x < 0) {
                p.x =0;
            }
        }

        setLocation(p);
        setPreferredSize(currentSize);
        setSize(currentSize);
    }//GEN-LAST:event_leftArrowActionPerformed

    private void rightArrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightArrowActionPerformed
        Dimension currentSize =getSize();
        if (rightArrow.getDependant().isVisible()) {
            rightArrow.getDependant().setVisible(false);
            currentSize.width -=rightArrow.getDependant().getWidth();
        } else {
            rightArrow.getDependant().setVisible(true);
            rightArrow.getDependant().validate();
            currentSize.width +=rightArrow.getDependant().getPreferredSize().width;
        }
        
        setSize(currentSize);
    }//GEN-LAST:event_rightArrowActionPerformed

    private void setupGUI() {
        leftArrow.setPosition(controls, true);
        rightArrow.setPosition(content, false);
        
        addWindowListener(new GUIControl());
        
        streamVideo.addActionListener(new UserControl());
        streamAudio.addActionListener(new UserControl());
    }

    public ContactList getContactList() {
        return contacts;
    }

    public UserPanel getUserPanel() {
        return user;
    }

    public JPanel getContentPanel() {
        return content;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private mvc.view.generalUI.Button acceptAudio;
    private mvc.view.generalUI.Button acceptVideo;
    private mvc.view.generalUI.contacts.ContactList contacts;
    private javax.swing.JScrollPane contactsScroll;
    private javax.swing.JPanel content;
    private javax.swing.JPanel controls;
    private mvc.view.generalUI.HideArrow leftArrow;
    private mvc.view.generalUI.Button logout;
    private javax.swing.JPanel people;
    private mvc.view.generalUI.HideArrow rightArrow;
    private mvc.view.generalUI.Button settings;
    private mvc.view.generalUI.Button streamAudio;
    private mvc.view.generalUI.Button streamVideo;
    private mvc.view.generalUI.UserPanel user;
    // End of variables declaration//GEN-END:variables
}