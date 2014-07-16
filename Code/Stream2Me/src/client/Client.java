/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import client.GUI.GUI;
import javax.swing.JOptionPane;

/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class Client {
    private int PORT = 2014;
    private String name = "User";
    private GUI userInterface = null;
    
    /**
     * Constructor.
     */
    public Client() {
        setup();
    }
    
    /**
     * Constructor to create a client and initialize it to connect to a specific
     * port number.
     * @param PORT - the port number the new client is to connect on.
     */
    public Client(int PORT) {
        this.PORT =PORT;
        setup();
    }
    
    /**
     * Constructor to create a client and initialize it to connect to a specific
     * port number and with a specific name other than the default.
     * @param PORT - the port number the new client is to connect on.
     * @param name - the name of the client being created.
     */
    public Client(int PORT, String name) {
        this.PORT =PORT;
        this.name =name;
        setup();
    }
    
    /**
     * Sets up the client and updates the interface to reflect the changes to
     * the system.
     */
    private void setup() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                userInterface =new GUI(name, PORT, Client.this);
                userInterface.setVisible(true);
            }
        });
    }
    
    public static void main(String[] args){
        String name ="a";// JOptionPane.showInputDialog("Please enter your name.");
        System.out.println("My name is "+name+"!");

        Client client = (new Client(2014,name));
    }
}
