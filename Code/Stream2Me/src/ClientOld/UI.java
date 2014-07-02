/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientOld;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author Bernhard
 */
public class UI extends JFrame {
    protected enum Action {REMOVE, ADD, UPDATE}
    
    JTabbedPane jtp;
    
    public UI() {
        setupUI("");
    }
    
    public UI(String name) {
        setupUI(name);
    }
    
    private void setupUI(String name) {
        setDefaultLookAndFeelDecorated(false);
        if (name.isEmpty()) {
            setTitle("Stream2Me");
        } else {
            setTitle("Stream2Me: "+name);
        }
        
        setMinimumSize(new Dimension(300, 300));
        
        jtp = new JTabbedPane();
        add(jtp);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void updateGUI(Colleague coll, Action act) {
        switch (act) {
            case REMOVE: 
                try {
                    jtp.removeTabAt(coll.tabIndex);
                    
                    System.out.println("Remove user"+coll.localName);
                } catch (IndexOutOfBoundsException ioobe) {
                    System.err.println("UPDATEGUI - Error removing user");
                }
                break;
            case ADD: //IDindex will be -1
                jtp.addTab(""+coll.localName, coll.panel);
                
                coll.updatePanel();
                coll.tabIndex =jtp.getTabCount()-1;
                System.out.println("Add user "+coll.localName);
                break;
            case UPDATE:
                jtp.setTitleAt(coll.tabIndex, coll.localName);
                coll.updatePanel();
                System.out.println("Update user "+coll.localName);
                break;
            default:
                break;
        }
        
        jtp.updateUI();
    }
}