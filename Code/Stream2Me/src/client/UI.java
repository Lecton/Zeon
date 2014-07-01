/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import Messages.ClientInit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

/**
 *
 * @author Bernhard
 */
public class UI extends JFrame {
    protected enum Action {REMOVE, ADD, UPDATE}
    
    private Connection con =null;
    private JTabbedPane jtp;
    private JMenuBar menuBar =null;
    private inStream input =null;
    private Client client =null;
    
    public String name ="User";
    public int ID =-1;
    
    public UI(String name, int PORT, Client client) {
        this.client =client;
        this.name =name;
        con =new Connection();
        con.setPORT(PORT);
        con.setAddress("127.0.0.1");
        
        setDefaultLookAndFeelDecorated(false);
        if (name.isEmpty()) {
            setTitle("Stream2Me");
        } else {
            setTitle("Stream2Me: "+name);
        }
        
        setMinimumSize(new Dimension(300, 300));
        
        
        menuBar =new JMenuBar();
        createMenu();
        setJMenuBar(menuBar);
        
        jtp = new JTabbedPane();
        add(jtp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        input =new inStream(this);
    }
    
    private void createMenu() {
        final JMenuItem connect =new JMenuItem("Connect");
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    con.makeConnection();
                    con.write(new ClientInit(name));
                    input.setInputStream(con.getInputStream());
                    (new Thread(input)).start();
                    connect.setEnabled(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        menuBar.add(connect);
    }
    
    
    public void updateGUI(Colleague coll, UI.Action action) {
        switch (action) {
            case ADD:
                coll.tabIndex =jtp.getTabCount();
                jtp.add(coll.localName, coll.panel);
                break;
            case REMOVE:
                jtp.removeTabAt(coll.tabIndex);
                break;
            case UPDATE:
                jtp.setTitleAt(coll.tabIndex, coll.localName);
                break;
            default:
                break;
        }
    }
}