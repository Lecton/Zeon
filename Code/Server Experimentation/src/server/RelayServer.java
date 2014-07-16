/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import Messages.*;
import Messages.MessageUtils.MessageSender;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Bernhard
 * @author Lecton
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
        while(true) {
            try {
                Socket newConnection = ss.accept();
                idIncrementor = ++idIncrementor;
                clientConnection c =new clientConnection(newConnection, this, idIncrementor);
                acceptMessage(c);
                c.start();
                System.out.println(c.getName() + " joined");
            } catch (IOException e) {
                System.err.println("Connection error client connection not established");
            }
        }
    }
    
    private int[] getAllIDs() {
        int[] IDs =new int[clients.size()];
        for (int i=0; i<clients.size(); i++) {
            IDs[i] =clients.get(i).getID();
        }
        return IDs;
    }
    
    private String[] getAllNames() {
        String[] Names =new String[clients.size()];
        for (int i=0; i<clients.size(); i++) {
            Names[i] =clients.get(i).getName();
        }
        return Names;
    }
    
    public void acceptMessage(clientConnection cc) throws IOException{
        System.out.println("Connection");
//        cc.send(new Greeting(cc.getName(), cc.getID(), clients.size(), getAllIDs(), getAllNames(),"Server"));
        
//        clients.add(cc);
//        for (int i=0; i<clients.size(); i++) {
//                clients.get(i).send(new NewUser(clients.size(), cc.getID(), cc.getName(), "Server"));
//            }
//        }
        
        System.out.println("Clients: " + clients.size());
    }
    
    int check = 1;
    public void relayMessage(clientConnection cc, byte type, int to, byte[] message) throws IOException {
        if (to == -1) {
            for (int i=0; i<clients.size(); i++) {
                if (!clients.get(i).equals(cc)) {
                    MessageSender.relay(clients.get(i).getObjectOutputStream(), type, to, cc.getID(), message);
                }
            }
        } else {
            for (int i=0; i<clients.size(); i++) {
                if (clients.get(i).getID() == to) {
                    MessageSender.relay(clients.get(i).getObjectOutputStream(), type, to, cc.getID(), message);
                    break;
                }
            }
        }
    }
    
    public void closeConnection(clientConnection cc) throws IOException {
        for (int i=0; i<clients.size(); i++) {
            if (!clients.get(i).equals(cc)){
                    MessageSender.RemoveUser(clients.get(i).getObjectOutputStream(), cc.getID(), clients.size()-1);
//                clients.get(i).send(new RemoveUser(cc.getID(), clients.size()-1, "Server"));
            }
        }
        clients.remove(cc);
        System.out.println("Clients: " + clients.size());
    }  
}
