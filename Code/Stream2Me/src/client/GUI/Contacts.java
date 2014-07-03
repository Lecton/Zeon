/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.GUI;

import Messages.StringMessage;
import Messages.UpdateUser;
import client.Colleague;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
    
    public int getSelectedIndex() {
        return list.getSelectedIndex();
    }
    
    public Object getSelectedValue() {
        return list.getSelectedValue();
    }
    
    
    
    /**
     * Find the colleague with the same ID as provided and returns his index
     * @param ID of the colleague to find
     * @return the colleague index or -1 if no one has that ID
     */
    private int find(int ID) {
        for (int i=0; i<colleagues.size(); i++) {
            if (colleagues.get(i).ID == ID) {
                return i;
            }
        }
        return -1;
    }
    
    private Colleague getColleague(int ID) {
        int index =find(ID);
        if (index != -1) {
            return colleagues.get(index);
        }
        return null;
    }
    
    public void addContact(Colleague coll) {
        System.out.println("User added");
        colleagues.add(coll);
        list.setListData(colleagues.toArray());
    }
    
    public void removeContact(int colleagueID) {
        System.out.println("User removed");
        Colleague toRemove =getColleague(colleagueID);
        if (toRemove != null) {
            colleagues.remove(toRemove);
        } else {
            System.err.println("Error removing colleague with ID: "+colleagueID);
        }
        list.setListData(colleagues.toArray());
    }

    public void updateUser(UpdateUser uu) {
        System.out.println("User updated");
        int index =-1;
        if ((index =find(uu.ID)) != -1) {
            colleagues.get(index).setName(uu.name);
        }
        list.setListData(colleagues.toArray());
    }
    
    public void acceptMessage(StringMessage sm) {
        
    }
    
    private class ListSelection implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            System.out.println("First Index: "+e.getFirstIndex());
            System.out.println("Last Index: "+e.getLastIndex());
        }
    }
    
    private class CellRenderer extends JPanel implements ListCellRenderer<Colleague> {
        private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

        private JLabel name;
        private JLabel avatar;
        private JButton button1;
        private JButton button2;
        private GroupLayout layout;

        public CellRenderer() {
            setOpaque(true);
            this.setLayout(new BorderLayout());
            name =new JLabel("User");
            avatar =new JLabel();
            layout = new GroupLayout(this);
            
            button1 = new JButton();
            button2 = new JButton();

            name.setText("Name");

            button1.setBackground(new Color(51, 255, 0));
            button2.setBackground(new Color(255, 0, 0));

            setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(avatar, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(button1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(button2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 29, Short.MAX_VALUE))))
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(avatar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(name, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            );
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Colleague> list, Colleague value,
            int index, boolean isSelected, boolean cellHasFocus) {
                name.setText(value.name);
                avatar.setIcon(new ImageIcon("./default.png"));
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
