/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connection.MessageLog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Bernhard
 */
public class LogList extends JPanel {
    private GridBagConstraints gbcLog;
    public ArrayList<LogItem> list;
    
    public LogList() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(new JPanel(), gbc);
        
        
        gbcLog = new GridBagConstraints();
        gbcLog.gridwidth = GridBagConstraints.REMAINDER;
        gbcLog.weightx = 1;
        gbcLog.fill = GridBagConstraints.HORIZONTAL;
        
        list =new ArrayList<>();
        
        LogItem li =LogItem.getHeader();
        list.add(li);
        add(li, gbcLog, 0);
        update();
    }
    
    private void update() {
        validate();
        repaint();
    }

    public void addItem(TimeStampedMessage msg) {
        LogItem li =new LogItem();
        li.setStampedMessage(msg);
        list.add(li);
        add(li, gbcLog, 1);
        update();
    }
    
//    public void addProfile(Colleague profile) {
//        
//        ContactProfile contact =new ContactProfile();
//        contact.setColleague(profile);
//        contact.setContactList(this);
//        list.add(contact);
//        add(contact, gbcContactProfile, list.size()-1);
//        update();
//        
//        Log.write(this, "User added");
//    }
}
