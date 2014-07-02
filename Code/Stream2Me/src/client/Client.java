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
 */
public class Client {
    private int PORT =2014;
    private String name ="User";
    private GUI userInterface =null;
    
    public Client() {
        setup();
    }
    
    public Client(int PORT) {
        this.PORT =PORT;
        setup();
    }
    
    public Client(int PORT, String name) {
        this.PORT =PORT;
        this.name =name;
        setup();
    }
    
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
        String name = JOptionPane.showInputDialog("Please enter your name.");
        System.out.println("My name is "+name+"!");

        Client client = (new Client(2014,name));
    }
}
