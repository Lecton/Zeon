/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.GUI;

import client.Colleague;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Bernhard
 */
public class Contacts extends JScrollPane {
    public ArrayList<Colleague> colleagues = new ArrayList<>();
    
    private JList list;
    
    public Contacts() {
        list =new JList();
        list.setVisibleRowCount(10);
        list.setCellRenderer(new CellRenderer());
        
        setViewportView(list);
    }
    
    public void addContact(Colleague coll) {
        System.out.println("User added");
        colleagues.add(coll);
        list.setListData(colleagues.toArray());
    }
    
    public void removeContact(Colleague coll) {
        System.out.println("User removed");
        colleagues.remove(coll);
        list.setListData(colleagues.toArray());
    }
    
    private class CellRenderer extends JPanel implements ListCellRenderer<Colleague> {
        private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

        private JLabel name;
        private JLabel avatar;

        public CellRenderer() {
            setOpaque(true);
            this.setLayout(new BorderLayout());
            name =new JLabel("User");
            avatar =new JLabel();
            
            add(name);
            add(avatar);
            
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Colleague> list, Colleague value,
            int index, boolean isSelected, boolean cellHasFocus) {
                name.setText(value.name);
            
                add(name);
//                name.setText(value.getTitle());
//                name.setIcon(value.getImage());
                if (isSelected) {
                    setBackground(HIGHLIGHT_COLOR);
                    setForeground(Color.white);
                } else {
                    setBackground(Color.white);
                    setForeground(Color.black);
                }
                return this;
            }
        }
}
