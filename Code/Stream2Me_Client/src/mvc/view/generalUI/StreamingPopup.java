/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view.generalUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import mvc.controller.generalUI.UserControl;
import mvc.view.generalUI.containers.Button;

/**
 *
 * @author Bernhard
 */
public class StreamingPopup extends JPopupMenu implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(StreamingPopup.class.getName());

    JMenuItem setName;
    final Button parent;
    final int type;
    
    public StreamingPopup(Button parent, int type) {
        this.parent =parent;
        this.type =type;
        init();
    }

    @Override
    public void show(Component invoker, int x, int y) {
        if (type == 0) {
            if (!UserControl.streamingVideo()) {
                setName.setVisible(true);
                super.show(invoker, x, y);
            } else {
                setName.setVisible(false);
            }
        } else if (type == 1) {
            if (!UserControl.streamingAudio()) {
                setName.setVisible(true);
                super.show(invoker, x, y);
            } else {
                setName.setVisible(false);
            }
        }
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("setName")) {
            String name =JOptionPane.showInputDialog(this, "Enter the name of the stream (Blank will default to user's name)", "Set Stream Name", JOptionPane.QUESTION_MESSAGE);
            name =name == null? "" : name;
            UserControl.INSTANCE.setStreamName(name, type);
        } else {
            LOGGER.log(Level.WARNING, "Unknown action command");
        }
    }

    private void init() {
        setName =new JMenuItem("Set name");
        
        setName.setActionCommand("setName");
        
        setName.addActionListener(this);
        
        add(setName);
        
        pack();
    }
}
