/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.view.generalUI;

import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import mvc.controller.generalUI.UserControl;

/**
 *
 * @author Bernhard
 */
public class UserPopup extends JPopupMenu {
    private final static Logger LOGGER = Logger.getLogger(UserPopup.class.getName());
    
    JMenuItem viewProfile, viewMessages;
    
    public UserPopup() {
        viewMessages =new JMenuItem("View Messages");
        viewProfile =new JMenuItem("View Profile");
        
        viewMessages.setActionCommand("viewMessages");
        viewProfile.setActionCommand("viewProfile");
        
        viewMessages.addActionListener(UserControl.INSTANCE);
        viewProfile.addActionListener(UserControl.INSTANCE);
        
        add(viewProfile);
        add(viewMessages);
    }
}
