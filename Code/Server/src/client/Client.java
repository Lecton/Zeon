/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class Client {
    private int PORT =2000;
    private Socket connection =null;
    private ObjectOutputStream oos =null;
    private inStream is =null;
    
    public Client(int port) throws UnknownHostException, IOException {
        this.PORT =port;
        this.connection =new Socket("127.0.0.1", port);
        oos =new ObjectOutputStream(connection.getOutputStream());
        is =new inStream(connection.getInputStream());
    }
    
    public void start() {
        Scanner scan =new Scanner(System.in);
        Thread inputStreamThread =new Thread(is);
        inputStreamThread.start();
        
        while (true) {
            Object message =scan.nextLine();
            try {
                oos.writeObject(message);
                oos.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    public static void main(String[] args) {
        try {
            (new Client(2014)).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
