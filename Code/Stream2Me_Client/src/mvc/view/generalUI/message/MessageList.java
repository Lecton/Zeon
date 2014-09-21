/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.message;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Bernhard
 */
public class MessageList extends JPanel {
    private final static Logger LOGGER = Logger.getLogger(MessageList.class.getName());
    
    private GridBagConstraints gbcContent;

    public MessageList() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JPanel p =new JPanel();
        p.setBackground(new Color(204, 204, 204));
        p.setPreferredSize(new Dimension(0, 0));
        add(p, gbc);
        
        gbcContent = new GridBagConstraints();
        gbcContent.gridwidth = GridBagConstraints.REMAINDER;
        gbcContent.weightx = 1;
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
    }
    
    private void update() {
        revalidate();
        repaint();
    }
    
    int index =0;
    void addMessage(int ID, boolean owner, String name, String time, String message) {
        MessageItem msg =new MessageItem(ID, owner, name, time, message);
        add(msg, gbcContent, index++);
        update();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                update();
            }
        });
    }

    void clear() {
        removeAll();
        initComponents();
        index =0;
        update();
    }
}
