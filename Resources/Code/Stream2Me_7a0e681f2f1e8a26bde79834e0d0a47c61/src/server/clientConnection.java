/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import Messages.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Bernhard
 */
public class clientConnection implements Runnable {
    private Connection con =null;
    private RelayServer relay = null;
    private String name = null;
    private int id = -1; 
    
    public clientConnection(Connection con, RelayServer rs,int _id) throws IOException, ClassNotFoundException {
        name = con.getHostName();
        this.con =con;
        relay =rs;
        id = _id;
    }
    
    public void setName(String name) {
        this.name =name;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Message m =con.read();

                relay.relayMessage(this, m.repackage(this));
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("Close connection.");
                try {
                    relay.closeConnection(clientConnection.this);
                    break;
                } catch (IOException ex1) {
                    System.out.println("Client notification error");
                }
            }
        }
    }
    
    public String getName() {
        return this.name;
    }
    
    public Integer getID() {
        return this.id;
    }
    
    public void send(Message m) throws IOException {
        con.write(m);
    }
    
    public StreamProperties getStreamProperties(String StreamID) {
        return relay.getStreamProperties(StreamID);
    }
    
    public void addStreamProperty(String StreamID, int[] allowedID) {
        relay.addStreamProperty(id, StreamID, allowedID);
    }
}