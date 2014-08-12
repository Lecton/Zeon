package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Messages.Message;

public class Connection {
    private int PORT;
    private String address;
    private Socket soc;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public Connection() {
        PORT =2000;
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
        oos =new ObjectOutputStream((soc.getOutputStream()));
        ois =new ObjectInputStream((soc.getInputStream()));
    }
    
    public Message read() throws IOException, ClassNotFoundException {
        Message m =(Message)ois.readObject();
        System.out.println("Reading: "+m.handle()+" userID: "+m.getUserID()+" TargetID: "+m.getTargetID());
        return m;
    }
    
    public void write(Message m) throws IOException {
        if (m.getTargetID()!= -2) {
            System.out.println("Writing: "+m.handle());
            synchronized (oos) {
                oos.writeObject(m);
                oos.flush();
            }
        } else {
            System.out.println("Not sending: "+m.getTargetID());
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
            e.printStackTrace();
        }
    }
}
