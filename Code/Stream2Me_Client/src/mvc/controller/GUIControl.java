/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import mvc.view.generalUI.GUI;
import sun.awt.WindowClosingListener;
import sun.awt.WindowClosingSupport;

/**
 *
 * @author Bernhard
 */
public class GUIControl implements WindowListener {
    private static GUI view;
    
    public static void register(GUI ui) {
        view =ui;
    }
    
    protected static void clear() {
        UpdateControl.clear();
        ContactListControl.clear();
        UserControl.clear();
    }
    
    protected static void changeContent(int type, String userID) {
        switch (type) {
            case 0:
                //show profile
                break;
            case 1:
                //show message
                break;
            case 2:
                //show user profile
                break;
            case 3:
                //show group message
                break;
            default:
                //show group message
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Control.INSTANCE.close();
    }

    @Override
    public void windowClosed(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
