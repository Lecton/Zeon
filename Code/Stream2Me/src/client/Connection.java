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
    
    /**
     * Constructor to assign a default IP address and port number.
     */
    public Connection() {
        PORT =2014;
        address ="127.0.0.1";
    }
    
    /**
     * Sets a specific port number for this connection.
     * @param PORT - the port number to set the original port to.
     */
    public void setPORT(int PORT) {
        this.PORT =PORT;
    }
    
    /**
     * Sets a specific IP address for this connection.
     * @param address 
     */
    public void setAddress(String address) {
        this.address =address;
    }
    
    /**
     * Establishes the connection between a client and the server.
     * @throws IOException 
     */
    public void makeConnection() throws IOException {
        soc =new Socket(address, PORT);
        oos =new ObjectOutputStream(soc.getOutputStream());
        ois =new ObjectInputStream(soc.getInputStream());
    }
    
    /**
     * Returns the object output stream and its associated messages for this
     * connection.
     * @return
     * @throws IOException 
     */
    public ObjectOutputStream getOutputStream() throws IOException {
        return oos;
    }
    
    /**
     * Returns the object input stream and its associated messages for this
     * connection.
     * @return
     * @throws IOException 
     */
    public ObjectInputStream getInputStream() throws IOException {
        return ois;
    }
    
    /**
     * Writes the message being passed to it via the connection 
     * by identifying the type and determining the appropriate way of handling
     * that specific message type.
     * @param m - message to be sent.
     */
    public void write(Message m) {
        try {
            oos.writeObject(m);
            oos.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
