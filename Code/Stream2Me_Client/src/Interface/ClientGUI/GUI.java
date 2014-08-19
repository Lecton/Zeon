package Interface.ClientGUI;

import Client.Colleague;
import Connection.Connection;
import Interface.ClientGUI.Contacts.ContactList;
import Interface.ClientGUI.Contacts.UserProfile;
import Utils.MessageFactory;
import java.awt.Color;

public class GUI extends javax.swing.JFrame {
    private Connection con;
    private Client.Client parent;
    private Client.AudioPlayer audioPlayer;
    
    public GUI(Connection con, Colleague me, Client.Client parent) {
        this.parent =parent;
        
        setupBackend(con);
        setupGUI(me);
        
        con.writeSafe(Utils.MessageFactory.generateRefreshListRequest(getUserID()));
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public int getUserID() {
        return userProfileControl.getUserID();
    }

    public UserProfile getUserProfile() {
        return userProfileControl;
    }

    public ContactList getContactList() {
        return contacts;
    }

    public ChatArea getChatArea() {
        return chatMessages;
    }

    public VideoPane getVideoArea() {
        return videoArea;
    }
    
    public Client.AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainSplit = new javax.swing.JSplitPane();
        ControlPanel = new javax.swing.JPanel();
        ContactContainer = new javax.swing.JPanel();
        ContactScroller = new javax.swing.JScrollPane();
        contacts = new Interface.ClientGUI.Contacts.ContactList();
        userProfileControl = new Interface.ClientGUI.Contacts.UserProfile();
        InterfacePanel = new javax.swing.JPanel();
        InterfaceSplit = new javax.swing.JSplitPane();
        ChatPanel = new javax.swing.JPanel();
        chatText = new java.awt.TextField();
        chatMessages = new Interface.ClientGUI.ChatArea();
        submitBtn = new Interface.ClientGUI.Button();
        videoArea = new Interface.ClientGUI.VideoPane();
        mainMenu = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        mmuLogout = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

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

        ContactContainer.setLayout(new java.awt.BorderLayout());

        ContactScroller.setViewportView(contacts);

        javax.swing.GroupLayout ContactContainerLayout = new javax.swing.GroupLayout(ContactContainer);
        ContactContainer.setLayout(ContactContainerLayout);
        ContactContainerLayout.setHorizontalGroup(
            ContactContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ContactScroller)
        );
        ContactContainerLayout.setVerticalGroup(
            ContactContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContactContainerLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(ContactScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ControlPanelLayout = new javax.swing.GroupLayout(ControlPanel);
        ControlPanel.setLayout(ControlPanelLayout);
        ControlPanelLayout.setHorizontalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ContactContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ControlPanelLayout.createSequentialGroup()
                        .addComponent(userProfileControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ControlPanelLayout.setVerticalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userProfileControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ContactContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        chatText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        chatText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                chatTextKeyPressed(evt);
            }
        });

        submitBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/submit.png"))); // NOI18N
        submitBtn.setBorderPainted(false);
        submitBtn.setContentAreaFilled(false);
        submitBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                submitBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ChatPanelLayout = new javax.swing.GroupLayout(ChatPanel);
        ChatPanel.setLayout(ChatPanelLayout);
        ChatPanelLayout.setHorizontalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelLayout.createSequentialGroup()
                .addGroup(ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ChatPanelLayout.createSequentialGroup()
                        .addComponent(chatText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chatMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE))
                .addContainerGap())
        );
        ChatPanelLayout.setVerticalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelLayout.createSequentialGroup()
                .addComponent(chatMessages, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chatText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(submitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        InterfaceSplit.setBottomComponent(ChatPanel);
        InterfaceSplit.setLeftComponent(videoArea);

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

        mmuLogout.setText("Logout");
        mmuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmuLogoutActionPerformed(evt);
            }
        });
        menuFile.add(mmuLogout);

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
                .addComponent(MainSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButton1ActionPerformed
        
    }//GEN-LAST:event_submitButton1ActionPerformed

    private void chatTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_chatTextKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            submitBtnMouseClicked(null);
        }
    }//GEN-LAST:event_chatTextKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        userProfileControl.formWindowClosing(evt);
        
        con.writeSafe(MessageFactory.generateLogout(getUserProfile().getUserID()));
        con.setHandlerLoggedOut();
    }//GEN-LAST:event_formWindowClosing

    private void mmuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmuLogoutActionPerformed
        formWindowClosing(null);
        this.setVisible(false);
        parent.logoutCalled();
    }//GEN-LAST:event_mmuLogoutActionPerformed

    private void submitBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_submitBtnMouseClicked
        if(!chatText.getText().isEmpty()){
            Messages.StringMessage sm = MessageFactory.generateStringMessage(
                    getUserID(), contacts.getSelectedUserID(), 
                    getUserProfile().getUsername(), 
                    chatText.getText() + "\n");
            if (sm.getTargetID() != Messages.Message.IGNORE) {
                con.writeSafe(sm);
                contacts.getSelectedProfile().addMessage(sm);
                chatMessages.update();
            }
        }
        chatText.setText("");
    }//GEN-LAST:event_submitBtnMouseClicked

    private void setupGUI(Colleague me) {
        initComponents();
        setTitle("Stream2Me: " + me.getUsername()+ " " + me.getUserID());
        
        this.getContentPane().setBackground(Color.WHITE);
        
        MainSplit.setBackground(Color.black);
        MainSplit.setBorder(null);
        
        userProfileControl.setUserInterface(this);
        userProfileControl.setUser(me);
        
        contacts.setUserInterface(this);
    }
    
    private void setupBackend(Connection con) {
        this.con =con;        
        
        this.con.setHandlerUserInterface(this);
//        this.listener =streamListener.start(con, this);
        this.audioPlayer =Client.AudioPlayer.start();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChatPanel;
    private javax.swing.JPanel ContactContainer;
    private javax.swing.JScrollPane ContactScroller;
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JPanel InterfacePanel;
    private javax.swing.JSplitPane InterfaceSplit;
    private javax.swing.JSplitPane MainSplit;
    private Interface.ClientGUI.ChatArea chatMessages;
    private java.awt.TextField chatText;
    private Interface.ClientGUI.Contacts.ContactList contacts;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem mmuLogout;
    private Interface.ClientGUI.Button submitBtn;
    private Interface.ClientGUI.Contacts.UserProfile userProfileControl;
    private Interface.ClientGUI.VideoPane videoArea;
    // End of variables declaration//GEN-END:variables
   
}