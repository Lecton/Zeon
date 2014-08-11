/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Messages.Message;
import Utils.Log;
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
        createStreams(soc);
    }
    
    public void createStreams(Socket soc) throws IOException {
        oos =new ObjectOutputStream((soc.getOutputStream()));
        ois =new ObjectInputStream((soc.getInputStream()));
    }
    
    public String getHostName() {
        return soc.getInetAddress().getHostName();
    }
    
    public Message read() throws IOException, ClassNotFoundException {
        Message m =(Message)ois.readObject();
        return m;
    }
    
    public void write(Message m) throws IOException {
            Log.write(this, "Sending "+m.handle());
        if (m.getTargetID() == Messages.Message.IGNORE) {
            Log.error(this, "Ignoring: "+m.handle());
        } else if (m.getTargetID() == Messages.Message.SERVER) {
            Log.error(this, "Server: "+m.handle());
        } else if (m.getTargetID() == Messages.Message.ERROR) {
            Log.error(this, "Error: "+m.handle());
        } else {
            synchronized (oos) {
                oos.writeObject(m);
                oos.flush();
            }
        }
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
            Log.write(this, "Write Stack Trace Exception");
            e.printStackTrace();
        }
    }
}