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
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class RelayServer {
    private int PORT =2000;
    private ServerSocket ss =null;
    private ConcurrentLinkedQueue<clientConnection> clients = null;
    private int idIncrementor = -1;
    
    public RelayServer(int port) throws IOException {
        System.out.println("Server Started");
        this.PORT =port;
        this.ss =new ServerSocket(PORT);
        clients =new ConcurrentLinkedQueue<>();
    }
    
    public void start() {
        try{
            while(true) {
                try {
                    Socket soc =ss.accept();
                    System.out.println("New client connection");
                    
                    Connection client =new Connection(soc);
                    idIncrementor = ++idIncrementor;
                    clientConnection cConnection =new clientConnection(client, this, idIncrementor);
                    (new Thread(cConnection)).start();
                    acceptMessage(cConnection);
                    System.out.println(cConnection.getName() + " joined");
                } catch (IOException e) {
                    System.err.println("Connection error");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getAllDetails(int[] IDs, String[] Names) {
        IDs =new int[clients.size()];
        Names =new String[clients.size()];
        
        int index =0;
        for (clientConnection cc: clients)
        {
            IDs[index] =cc.getID();
            Names[index] =cc.getName();
            index++;
        }
    }
    
    private int[] getAllIDs() {
        int[] IDs =new int[clients.size()];
        
        int index =0;
        for (clientConnection cc: clients)
        {
            IDs[index] =cc.getID();
            index++;
        }
        
        return IDs;
    }
    
    private String[] getAllNames() {
        String[] Names =new String[clients.size()];
        
        int index =0;
        for (clientConnection cc: clients)
        {
            Names[index] =cc.getName();
            index++;
        }
        
        return Names;
    }
    
    public void acceptMessage(clientConnection cc) throws IOException{
        cc.send(new Greeting(cc.getName(), cc.getID(), clients.size(), getAllIDs(), getAllNames(),"Server"));
        
        for (clientConnection client: clients)
        {
            client.send(new NewUser(clients.size(), cc.getID(), cc.getName(), "Server"));
        }
        
        clients.add(cc);
        
        System.out.println("Clients: " + clients.size());
    }
    
    public void relayMessage(clientConnection cc, Message mess) throws IOException {
        
        
        if (mess.getTo() == -1) {
            for (clientConnection client: clients) {
                if (!client.equals(cc)){
                    client.send(mess);
                }
            }
        } else {
            for (clientConnection client: clients) {
                if (client.getID() == mess.getTo()) {
                    client.send(mess);
                    break;
                }
            }
        }
    }
    
    public void closeConnection(clientConnection cc) throws IOException {
        clients.remove(cc);
        for (clientConnection client: clients) {
            client.send(new RemoveUser(cc.getID(), clients.size()-1, "Server"));
        }
        System.out.println("Clients: " + clients.size());
    }  
}
