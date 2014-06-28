/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import Messages.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.xml.crypto.dsig.XMLObject;

/**
 *
 * @author Bernhard
 */
public class RelayServer {
    private int PORT =2000;
    private ServerSocket ss =null;
    private ArrayList<clientConnection> clients = null;
    private int idIncrementor = -1;
    
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
                    idIncrementor = ++idIncrementor;
                    clientConnection c =new clientConnection(newConnection, this, idIncrementor);
                    clients.add(c);
                    
                    acceptMessage(c);
                    c.start();
                    System.out.println(c.getName() + " joined");
                } catch (IOException e) {
                    System.err.println("Connection error");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void acceptMessage(clientConnection cc) throws IOException{
        System.out.println("Connection");
        cc.send(new Greeting(cc.getName(), cc.getID(), "Server"));
        
        for (int i=0; i<clients.size(); i++) {
            clients.get(i).send(new NewUser(clients.size(), cc.getID(), cc.getName(), "Server"));
        }
    }
    
    public void relayMessage(clientConnection cc, Message mess) throws IOException {
        for (int i=0; i<clients.size(); i++) {
            if (!clients.get(i).equals(cc)){
                clients.get(i).send(mess);
            }
        }
    }
    
    public void closeConnection(clientConnection cc) throws IOException {
        for (int i=0; i<clients.size(); i++) {
            if (!clients.get(i).equals(cc)){
                clients.get(i).send(new RemoveUser(cc.getID(), clients.size()-1, "Server"));
            }
        }
        clients.remove(cc);
    }  
    
    public void xmlGenerator(){
        String sXML = "<?xml version='"+"1.0"+"' encoding='"+"UTF-8"+"'?>";
    }
}
