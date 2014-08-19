package client.GUI;

import client.GUI.Contacts.UserProfile;
import client.GUI.Contacts.Contacts;
import client.GUI.audio.AudioPlayer;
import Messages.ClientInit;
import Messages.StringMessage;
import client.Client;
import client.Colleague;
import client.Connection;
import client.GUI.Contacts.ContactProfile;
import client.inStream;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
    private enum Action {REMOVE, ADD, UPDATE}
    
    private Connection con =null;
    private inStream input =null;
    private Client client =null;
    
    private String username ="User";
    private int ID =-1;
    private ContactProfile myContactData;
    
    private AudioPlayer audio;

    /**
     * Creates new form GUI
     */
    public GUI(String name, int PORT, Client client) {
        this.client =client;
        this.username =name;
        con =new Connection();
        con.setPORT(PORT);
        con.setAddress("127.0.0.1");
        
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        MainSplit.setBackground(Color.black);
        MainSplit.setBorder(null);
        
        setTitle("Stream2Me: " + name);
        input = new inStream(this);
        audio =new AudioPlayer();
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
        userProfileControl = new client.GUI.Contacts.UserProfile(this);
        ContactContainer = new javax.swing.JPanel();
        ContactScroller = new javax.swing.JScrollPane();
        ContactPane = new client.GUI.Contacts.Contacts(this);
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
        mmuUpdateList = new javax.swing.JMenuItem();

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

        ContactContainer.setLayout(new java.awt.BorderLayout());

        ContactScroller.setViewportView(ContactPane);

        javax.swing.GroupLayout ContactContainerLayout = new javax.swing.GroupLayout(ContactContainer);
        ContactContainer.setLayout(ContactContainerLayout);
        ContactContainerLayout.setHorizontalGroup(
            ContactContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ContactScroller)
        );
        ContactContainerLayout.setVerticalGroup(
            ContactContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ContactScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ControlPanelLayout = new javax.swing.GroupLayout(ControlPanel);
        ControlPanel.setLayout(ControlPanelLayout);
        ControlPanelLayout.setHorizontalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(userProfileControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addComponent(ContactContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        chatSend.setBackground(new java.awt.Color(255, 255, 255));
        chatSend.setForeground(new java.awt.Color(255, 255, 255));
        chatSend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/submit.png"))); // NOI18N
        chatSend.setToolTipText("");
        chatSend.setBorderPainted(false);
        chatSend.setContentAreaFilled(false);
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

        mmuUpdateList.setText("Update Contacts");
        mmuUpdateList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmuUpdateListActionPerformed(evt);
            }
        });
        menuEdit.add(mmuUpdateList);

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

    private void menuConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConnectActionPerformed
        try {
            con.makeConnection();
            con.write(new ClientInit(username));
            input.setInputStream(con);
            (new Thread(input)).start();
            menuConnect.setEnabled(false);
            audio.play();
            (new Thread(audio)).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_menuConnectActionPerformed

    private void chatSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatSendActionPerformed

        if(!chatText.getText().isEmpty()){
            StringMessage sm = new StringMessage(username +" :\t"+ chatText.getText() + "\n", ID);
            sm.setTo(ContactPane.getSelectedID());
            if (sm.getTo() != -2) {
                con.writeSafe(sm);
                ContactPane.acceptMessage(sm);
            }
        }
        chatText.setText("");
    }//GEN-LAST:event_chatSendActionPerformed

    private void chatTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chatTextActionPerformed

    private void audioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audioButton1ActionPerformed

    }//GEN-LAST:event_audioButton1ActionPerformed

    private void mmuUpdateListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmuUpdateListActionPerformed
        ContactPane.updateList();
    }//GEN-LAST:event_mmuUpdateListActionPerformed
    
    public void setChatHistory(ArrayList<StringMessage> chatHist){
//        chatMessages.setText("");
        chatMessages.setChatMessages(chatHist);
    }
    
    public void appendChatMessage(StringMessage chatMsg){
        chatMessages.append(chatMsg);
    }
    
    public inStream getInstream() {
        return input;
    }

    public UserProfile getUserProfileControl() {
        return userProfileControl;
    }

    public Contacts getContactPane() {
        return ContactPane;
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public String getUsername() {
        return username;
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public JLabel getImgBlock() {
        return imgBlock;
    }

    public AudioPlayer getAudio() {
        return audio;
    }

    public void setupMyContactData() {
        //add me
        myContactData =ContactPane.addContact(getID(), getUsername());
    }

    public ContactProfile getMyContactData() {
        return myContactData;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChatPanel;
    private javax.swing.JPanel ContactContainer;
    private client.GUI.Contacts.Contacts ContactPane;
    private javax.swing.JScrollPane ContactScroller;
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JPanel DetailsPanel;
    private javax.swing.JPanel InterfacePanel;
    private javax.swing.JSplitPane InterfaceSplit;
    private javax.swing.JSplitPane MainSplit;
    private client.GUI.ChatArea chatMessages;
    private javax.swing.JButton chatSend;
    private java.awt.TextField chatText;
    private javax.swing.JLabel imgBlock;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JMenuItem menuConnect;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenuItem mmuUpdateList;
    private client.GUI.Contacts.UserProfile userProfileControl;
    // End of variables declaration//GEN-END:variables
   
}