/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import Messages.Greeting;
import Messages.MessageUtils.MessageReceiver;
import Messages.MessageUtils.MessageSender;
import Messages.MessageUtils.MessageUtils;
import Messages.UpdateUser;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Bernhard
 */
public class Client {
    Socket sc;
    
    public Client() throws IOException {
        sc =new Socket("127.0.0.1",2014);
        System.out.println("Connected");
        
        Thread write =new Thread(new Writer());
        write.start();
        
        Thread read =new Thread(new Reader());
        read.start();
    }
    
    public class Writer implements Runnable {
        
        @Override
        public void run() {
            System.out.println("Writer started");
            Scanner scan =new Scanner(System.in);
            String line =scan.nextLine();
            ObjectOutputStream oos = null;
            try {
                oos = new ObjectOutputStream(sc.getOutputStream());
            } catch (IOException ex) {
            }
            
//            while (true) {
                try {
                    MessageSender.ClientInit(oos, -1, 1, "Bernhard");
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
        }
        
    }
    
    public class Reader implements Runnable {

        @Override
        public void run() {
            System.out.println("Reader started");
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(sc.getInputStream());
            } catch (IOException ex) {
            }
            
            while (true) {
                try {
                    byte type =ois.readByte();
                    System.out.println("Type: "+type);
                    switch (type) {
                        case MessageUtils.UPDATEUSER: 
                            UpdateUser uu =MessageReceiver.UpdateUser(ois);
                            System.out.println(uu.getMessage());
                            break;
                        case MessageUtils.GREETING:
                            Greeting g =MessageReceiver.Greeting(ois);
                            
                        default:
                            break;
                    }
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        
    }
    
    public static void main(String[] args) throws IOException {
        Client c =new Client();
    }
}
