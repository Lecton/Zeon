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
    String content = "";
    JPanel panel = new JPanel();
    JLabel label = new JLabel(content);

    public boolean contained(ArrayList<Colleague> list) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).ID == ID) {
                return true;
            }
        }
        return false;
    }

    public boolean update(ArrayList<Colleague> list) {
        for (int i=0; i<list.size(); i++) {
            Colleague c =list.get(i);
            if (c.ID == ID) {
                if (c.localName.equalsIgnoreCase(c.name)) {
                    c.localName =name;
                }
                c.name = name;
            }
        }
        return false;
    }
}
