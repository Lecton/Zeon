package client.GUI;

import MediaStreaming.Audio.AudioCapture;
import MediaStreaming.Video.ScreenCapture;
import MediaStreaming.Video.StreamVideo;
import Messages.ClientInit;
import Messages.StringMessage;
import Messages.VideoStream;
import client.Client;
import client.Connection;
import client.inStream;
import java.awt.Color;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lecton
 * @author Bernhard
 * @author Zenadia
 */
public class GUI extends JFrame {
    public enum Action {REMOVE, ADD, UPDATE}
    
    private Connection con =null;
    private inStream input =null;
    private Client client =null;
    private AudioCapture ac;
    private StreamVideo sv;
    
    public String name ="User";
    public int ID =-1;

    /**
     * Creates new form GUI
     */
    public GUI(String name, int PORT, Client client) {
        this.client =client;
        this.name =name;
        con =new Connection();
        con.setPORT(PORT);
        
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        MainSplit.setBackground(Color.black);
        MainSplit.setBorder(null);
//        this.repaint();
//        this.update(UserControls.getGraphics());
        
        setTitle("Stream2Me: "+name);
        
        input = new inStream(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DetailsPanel = new javax.swing.JPanel();
        MainSplit = new javax.swing.JSplitPane();
        ControlPanel = new javax.swing.JPanel();
        UserControls = new javax.swing.JPanel();
        videoButton1 = new client.GUI.VideoButton();
        audioButton1 = new client.GUI.AudioButton();
        streamButton1 = new client.GUI.StreamButton();
        ProfilePic = new javax.swing.JLabel();
        ContactPane = new client.GUI.Contacts(this);
        InterfacePanel = new javax.swing.JPanel();
        InterfaceSplit = new javax.swing.JSplitPane();
        ChatPanel = new javax.swing.JPanel();
        chatSend = new javax.swing.JButton();
        chatText = new java.awt.TextField();
        chatMessages = new client.GUI.ChatArea(this);
        jPanel1 = new javax.swing.JPanel();
        imgBlock = new javax.swing.JLabel();
        mainMenu = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuConnect = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();

        javax.swing.GroupLayout DetailsPanelLayout = new javax.swing.GroupLayout(DetailsPanel);
        DetailsPanel.setLayout(DetailsPanelLayout);
        DetailsPanelLayout.setHorizontalGroup(
            DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 758, Short.MAX_VALUE)
        );
        DetailsPanelLayout.setVerticalGroup(
            DetailsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        MainSplit.setBackground(new java.awt.Color(255, 255, 255));
        MainSplit.setBorder(null);
        MainSplit.setDividerLocation(230);
        MainSplit.setDividerSize(1);
        MainSplit.setForeground(new java.awt.Color(255, 255, 255));
        MainSplit.setMinimumSize(new java.awt.Dimension(230, 3));
        MainSplit.setBackground(Color.black);
        MainSplit.setBorder(null);

        ControlPanel.setBackground(new java.awt.Color(255, 255, 255));
        ControlPanel.setForeground(new java.awt.Color(255, 255, 255));

        UserControls.setBackground(new java.awt.Color(255, 255, 255));
        UserControls.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 85, 0)));
        UserControls.setForeground(new java.awt.Color(255, 255, 255));

        videoButton1.setBackground(new java.awt.Color(255, 255, 255));
        videoButton1.createButton();
        videoButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                videoButton1ActionPerformed(evt);
            }
        });

        audioButton1.setBackground(new java.awt.Color(255, 255, 255));
        audioButton1.createButton();
        audioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audioButton1ActionPerformed(evt);
            }
        });

        streamButton1.setBackground(new java.awt.Color(255, 255, 255));
        streamButton1.createButton();

        ProfilePic.setBackground(new java.awt.Color(255, 255, 255));
        ProfilePic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/default_profile.png"))); // NOI18N
        ProfilePic.setToolTipText("");

        javax.swing.GroupLayout UserControlsLayout = new javax.swing.GroupLayout(UserControls);
        UserControls.setLayout(UserControlsLayout);
        UserControlsLayout.setHorizontalGroup(
            UserControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(UserControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(UserControlsLayout.createSequentialGroup()
                        .addComponent(audioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(videoButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(streamButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ProfilePic, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))
                .addContainerGap())
        );
        UserControlsLayout.setVerticalGroup(
            UserControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ProfilePic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(streamButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(videoButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(audioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        ContactPane.setBackground(new java.awt.Color(255, 255, 255));
        ContactPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 85, 0)));
        ContactPane.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ControlPanelLayout = new javax.swing.GroupLayout(ControlPanel);
        ControlPanel.setLayout(ControlPanelLayout);
        ControlPanelLayout.setHorizontalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ContactPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UserControls, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ControlPanelLayout.setVerticalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addComponent(UserControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(ContactPane, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        MainSplit.setLeftComponent(ControlPanel);

        InterfaceSplit.setBackground(new java.awt.Color(255, 255, 255));
        InterfaceSplit.setBorder(javax.swing.BorderFactory.createCompoundBorder(null, javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 85, 0))));
        InterfaceSplit.setDividerLocation(400);
        InterfaceSplit.setDividerSize(1);
        InterfaceSplit.setForeground(new java.awt.Color(255, 255, 255));
        InterfaceSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        InterfaceSplit.setMinimumSize(new java.awt.Dimension(1, 7));

        ChatPanel.setBackground(new java.awt.Color(255, 255, 255));

        chatSend.setBackground(new java.awt.Color(255, 255, 255));
        chatSend.setForeground(new java.awt.Color(255, 255, 255));
        chatSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/submit.png"))); // NOI18N
        chatSend.setToolTipText("");
        chatSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatSendActionPerformed(evt);
            }
        });

        chatText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        chatText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatTextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ChatPanelLayout = new javax.swing.GroupLayout(ChatPanel);
        ChatPanel.setLayout(ChatPanelLayout);
        ChatPanelLayout.setHorizontalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelLayout.createSequentialGroup()
                .addComponent(chatText, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chatSend, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(chatMessages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ChatPanelLayout.setVerticalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelLayout.createSequentialGroup()
                .addComponent(chatMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addGroup(ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chatSend)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChatPanelLayout.createSequentialGroup()
                        .addComponent(chatText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        InterfaceSplit.setBottomComponent(ChatPanel);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        imgBlock.setBackground(new java.awt.Color(255, 255, 255));
        imgBlock.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgBlock, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgBlock, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addContainerGap())
        );

        InterfaceSplit.setLeftComponent(jPanel1);

        javax.swing.GroupLayout InterfacePanelLayout = new javax.swing.GroupLayout(InterfacePanel);
        InterfacePanel.setLayout(InterfacePanelLayout);
        InterfacePanelLayout.setHorizontalGroup(
            InterfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(InterfaceSplit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        InterfacePanelLayout.setVerticalGroup(
            InterfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(InterfaceSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MainSplit.setRightComponent(InterfacePanel);

        menuFile.setText("File");

        menuConnect.setText("Connect");
        menuConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuConnectActionPerformed(evt);
            }
        });
        menuFile.add(menuConnect);

        mainMenu.add(menuFile);

        menuEdit.setText("Edit");
        mainMenu.add(menuEdit);

        setJMenuBar(mainMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConnectActionPerformed
        try {
            con.makeConnection();
            con.write(new ClientInit(name));
            input.setInputStream(con.getInputStream());
            ac = new AudioCapture(con.getOutputStream(),this.name,this.ID);
            sv =new StreamVideo(new VideoStream(this.name, this.ID, -1, -1), 1, new ScreenCapture(), con.getOutputStream());
            (new Thread(input)).start();
            menuConnect.setEnabled(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_menuConnectActionPerformed

    private void chatSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatSendActionPerformed

        if(!chatText.getText().isEmpty()){
            StringMessage sm = new StringMessage(name +" :\t"+ chatText.getText() + "\n", ID);
            sm.to = ContactPane.getSelectedID();
            if (sm.to != -2) {
                con.write(sm);
                ContactPane.acceptMessage(sm);
            }
        }
        chatText.setText("");
    }//GEN-LAST:event_chatSendActionPerformed

    private void videoButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_videoButton1ActionPerformed
        if(!videoButton1.pressed){
            sv.start();
        }else{
            sv.stop();
        }
    }//GEN-LAST:event_videoButton1ActionPerformed

    private void chatTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chatTextActionPerformed

    private void audioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioButton1ActionPerformed
        try 
        {
            if(!audioButton1.pressed){
                ac.start();
            }else{
                ac.stop();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }//GEN-LAST:event_audioButton1ActionPerformed
    
    public synchronized void setChatHistory(ArrayList<StringMessage> chatHist){
//        chatMessages.setText("");
        chatMessages.setChatMessages(chatHist);
    }
    
    public synchronized void appendChatMessage(StringMessage chatMsg){
        chatMessages.append(chatMsg);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChatPanel;
    public client.GUI.Contacts ContactPane;
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JPanel DetailsPanel;
    private javax.swing.JPanel InterfacePanel;
    private javax.swing.JSplitPane InterfaceSplit;
    private javax.swing.JSplitPane MainSplit;
    public javax.swing.JLabel ProfilePic;
    private javax.swing.JPanel UserControls;
    private client.GUI.AudioButton audioButton1;
    public client.GUI.ChatArea chatMessages;
    private javax.swing.JButton chatSend;
    private java.awt.TextField chatText;
    public javax.swing.JLabel imgBlock;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JMenuItem menuConnect;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private client.GUI.StreamButton streamButton1;
    private client.GUI.VideoButton videoButton1;
    // End of variables declaration//GEN-END:variables
   
}
