/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import User.Client;
import Messages.UserConnection.Greeting;
import Messages.UserConnection.Logout;
import Utils.Log;
import DatabaseBuilder.MessageBuilder;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
    
    private boolean running =false;
    
    private ArrayList<StreamProperties> streams;
    
    public RelayServer(int port) throws IOException {
        System.out.println("Server Started");
        this.DB =new Database();
        this.PORT =port;
        this.ss =new ServerSocket(PORT);
        clients =new ConcurrentLinkedQueue<>();
        streams =new ArrayList<>();
    }
    
    public void stop() {
        DB.close();
        running =false;
    }
    
    public void start() {
        running =true;
        try {
            while(running) {
                try {
                    
                    try {
                        Socket soc =ss.accept();
                        Log.write(this, "New client connection accepted");

                        Connection client =new Connection(soc);
                        Client cConnection =new Client(client, this);
                        (new Thread(cConnection)).start();

                        Log.write(this, "User joined");
                    } catch (SocketException se) {
                        Log.error(this, "Client connection unsucessful");
                    }
                    
                    
                    
                } catch (IOException e) {
                    Log.error(this, "Connection error");
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Log.error(this, "Server error");
            e.printStackTrace();
        } finally {
            this.DB.close();
        }
    }
    
    public void relayDefault(Client cc, Messages.Message m) {
        Log.write(this, "Relay "+m.getClass().getSimpleName()+" to: "+m.getTargetID());
        
        if (m.getTargetID() == Messages.Message.IGNORE) {
        } else if (m.getTargetID() == Messages.Message.SERVER) {
            Log.write(this, "Server message received");
        } else if (m.getTargetID() == Messages.Message.ERROR) {
            Log.error(this, "Error in message relay on message type "+m.handle());
        } else if (m.getTargetID() == Messages.Message.ALL) {
            for (Client c: clients) {
                if (!c.equals(cc)){
                    c.send(m);
                }
            }
        } else if (m.getTargetID() < 0) {
            Log.error(this, "Unknown target type "+m.getTargetID());
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
        if (cc.isLoggedIn()) {
            clients.remove(cc);
            for (Client client: clients) {
                client.send(new Logout(cc.getID(), Messages.Message.ALL));
            }

            DB.logoff(cc.getID());
            cc.setLoggedIn(false);

            Log.write(this, "Clients: " + clients.size());
        }
    }
    
    public StreamProperties getStreamProperties(String StreamID) {
        for (StreamProperties sp: streams) {
            if (sp.compareID(StreamID)) {
                return sp;
            }
        }
        return null;
    }
    
    public void createStreamProperty(int userID, String StreamID) {
        StreamProperties sp =new StreamProperties(userID, StreamID);
        streams.add(sp);
    }

    public Greeting userLogin(Client c, String username, String passwordHash) {
        int ID =DB.login(username, passwordHash);
        c.setID(ID);
        
        Log.write(this, "USERLOGIN - ID: "+ID+" - "+c.isLoggedIn());
        
        if (ID != Messages.Message.ERROR && !c.isLoggedIn()) {
            clients.add(c);
            c.setLoggedIn(true);
            relayDefault(c, MessageBuilder.generateNewUser(getUserProfile(c.getID())));
            return MessageBuilder.generateGreeting(getUserProfile(ID), true);
        }
        return new Greeting(false);
    }
    
    public void updateUserConnection(Client cc) {
        for (Client c: clients) {
            if (!c.equals(cc)) {
                cc.send(DB.getNewUser(c.getID()));
            }
        }
    }

    public Database getDB() {
        return DB;
    }
    
    private User.Profile getUserProfile(int userID) {
        return DB.getUserProfile(userID);
    }

    public void removeStreamProperty(int userID, String streamID) {
        StreamProperties sp =getStreamProperties(streamID);
        if (sp != null) {
            streams.remove(sp);
            
            Log.error(this, "Implement notification of everyone on list");
            //notify everyone
        }
    }
}
