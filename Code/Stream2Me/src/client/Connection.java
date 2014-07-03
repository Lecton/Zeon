/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Messages.Message;

/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class Connection {
    private int PORT;
    private String address;
    private Socket soc;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public Connection() {
        PORT =2014;
        address ="127.0.0.1";
    }
    
    public void setPORT(int PORT) {
        this.PORT =PORT;
    }
    
    public void setAddress(String address) {
        this.address =address;
    }
    
    public void makeConnection() throws IOException {
        soc =new Socket(address, PORT);
        oos =new ObjectOutputStream(soc.getOutputStream());
        ois =new ObjectInputStream(soc.getInputStream());
    }
    
    public ObjectOutputStream getOutputStream() throws IOException {
        return oos;
    }
    
    public ObjectInputStream getInputStream() throws IOException {
        return ois;
    }
    
    public void write(Message m) {
        try {
            oos.writeObject(m);
            oos.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
