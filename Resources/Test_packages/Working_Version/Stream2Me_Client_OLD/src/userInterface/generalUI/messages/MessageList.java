/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userInterface.generalUI.messages;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JScrollPane;
import messages.StringMessage;

/**
 *
 * @author Bernhard
 */
public class MessageList extends JScrollPane {
    private ArrayList<StringMessage> chatMessages;
    private final JList list;
    
    /**
     * Creates new form UserMessage
     */
    public MessageList() {
        list = new JList();
        list.setVisibleRowCount(1);
        list.setCellRenderer(new MessageItem());
        list.setEnabled(false);
        list.setDragEnabled(false);
        setViewportView(list);
        getVerticalScrollBar().setValue(0);
    }
    
    public void addMessage(StringMessage msg) {
        chatMessages.add(msg);
        
        int extent = getVerticalScrollBar().getModel().getExtent();
        int max =getVerticalScrollBar().getMaximum();
        int current =getVerticalScrollBar().getValue()+extent;
        update();
        
        if (max == current) {
            int newMax =getVerticalScrollBar().getMaximum();
            getVerticalScrollBar().setValue(newMax);
        }
    }

    public void setMessagesHistory(ArrayList<StringMessage> chatHistory, int userID, boolean multiPerson) {
        ((MessageItem)list.getCellRenderer()).setMultiperson(multiPerson);
        ((MessageItem)list.getCellRenderer()).setUserID(userID);
        chatMessages = chatHistory;
        update();
        
        int newMax =getVerticalScrollBar().getMaximum();
        getVerticalScrollBar().setValue(newMax);
    }
    
    public void update() {
        if (chatMessages != null) {
            list.setListData(chatMessages.toArray());
        }
        
        for (Component c: list.getComponents()) {
            if (c instanceof MessageItem) {
                System.out.println("hi");
            }
        }
        
        validate();
        repaint();
    }

    public void clear() {
        chatMessages = new ArrayList<>();
        update();
    }
}
