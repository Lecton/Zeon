/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import Messages.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Bernhard
 */
public class clientConnection {
    private Socket s =null;
    private inStream is =null;
    private ObjectOutputStream oos =null;
    private RelayServer relay = null;
    private String name = null;
    private int id = -1;
    
    public clientConnection(Socket soc, RelayServer rs,int _id) throws IOException, ClassNotFoundException {
        name = "User";
        s =soc;
        is =new inStream(s.getInputStream());
        oos =new ObjectOutputStream(s.getOutputStream());
        relay =rs;
        id = _id;
    }
    
    public void start() {
        Thread inputStreamThread =new Thread(is);
        
        inputStreamThread.start();
    }
    
    public String getName() {
        return this.name;
    }
    
    public Integer getID() {
        return this.id;
    }
    
    public void send(Message m) throws IOException {
        oos.writeObject(m);
        oos.flush();
    }
    
    private class inStream implements Runnable {
        private ObjectInputStream ois =null;
        
        public inStream(InputStream input) throws IOException, ClassNotFoundException {
            ois =new ObjectInputStream(input);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Message o =(Message)ois.readObject();
                    
                    if (o instanceof ClientInit) {
                        System.out.println("ClientInit received");
                        ClientInit ci =(ClientInit) o;
                        name =ci.name;
                        
                        UpdateUser um =new UpdateUser(id, name);
                        relay.relayMessage(clientConnection.this, um);
                    } else if (o instanceof AudioStream) {
                        System.out.println("Audio Message received");
                        relay.relayMessage(clientConnection.this, o);
                    } else if (o instanceof VideoStream) {
                        System.out.println("Video Message received");
                        relay.relayMessage(clientConnection.this, o);
                    } else if(o instanceof StringMessage) {
                        System.out.println("StringMessage received");
                        relay.relayMessage(clientConnection.this, o);
                    } else {
                        System.out.println("Some message received");
                        System.out.println("Message: "+o.getMessage());
                        relay.relayMessage(clientConnection.this, o);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Close connection.");
                    try {
                        relay.closeConnection(clientConnection.this);
                        break;
                    } catch (IOException ex1) {
                        System.out.println("Client notification error");
                    }
                }
            }
        }
        
    }
}
