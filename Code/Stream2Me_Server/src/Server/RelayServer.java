/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Messages.UserConnection.Logout;
import Messages.UserConnection.NewUser;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class RelayServer {
    private int PORT =2000;
    private ServerSocket ss =null;
    private ConcurrentLinkedQueue<Client> clients = null;
    private Database DB;
    
    private ArrayList<StreamProperties> streams;
    
    public RelayServer(int port) throws IOException {
        System.out.println("Server Started");
        this.DB =new Database();
        this.PORT =port;
        this.ss =new ServerSocket(PORT);
        clients =new ConcurrentLinkedQueue<>();
        streams =new ArrayList<>();
    }
    
    public void start() {
        try{
            while(true) {
                try {
                    Socket soc =ss.accept();
                    System.out.println("New client connection");
                    
                    Connection client =new Connection(soc);
                    Client cConnection =new Client(client, this);
                    (new Thread(cConnection)).start();
                    
                    System.out.println(cConnection.getUsername() + " joined");
                } catch (IOException e) {
                    System.err.println("Connection error");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void relayDefault(Client cc, Messages.Message m) {
        System.out.println("Relay "+m.getClass().getSimpleName()+" to: "+m.getTargetID());
        if (m.getTargetID() == -1) {
            for (Client c: clients) {
                if (!c.equals(cc)){
                    c.send(m);
                }
            }
        } else {
            for (Client c: clients) {
                if (c.getID() == m.getTargetID()) {
                    c.send(m);
                    break;
                }
            }
        }
    }
    
    public void closeConnection(Client cc) throws IOException {
        clients.remove(cc);
        for (Client client: clients) {
            client.send(new Logout(cc.getID(), "Server"));
        }
        System.out.println("Clients: " + clients.size());
    }
    
    public StreamProperties getStreamProperties(String StreamID) {
        for (StreamProperties sp: streams) {
            if (sp.compareID(StreamID)) {
                return sp;
            }
        }
        return null;
    }
    
    public void addStreamProperty(int ID, String StreamID, int[] allowedID) {
        if (allowedID.length == 1 && allowedID[0] == -1) {
//            allowedID =getAllIDs();
        }
        StreamProperties sp =new StreamProperties(ID, StreamID, allowedID);
        streams.add(sp);
    }

    public NewUser userLogin(Client c, String username, String passwordHash) {
        int ID =DB.login(username, passwordHash);
        c.setID(ID);
        System.out.println("USERLOGIN - ID: "+ID);
        if (ID != -1) {
            clients.add(c);
            return DB.getNewUser(ID);
        }
        return null;
    }
    
    public void updateUserConnection(Client cc) {
        for (Client c: clients) {
            cc.send(DB.getNewUser(c.getID()));
        }
    }
}
