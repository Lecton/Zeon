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
 * Specifies the user interface and its functionality by providing actions.
 * @author Bernhard
 */
public class UI extends JFrame {
    protected enum Action {REMOVE, ADD, UPDATE}
    
    JTabbedPane jtp;
    
    /**
     * Default constructor.
     * Sets up a user interface with a blank name.
     */
    public UI() {
        setupUI("");
    }
    
    /**
     * Constructor.
     * Sets up a user interface with a specified name.
     * @param name - a name for the interface.
     */
    public UI(String name) {
        setupUI(name);
    }
    
    /**
     * Sets up the user interface.
     * Specifies the title of the interface, adjusts the window dimensions,
     * and specifies a condition under which the application exits - namely
     * when it is closed by the end user.
     * @param name - a name for the interface.
     */
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
    
    /**
     * Updates the user interface when an action is performed on it by the user.
     * Includes functionality for removal of a user - the user's tab is removed
     * from the interface.
     * Includes functionality for adding a user - the user gets his/her own tab
     * and the panel is updated by the colleague being added.
     * Includes functionality for updating a user's information - it is able to 
     * update the title of a colleague and in doing so, needs to update the panel.
     * @param coll - the colleague.
     * @param act - the action to be performed.
     */
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
                jtp.addTab("" + coll.localName, coll.panel);
                
                coll.updatePanel();
                coll.tabIndex = jtp.getTabCount()-1;
                System.out.println("Add user " + coll.localName);
                break;
            case UPDATE:
                jtp.setTitleAt(coll.tabIndex, coll.localName);
                coll.updatePanel();
                System.out.println("Update user " + coll.localName);
                break;
            default:
                break;
        }
        
        jtp.updateUI();
    }
}