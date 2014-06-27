/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Bernhard
 */
public class Client {
    private int PORT = 2000;
    private Socket connection = null;
    private ObjectOutputStream oos = null;
    private inStream is = null;
    private int nClients = 0;
    
    JPanel [] panels;
    JLabel [] labels;
    JFrame frame;
    
    public Client(int port) throws UnknownHostException, IOException {
        this.PORT = port;
        this.connection =new Socket("127.0.0.1", port);
        oos = new ObjectOutputStream(connection.getOutputStream());
        is = new inStream(connection.getInputStream());
        
        

    }
    
    public void setGUI(){
        //System.out.println("GUI clients:\t" + nClients);
        if(frame != null){
            frame.dispose();
        }
        
        frame = new JFrame();
        frame.setTitle("Tabbed Pane");
        frame.setMinimumSize(new Dimension(1000, 300));
        panels = new JPanel[this.nClients];
        labels = new JLabel[this.nClients];
        for(int i = 0; i < this.nClients;i++){
            panels[i] = new JPanel();
            labels[i] = new JLabel();
            labels[i].setText("You are in area of Tab" + i); 
        }
        JTabbedPane jtp = new JTabbedPane();
        frame.getContentPane().add(jtp);
        
        for(int i = 0; i < this.nClients;i++){
            panels[i].add(labels[i]);
            jtp.addTab("Tab " + i, panels[i]);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);        
    }
    
    public void start() {
        Scanner scan =new Scanner(System.in);
        Thread inputStreamThread =new Thread(is);
        inputStreamThread.start();
        
        while (true) {
            Object message =scan.nextLine();
            try {
                oos.writeObject(message);
                oos.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private class inStream implements Runnable {
        private ObjectInputStream ois =null;
        
        public inStream(InputStream input) throws IOException {
            ois =new ObjectInputStream(input);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object o = ois.readObject();
                    String message =(String)o;
                    
                    if(message.startsWith("Update")){
                        nClients = Integer.parseInt(message.substring(message.indexOf(":") + 1));
                        setGUI();
               
                    }else{
                        System.out.println("Message: "+message);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    public static void main(String[] args) {
        try {
            (new Client(2014)).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
