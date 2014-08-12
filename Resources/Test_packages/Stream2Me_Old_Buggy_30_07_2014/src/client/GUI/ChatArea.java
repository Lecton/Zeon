package client.GUI;

import Messages.StringMessage;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

public class ChatArea extends JScrollPane {
    private ArrayList<StringMessage> chatMessages;
    private GUI userInterface;
    private JList list;
    private boolean multiperson =false;
    
    public ChatArea() {}
    
    public ChatArea(GUI userInterface) {
        this.userInterface = userInterface;
        list = new JList();
        list.setVisibleRowCount(1);
        list.setCellRenderer(new CellRenderer());
        list.setEnabled(false);
        list.setDragEnabled(false);
        setViewportView(list);
    }
    
    public void setMultipersonChat(boolean multiperson) {
        this.multiperson =multiperson;
    }
    
    public void setChatMessages(ArrayList<StringMessage> chatHistory) {
        chatMessages = chatHistory;
        list.setListData(chatMessages.toArray());
    }
    
    public void append(StringMessage sm) {
        list.setListData(chatMessages.toArray());
    }
    
    public void appendChatMessage(StringMessage chatMsg){
        list.setListData(chatMessages.toArray());
    }
    
    private class CellRenderer extends JPanel implements ListCellRenderer<StringMessage> {

        private JLabel name;
        private JLabel timestamp;
        private JTextArea message;

        public CellRenderer() {
            setOpaque(true);
            this.setLayout(new BorderLayout());
            
            name = new JLabel();
            timestamp = new JLabel();
            message = new JTextArea();

            message.setColumns(10);
            message.setEditable(false);
//            message.setEnabled(false);
            message.setRows(1);
            message.setLineWrap(true);
            message.setWrapStyleWord(true);

            GroupLayout messageItemLayout = new GroupLayout(this);
            this.setLayout(messageItemLayout);
            messageItemLayout.setHorizontalGroup(
                messageItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(messageItemLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(messageItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, messageItemLayout.createSequentialGroup()
                            .addGap(0, 10, Short.MAX_VALUE)
                            .addComponent(name)
                            .addGap(148, 148, 148)
                            .addComponent(timestamp))
                        .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            messageItemLayout.setVerticalGroup(
                messageItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(messageItemLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(messageItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(name)
                        .addComponent(timestamp))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends StringMessage> list, StringMessage value,
            int index, boolean isSelected, boolean cellHasFocus) {
                name.setText(value.getSender());
                timestamp.setText(value.getTimestamp());
                message.setText(value.getMessage());
                return this;
        }
    }
}