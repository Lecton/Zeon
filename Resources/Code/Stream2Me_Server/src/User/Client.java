/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package User;

import Messages.Message;
import Server.Connection;
import Messages.MessageHandler;
import Server.RelayServer;
import Utils.Log;
import java.io.IOException;

/**
 *
 * @author Bernhard
 */
public class Client implements Runnable {
    private Connection con =null;
    private RelayServer relay = null;
    private int ID = -1;
    private boolean loggedIn =false;
    
    public Client(Connection con, RelayServer rs) throws IOException, ClassNotFoundException {
        this.con =con;
        relay =rs;
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
                Log.error(this, "Close connedtion.");
                try {
                    relay.closeConnection(Client.this);
                    break;
                } catch (IOException ex1) {
                    Log.error(this, "Client notification error");
                }
            }
        }
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
    
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn =loggedIn;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
}