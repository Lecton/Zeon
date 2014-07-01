/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import Messages.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Bernhard
 * @auther Lecton
 */
public class Client {
    private int PORT =2000;
    private Socket connection  =null;
    private ObjectOutputStream os =null;
    private inStream is =null;
    private UI userInterface =null;
    
    public int ID = -1;
    public String name = "User";
    
    
    public Client() throws UnknownHostException, IOException {
        setup(PORT, name);
    }
    
    public Client(int port) throws UnknownHostException, IOException {
        setup(port, name);
    }
    
    public Client(int port, String name) throws UnknownHostException, IOException {
        setup(port, name);
    }
    
    private void setup(int port, String name) throws UnknownHostException, IOException {
        userInterface =new UI(name);
        this.PORT = port;
        this.connection = new Socket("127.0.0.1", port);
        this.name = name;
        System.out.println("I am setting up!");
        
        os = new ObjectOutputStream(connection.getOutputStream());
        
        is = new inStream(connection.getInputStream(), this, userInterface);
        System.out.println("Got streams");
        
        os.writeObject(new ClientInit(name));
        os.flush();
        
        System.out.println("I have finished setting up!");
    }
    
    public void start() {
        Scanner scan = new Scanner(System.in);
        Thread inputStreamThread = new Thread(is);
        inputStreamThread.start();
        
//        Thread outStreamThread =new Thread(os);
//        outStreamThread.start();
        
        while (true) {
            String line = scan.nextLine();
            try {
                os.writeObject(new StringMessage(line, name, ID));
                os.flush();
            } catch (IOException ex) {
                System.err.println("START - writeObject Error");
            }
        }
    }
    
    public static void main(String[] args){
        try {
            String name = JOptionPane.showInputDialog("Please enter your name.");
            System.out.println("My name is "+name+"!");
            
            Client client = (new Client(2014,name));
            client.start();
            
        } catch (IOException ex) {
            System.err.println("MAIN - start client error");
        }
    }
}