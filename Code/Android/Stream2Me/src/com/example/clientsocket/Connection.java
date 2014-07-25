package com.example.clientsocket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection {
	
	int PORT;
	String address;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    Socket soc;
	
    public Connection(){}
    
	public Connection(int p){
		this.PORT = p;
		this.address = "127.0.0.1";
	}
	
	public Connection(String a){
		this.PORT = 2014;
		this.address = a;
	}

	public Connection(int p,String a){
		this.PORT = p;
		this.address = a;
	}
	
    public void setPORT(int PORT) {
        this.PORT =PORT;
    }
    
    public void setAddress(String address) {
        this.address =address;
    }
    
    public void makeConnection() throws UnknownHostException, IOException{
        soc =new Socket(address, PORT);
        oos =new ObjectOutputStream(soc.getOutputStream());
        ois =new ObjectInputStream(soc.getInputStream());
    }
    
    public void write(String m) {
        try {
            oos.writeObject(m);
            oos.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public Object read() throws OptionalDataException, ClassNotFoundException, IOException{
    	return ois.readObject();
    }
}
