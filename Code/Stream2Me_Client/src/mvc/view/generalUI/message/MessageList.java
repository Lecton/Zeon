/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.message;

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
    private ArrayList<String[]> chatMessages =new ArrayList<>();
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

    void addMessage(String name, String timestamp, String message) {
        chatMessages.add(new String[] {name, message});
        
        int extent = getVerticalScrollBar().getModel().getExtent();
        int max =getVerticalScrollBar().getMaximum();
        int current =getVerticalScrollBar().getValue()+extent;
        update();
        
        if (max == current) {
            int newMax =getVerticalScrollBar().getMaximum();
            getVerticalScrollBar().setValue(newMax);
        }
    }
}
