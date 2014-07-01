/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import Messages.*;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
    private ObjectOutputStream os = null;
    private inStream is = null;
    public int ID = -1;
    public String name = "User";
    
    public ArrayList<Colleague> coll = new ArrayList<>();
    
    JFrame frame;
    JTabbedPane jtp;
    
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
        this.PORT = port;
        this.connection = new Socket("127.0.0.1", port);
        this.name = name;
        System.out.println("I am setting up!");
        
        os = new ObjectOutputStream(connection.getOutputStream());
        
        is = new inStream(connection.getInputStream(), this);
        System.out.println("Got streams");
        
        os.writeObject(new ClientInit(name));
        os.flush();
        
        System.out.println("I have finished setting up!");
        
    }
    
    public synchronized void updateGUI() {
        if (jtp.getTabCount() != coll.size()) {
            jtp.removeAll();
            for (int i=0; i<coll.size(); i++) {
                coll.get(i).panel =new JPanel();
                coll.get(i).panel.add(coll.get(i).label);
                jtp.addTab(""+coll.get(i).localName, coll.get(i).panel);
            }
        }
        
        for (int i=0; i<coll.size(); i++) {
            coll.get(i).label.setText(coll.get(i).content);
        }
        jtp.updateUI();
    }
    
    public synchronized void setGUI(){
//        
        if(frame != null){
            frame.dispose();
        }
        frame = new JFrame();
        frame.setDefaultLookAndFeelDecorated(false);
        frame.setTitle("Stream2Me");
        frame.setMinimumSize(new Dimension(300, 300));

        jtp = new JTabbedPane();
        frame.getContentPane().add(jtp);
        
        for(int i = 0; i < coll.size();i++){
            coll.get(i).panel.add(coll.get(i).label);
            jtp.addTab(""+coll.get(i).localName, coll.get(i).panel);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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
                ex.printStackTrace();
            }
        }
    }
//    
//    private class outStream implements Runnable{
//        private ObjectOutputStream oos = null;
//
//        public outStream(OutputStream output) throws IOException {
//            oos = new ObjectOutputStream(output);
//        }
//        
//        @Override
//        public void run() {
//            while(true){
//                try 
//                {
//                    write(new StringMessage("This is " + name + "'s contents.\n",name, myID));
//                    Thread.sleep(1000);
//                }catch (IOException | InterruptedException ex){
//                    ex.printStackTrace();
//                }
//            }
//        }
//        
//        public void write(Object object) throws IOException{
//            oos.writeObject(object);
//            oos.flush();
//        }
//    }
//    
    
    public static void main(String[] args){
        try {
            String name = JOptionPane.showInputDialog("Please enter your name.");
            System.out.println("My name is "+name+"!");
            
            Client client = (new Client(2014,name));
            client.start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}