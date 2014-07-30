/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Messages.Message;
import java.io.IOException;

/**
 *
 * @author Bernhard
 */
public class Client implements Runnable {
    private Connection con =null;
    private RelayServer relay = null;
    private String username = null;
    private int ID = -1; 
    
    public Client(Connection con, RelayServer rs) throws IOException, ClassNotFoundException {
        username = con.getHostName();
        this.con =con;
        relay =rs;
    }
    
    public void setName(String username) {
        this.username =username;
    }
    
    public void setID(int ID) {
        this.ID =ID;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Message m =con.read();
                MessageHandler.handle(this, relay, m);

//                relay.relayMessage(this, m.repackage(this));
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Close connection.");
                try {
                    relay.closeConnection(Client.this);
                    break;
                } catch (IOException ex1) {
                    System.out.println("Client notification error");
                }
            }
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public Integer getID() {
        return this.ID;
    }
    
    public void send(Message m) {
        try {
            con.write(m);
        } catch (IOException e) {
            
        }
    }
    
    public StreamProperties getStreamProperties(String StreamID) {
        return relay.getStreamProperties(StreamID);
    }
    
    public void addStreamProperty(String StreamID, int[] allowedID) {
        relay.addStreamProperty(ID, StreamID, allowedID);
    }
}