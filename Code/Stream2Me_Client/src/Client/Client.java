/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Connection.Connection;
import Connection.MessageLog.ConnectionObserver;
import Connection.MessageLog.MessageLog;
import Interface.ClientGUI.GUI;
import Interface.ClientLogin.Login;
import javax.swing.JOptionPane;

/**
 *
 * @author Bernhard
 */
public class Client {
    private Connection connection =null;
    private Colleague myAccount =null;
    private boolean running;
    private boolean GUIrunning;
    
    private GUI userInterface;
    
    public static void main(String[] args){
        
        (new Thread(new ConnectionObserver())).start();
                
        Client c =new Client();
    }
    
    public Client() {
        running =true;
        String address ="127.0.0.1";
        int PORT =2014;
        
        if (connect(address, PORT)) {
            while (running) {
                if (login()) {
                    GUIrunning =true;
                    startClientGUI();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Server not found. Program exiting", "Connection error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private boolean connect(String Address, int PORT) {
        try {
            connection =new Connection(Address, PORT);
            connection.makeConnection();
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }
    
    private boolean login() {
        final Login userLogin =new Login();
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                userLogin.setVisible(true);
                userLogin.setConnection(connection, Client.this);
                
            }
        });
        
        while (!userLogin.isSuccessful()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
        return true;
    }
    
    private void startClientGUI() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                userInterface =new GUI(connection, myAccount, Client.this);
                userInterface.setVisible(true);
                GUIrunning =true;
            }
        });
        
        while (GUIrunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    public void logoutCalled() {
        myAccount =null;
        GUIrunning =false;
    }
    
    public void close() {
        running =false;
    }
    
    public void setMyAccount(Colleague me) {
        myAccount =me;
//        System.out.println(me.getAvatar());
    }
}
