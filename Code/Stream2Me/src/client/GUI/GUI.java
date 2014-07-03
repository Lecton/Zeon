package client.GUI;

import Messages.ClientInit;
import Messages.StringMessage;
import client.Client;
import client.Connection;
import client.inStream;
import java.io.IOException;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lecton
 * @author Bernhard
 */
public class GUI extends JFrame {
    public enum Action {REMOVE, ADD, UPDATE}
    
    private Connection con =null;
    private inStream input =null;
    private Client client =null;
    
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
        con.setAddress("127.0.0.1");
        
        initComponents();
        
        setTitle("Stream2Me: "+name);
        
        input =new inStream(this);
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
        StreamControls = new javax.swing.JPanel();
        ControlStream = new javax.swing.JButton();
        ControlStreamPlay = new javax.swing.JButton();
        ControlStreamStop = new javax.swing.JButton();
        ControlVideo = new javax.swing.JButton();
        ControlVideoPlay = new javax.swing.JButton();
        ControlVideoStop = new javax.swing.JButton();
        ControlAudio = new javax.swing.JButton();
        ControlAudioPlay = new javax.swing.JButton();
        ControlAudioStop = new javax.swing.JButton();
        ContactPane = new client.GUI.Contacts();
        InterfacePanel = new javax.swing.JPanel();
        InterfaceSplit = new javax.swing.JSplitPane();
        ChatPanel = new javax.swing.JPanel();
        chatSend = new javax.swing.JButton();
        chatMessages = new java.awt.TextArea();
        chatText = new java.awt.TextField();
        DetailsPanel = new javax.swing.JPanel();
        mainMenu = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuConnect = new javax.swing.JMenuItem();
        menuEdit = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MainSplit.setDividerLocation(230);
        MainSplit.setMinimumSize(new java.awt.Dimension(230, 3));

        StreamControls.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        ControlStream.setText("Stream");
        ControlStream.setToolTipText("");
        ControlStream.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ControlStreamActionPerformed(evt);
            }
        });

        ControlStreamPlay.setBackground(new java.awt.Color(102, 255, 51));
        ControlStreamPlay.setText("Play");
        ControlStreamPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ControlStreamPlayActionPerformed(evt);
            }
        });

        ControlStreamStop.setBackground(new java.awt.Color(255, 51, 51));
        ControlStreamStop.setText("Stop");

        ControlVideo.setText("Video");

        ControlVideoPlay.setBackground(new java.awt.Color(102, 255, 51));
        ControlVideoPlay.setText("Play");

        ControlVideoStop.setBackground(new java.awt.Color(255, 51, 51));
        ControlVideoStop.setText("Stop");

        ControlAudio.setText("Audio");

        ControlAudioPlay.setBackground(new java.awt.Color(102, 255, 51));
        ControlAudioPlay.setText("Play");

        ControlAudioStop.setBackground(new java.awt.Color(255, 51, 51));
        ControlAudioStop.setText("Stop");
        ControlAudioStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ControlAudioStopActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout StreamControlsLayout = new javax.swing.GroupLayout(StreamControls);
        StreamControls.setLayout(StreamControlsLayout);
        StreamControlsLayout.setHorizontalGroup(
            StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StreamControlsLayout.createSequentialGroup()
                .addGroup(StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ControlAudio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ControlVideo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ControlStream, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ControlAudioPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ControlStreamPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ControlVideoPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ControlVideoStop, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ControlStreamStop, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ControlAudioStop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 10, Short.MAX_VALUE))
        );
        StreamControlsLayout.setVerticalGroup(
            StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StreamControlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ControlStream, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ControlStreamStop, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ControlStreamPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlVideo)
                    .addComponent(ControlVideoPlay)
                    .addComponent(ControlVideoStop))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(StreamControlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ControlAudio)
                    .addComponent(ControlAudioPlay)
                    .addComponent(ControlAudioStop)))
        );

        javax.swing.GroupLayout ControlPanelLayout = new javax.swing.GroupLayout(ControlPanel);
        ControlPanel.setLayout(ControlPanelLayout);
        ControlPanelLayout.setHorizontalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addGroup(ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ContactPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(StreamControls, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        ControlPanelLayout.setVerticalGroup(
            ControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ControlPanelLayout.createSequentialGroup()
                .addComponent(StreamControls, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ContactPane, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
        );

        MainSplit.setLeftComponent(ControlPanel);

        InterfaceSplit.setDividerLocation(400);
        InterfaceSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        chatSend.setText("Submit");
        chatSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatSendActionPerformed(evt);
            }
        });

        chatMessages.setBackground(new java.awt.Color(204, 204, 204));
        chatMessages.setEditable(false);

        javax.swing.GroupLayout ChatPanelLayout = new javax.swing.GroupLayout(ChatPanel);
        ChatPanel.setLayout(ChatPanelLayout);
        ChatPanelLayout.setHorizontalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelLayout.createSequentialGroup()
                .addGroup(ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ChatPanelLayout.createSequentialGroup()
                        .addComponent(chatText, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chatSend, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addComponent(chatMessages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ChatPanelLayout.setVerticalGroup(
            ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chatMessages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chatSend)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ChatPanelLayout.createSequentialGroup()
                        .addComponent(chatText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        InterfaceSplit.setBottomComponent(ChatPanel);

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

        InterfaceSplit.setLeftComponent(DetailsPanel);

        javax.swing.GroupLayout InterfacePanelLayout = new javax.swing.GroupLayout(InterfacePanel);
        InterfacePanel.setLayout(InterfacePanelLayout);
        InterfacePanelLayout.setHorizontalGroup(
            InterfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(InterfaceSplit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
        );
        InterfacePanelLayout.setVerticalGroup(
            InterfacePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(InterfaceSplit)
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
                .addComponent(MainSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 996, Short.MAX_VALUE)
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

    private void ControlStreamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ControlStreamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ControlStreamActionPerformed

    private void ControlStreamPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ControlStreamPlayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ControlStreamPlayActionPerformed

    private void menuConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConnectActionPerformed
        // TODO add your handling code here:
        try {
            con.makeConnection();
            con.write(new ClientInit(name));
            input.setInputStream(con.getInputStream());
            (new Thread(input)).start();
            menuConnect.setEnabled(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_menuConnectActionPerformed

    private void chatSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatSendActionPerformed
        System.out.println(chatText.getText());
        for(int i = 0; i < ContactPane.colleagues.size();i++){
            if(ContactPane.colleagues.get(i).ID != ID &&
                          !chatText.getText().isEmpty()){
                int selectedIndex =ContactPane.getSelectedIndex();
                StringMessage sm = new StringMessage(name +" :\t"+ chatText.getText() + "\n", ID);
                if (selectedIndex != -1) {
                    sm.to =ContactPane.colleagues.get(selectedIndex).ID;
                } else {
                    sm.to =-1;
                }
                con.write(sm);
            }
        }
    }//GEN-LAST:event_chatSendActionPerformed

    private void ControlAudioStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ControlAudioStopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ControlAudioStopActionPerformed

    public void setChatMessage(String mess){
        this.chatMessages.setText(mess);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ChatPanel;
    public client.GUI.Contacts ContactPane;
    private javax.swing.JButton ControlAudio;
    private javax.swing.JButton ControlAudioPlay;
    private javax.swing.JButton ControlAudioStop;
    private javax.swing.JPanel ControlPanel;
    private javax.swing.JButton ControlStream;
    private javax.swing.JButton ControlStreamPlay;
    private javax.swing.JButton ControlStreamStop;
    private javax.swing.JButton ControlVideo;
    private javax.swing.JButton ControlVideoPlay;
    private javax.swing.JButton ControlVideoStop;
    private javax.swing.JPanel DetailsPanel;
    private javax.swing.JPanel InterfacePanel;
    private javax.swing.JSplitPane InterfaceSplit;
    private javax.swing.JSplitPane MainSplit;
    private javax.swing.JPanel StreamControls;
    private java.awt.TextArea chatMessages;
    private javax.swing.JButton chatSend;
    private java.awt.TextField chatText;
    private javax.swing.JMenuBar mainMenu;
    private javax.swing.JMenuItem menuConnect;
    private javax.swing.JMenu menuEdit;
    private javax.swing.JMenu menuFile;
    // End of variables declaration//GEN-END:variables
}
