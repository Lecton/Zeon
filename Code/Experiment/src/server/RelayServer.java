/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Bernhard
 */
public class RelayServer {
    private int PORT =2000;
    private ServerSocket ss =null;
    private ArrayList<clientConnection> clients =null;
    
    public RelayServer(int port) throws IOException {
        System.out.println("Server Started");
        this.PORT =port;
        this.ss =new ServerSocket(PORT);
        clients =new ArrayList<>();
    }
    
    public void start() {
        try{
            while(true) {
                try {
                    Socket newConnection = ss.accept();
                    clientConnection c =new clientConnection(newConnection, this);
                    clients.add(c);
                    accepMessage(c);
                    c.start();
                    System.out.println("Client joined");
                } catch (IOException e) {
                    System.err.println("Connection error");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void accepMessage(clientConnection cc) throws IOException{
        System.out.println("Connection");
        for (int i=0; i<clients.size(); i++) {
            if (clients.get(i).equals(cc)){
                clients.get(i).send("Welcome client.");
            }
        }        
        
        for (int i=0; i<clients.size(); i++) {
                clients.get(i).send("Update " + ":" + clients.size());
        }        
    }
    
    public void relayMessage(clientConnection cc, Object Message) throws IOException {
        for (int i=0; i<clients.size(); i++) {
            if (!clients.get(i).equals(cc)){
                clients.get(i).send(Message);
            }
        }
    }
    
    public void closeConnection(clientConnection cc) throws IOException {
        for (int i=0; i<clients.size(); i++) {
            if (!clients.get(i).equals(cc)){
                clients.get(i).send(cc + " left the chat.");
            }
        }
        clients.remove(cc);
        
        for (int i=0; i<clients.size(); i++) {
                clients.get(i).send("Update " + ":" + clients.size());
        }
    }    
}
