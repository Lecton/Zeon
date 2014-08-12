/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import Messages.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Bernhard
 */
public class Connection {
    private Socket soc;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public Connection(Socket soc) throws IOException {
        this.soc =soc;
        this.oos =new ObjectOutputStream((soc.getOutputStream()));
        this.ois =new ObjectInputStream((soc.getInputStream()));
    }
    
    public String getHostName() {
        return soc.getInetAddress().getHostName();
    }
    
    public Message read() throws IOException, ClassNotFoundException {
        return (Message)ois.readObject();
    }
    
    public void write(Message m) throws IOException {
        oos.writeObject(m);
        oos.flush();
    }
    
    public void writeSafe(Message m) {
        try {
            write(m);
        } catch (Exception ignored) {}
    }
    
    public void writeStackTrace(Message m) {
        try {
            write(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}