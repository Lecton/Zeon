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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
    private long myID = -1;
    private String name ="User";
    
    private ArrayList<Colleague> coll =new ArrayList<>();
    
    JPanel [] panels;
    JLabel [] labels;
    JFrame frame;
    
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
        oos = new ObjectOutputStream(connection.getOutputStream());
        is = new inStream(connection.getInputStream());
        System.out.println("Got streams");
        oos.writeObject(new ClientInit(name));
        oos.flush();
        System.out.println("I have finished setting up!");
    }
    
    public void setGUI(){
        
        if(frame != null){
            frame.dispose();
        }
        
        frame = new JFrame();
        frame.setTitle("Tabbed Pane");
        frame.setMinimumSize(new Dimension(1000, 300));
        panels = new JPanel[coll.size()];
        labels = new JLabel[coll.size()];
        for(int i = 0; i < coll.size();i++){
            panels[i] = new JPanel();
            labels[i] = new JLabel();
            labels[i].setText("You are in area of Tab" + i); 
        }
        JTabbedPane jtp = new JTabbedPane();
        frame.getContentPane().add(jtp);
        
        for(int i = 0; i < coll.size();i++){
            panels[i].add(labels[i]);
            jtp.addTab(""+coll.get(i).localName, panels[i]);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);        
    }
    
    public void start() {
        Scanner scan =new Scanner(System.in);
        Thread inputStreamThread =new Thread(is);
        inputStreamThread.start();
        myID= inputStreamThread.getId();
        while (true) {
            String line=scan.nextLine();
            try {
                oos.writeObject(new StringMessage(line, name));
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
                    Message m =(Message)o;
                    
                    if(m instanceof NewUser){
                        NewUser um =(NewUser)m;
                        Colleague c =new Colleague();
                        c.ID =um.ID;
                        if (c.ID == myID) {
                            um.name =name;
                        }
                        c.name =um.name;
                        c.localName =um.name;
                        if (c.contained(coll)) {
                            c.update(coll);
                        } else {
                            coll.add(c);
                        }
                        setGUI();
                        System.out.println("Added New User " + coll.size());
                    } else if (m instanceof RemoveUser) {
                        RemoveUser ru =(RemoveUser)m;
                        boolean done =false;
                        for (int i=0; i<coll.size() && !done; i++) {
                            if (coll.get(i).ID == ru.ID) {
                                coll.remove(i);
                                done =true;
                            }
                        }
                        setGUI();
                        System.out.println("Removed User");
                    } else if (m instanceof Greeting) {
                        Greeting g =(Greeting) m;
                        myID =g.ID;
                        Colleague cc =new Colleague();
                        cc.ID =g.ID;
                        cc.name =name;
                        cc.localName =name;
                        coll.add(cc);
                        
                        for (int i=0; i<g.size; i++) {
                            Colleague c =new Colleague();
                            c.ID =g.colleagueIDs[i];
                            c.name =g.colleagueNames[i];
                            c.localName =c.name;
                            coll.add(c);
                        }
                        setGUI();
                    } else if (m instanceof UpdateUser) {
                        UpdateUser uu =(UpdateUser) m;
                        for (int i=0; i<coll.size(); i++) {
                            if (coll.get(i).ID == uu.ID) {
                                if (coll.get(i).name.equalsIgnoreCase(coll.get(i).localName)) {
                                    coll.get(i).localName =uu.name;
                                }
                                coll.get(i).name =uu.name;
                            }
                        }
                        setGUI();
                    }
                    else {
                        System.out.println("Message: "+m.getMessage());
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    public static void main(String[] args) {
        try {
            String name = JOptionPane.showInputDialog("Please enter your name.");
            System.out.println("My name is "+name+"!");
            (new Client(2014,name)).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private class Colleague {
        int ID =-1;
        String name ="";
        String localName ="";
        
        public boolean contained(ArrayList<Colleague> list) {
            for (int i=0; i<list.size(); i++) {
                if (list.get(i).ID == ID) {
                    return true;
                }
            }
            return false;
        }
        
        public boolean update(ArrayList<Colleague> list) {
            for (int i=0; i<list.size(); i++) {
                Colleague c =list.get(i);
                if (c.ID == ID) {
                    if (c.localName == c.name) {
                        c.localName =name;
                    }
                    c.name =name;
                }
            }
            return false;
        }
    }
}
