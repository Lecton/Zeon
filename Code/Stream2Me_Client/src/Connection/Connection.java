/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connection;

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
    private int PORT;
    private String address;
    private Socket soc;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public Connection(String address, int PORT) {
        this.address =address;
        this.PORT =PORT;
    }
    
    public void makeConnection() throws IOException {
        soc =new Socket(address, PORT);
        createStreams(soc);
    }
    
    public void createStreams(Socket soc) throws IOException {
        oos =new ObjectOutputStream((soc.getOutputStream()));
        ois =new ObjectInputStream((soc.getInputStream()));
    }
    
    public String getHostName() {
        return soc.getInetAddress().getHostName();
    }
    
    public Messages.Message read() throws IOException, ClassNotFoundException {
        return (Messages.Message)ois.readObject();
    }
    
    private void writeMessage(Messages.Message m) throws IOException {
        synchronized (oos) {
            oos.writeObject(m);
            oos.flush();
        }
    }
    
    public void write(Messages.Message m) throws IOException {
        if (m.getTargetID() == Messages.Message.IGNORE) {
            Log.error(this, "Message "+m.handle()+" ignored");
        } else if (m.getTargetID() == Messages.Message.SERVER) {
            writeMessage(m);
        } else if (m.getTargetID() == Messages.Message.ERROR) {
            Log.error(this, "Message "+m.handle()+" error");
        } else if (m.getTargetID() < 0) {
            Log.error(this, "Unknown message type on "+m.handle());
        } else {
            writeMessage(m);
        }
    }
    
    public void writeSafe(Messages.Message m) {
        try {
            write(m);
        } catch (Exception ignored) {}
    }
    
    public void writeStackTrace(Messages.Message m) {
        try {
            write(m);
        } catch (IOException e) {
            Log.write(this, "Write Stack Trace Exception");
            e.printStackTrace();
        }
    }
}
