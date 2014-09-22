/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI.contacts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import mvc.controller.ContactControl;
import mvc.view.generalUI.containers.SeparatorBorder;

/**
 *
 * @author Bernhard
 */
public class ContactList extends JPanel implements MouseListener {
    private final static Logger LOGGER = Logger.getLogger(ContactList.class.getName());
    
    private GridBagConstraints gbcContent;
    private ArrayList<ContactProfile> list =new ArrayList<ContactProfile>();
    private ContactProfile selectedProfile =null;
    
    /**
     * Creates new form NewJPanel
     */
    public ContactList() {
        initComponents();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JPanel p =new JPanel();
        p.setBackground(Color.WHITE);
        p.setPreferredSize(new Dimension(0, 0));
        add(p, gbc);
        
        gbcContent = new GridBagConstraints();
        gbcContent.gridwidth = GridBagConstraints.REMAINDER;
        gbcContent.weightx = 1;
        gbcContent.fill = GridBagConstraints.HORIZONTAL;
    }
    
    private ContactProfile getContactProfile(String userID) {
        for (ContactProfile cp: list) {
            if (cp.getUserID().equals(userID)) {
                return cp;
            }
        }
        return null;
    }
    
    public void addProfile(String userID, String fullName, String avatar) {
        ContactProfile contact =new ContactProfile(userID);
        contact.setBorder(new SeparatorBorder(Color.BLACK, true, true, true, true));
        contact.setProfile(fullName, avatar);
        contact.setParent(this);
        contact.addMouseListener(this);
        
        //Property change for selecting and unselecting
        contact.addPropertyChangeListener(ContactControl.INSTANCE);
        list.add(contact);
        add(contact, gbcContent, list.size()-1);
        update();
        
        LOGGER.log(Level.INFO, "User added");
    }

    public void removeProfile(String userID) {
        ContactProfile profile =getContactProfile(userID);
        if (profile != null) {
            list.remove(profile);
            remove(profile);
            update();
        } else {
            LOGGER.log(Level.WARNING, "User to be removed was not found in list");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 20));
        setLayout(new java.awt.GridBagLayout());
    }// </editor-fold>//GEN-END:initComponents

    private void update() {
        revalidate();
        repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource().getClass().getSimpleName().equals("ContactProfile")) {
            ContactProfile caller =(ContactProfile)e.getSource();
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (selectedProfile == null) {
                    selectedProfile =caller;
                    selectedProfile.select();
                } else if (selectedProfile.getUserID().equals(caller.getUserID())) {
                    selectedProfile =null;
                    caller.unselect();
                } else {
                    selectedProfile.unselect();
                    selectedProfile =caller;
                    selectedProfile.select();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void checkSelected(String userID) {
        if (selectedProfile != null) {
            if (selectedProfile.getUserID().equals(userID)) {
                return;
            }
            selectedProfile.unselect();
            selectedProfile =null;
        }
    }

    public void alert(String userID) {
        ContactProfile profile =getContactProfile(userID);
        if (profile != null) {
            profile.update();
        }
    }

    public void updateProfile(String userID, String fullname, String avatar) {
        ContactProfile profile =getContactProfile(userID);
        if (profile != null) {
            profile.setProfile(fullname, avatar);
            profile.update();
        }
    }
}
