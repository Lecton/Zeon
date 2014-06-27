/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class clientConnection {
    private Socket s =null;
    private inStream is =null;
    private ObjectOutputStream oos =null;
    private RelayServer relay = null;
    
    public clientConnection(Socket soc, RelayServer rs) throws IOException {
        s =soc;
        is =new inStream(s.getInputStream());
        oos =new ObjectOutputStream(s.getOutputStream());
        relay =rs;
    }
    
    public void start() {
        Thread inputStreamThread =new Thread(is);
        
        inputStreamThread.start();
    }
    
    public void send(Object message) throws IOException {
        oos.writeObject(message);
        oos.flush();
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
                    Object o =ois.readObject();
                    String message =(String)o;
                    System.out.println("Message: "+message);
                    relay.relayMessage(clientConnection.this, o);
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Close connection.");
                    try {
                        relay.closeConnection(clientConnection.this);
                        break;
                    } catch (IOException ex1) {
                        ex1.printStackTrace();
                    }
                }
            }
        }
        
    }
}
