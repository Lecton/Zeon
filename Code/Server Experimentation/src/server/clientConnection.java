/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import Messages.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 *
 * @author Bernhard
 */
public class clientConnection {
    private Socket socket =null;
    private InStream is =null;
    private ObjectOutputStream oos =null;
    private ObjectInputStream ois =null;
    
    private RelayServer relay = null;
    private String name = null;
    private int id = -1;
    
    public clientConnection(Socket socket, RelayServer rs, int id) throws IOException {
        name = "User";
        this.socket =socket;
        is =new InStream(this);
        oos =new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        ois =new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        relay =rs;
        this.id = id;
    }
    
    public void start() {
        Thread inputStreamThread =new Thread(is);
        inputStreamThread.start();
    }
    
    public void setName(String name) {
        this.name =name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Integer getID() {
        return this.id;
    }
    
    public ObjectInputStream getObjectInputStream() throws IOException {
        return ois;
    }
    
    public ObjectOutputStream getObjectOutputStream() throws IOException {
        return oos;
    }
    
    public void closeConnection() throws IOException {
        relay.closeConnection(this);
    }
    
    public void relayMessage(byte type, int to, int from, byte[] message) throws IOException {
        relay.relayMessage(this, type, to, message);
    }
    
//    public void pipe(byte type) throws IOException {
//        int to =ois.readInt();
//        int from =ois.readInt();
//        
//        int val =-1;
//        while ((val =ois.read()) != -1) {
//            oos.write(val);
//        }
//        oos.flush();
//    }
}
