/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Lecton
 */
public class Colleague {
    int ID =-1;
    String name ="";
    String localName ="";
    String content = "This is the label";
    JPanel panel;
    JLabel label;
    int tabIndex =-1;
    
    /**
     * Create a new Colleague and set its default panel content
     */
    public Colleague() {
        panel =new JPanel();
        label =new JLabel(content);
        setupPanel();
    }
    
    /**
     * Update the content of the Colleague panel
     */
    public void updatePanel() {
        panel.removeAll();
        label.setText(content);
        setupPanel();
    }
    
    private void setupPanel() {
        panel.add(label);
    }

    /**
     * Checks if the current colleague is contained in the list
     * @param list Array of colleague objects
     * @return index if list if contained. -1 if not
     */
    public int contained(ArrayList<Colleague> list) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).ID == ID) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Updates the other colleague object with the details in the current one 
     * if the current one has the same ID and the other one
     * @param other Colleague to update
     * @return true if successfully update. false if not
     */
    public boolean update(Colleague other) {
        if (other.ID == ID) {
            if (other.localName.equalsIgnoreCase(other.name)) {
                other.localName =name;
            }
            other.name = name;
            return true;
        } else {
            return false;
        }
    }
}
