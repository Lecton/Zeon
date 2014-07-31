/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interface.ClientLogin;

import Client.Colleague;
import Connection.Connection;
import Utils.Log;
import java.io.IOException;

/**
 *
 * @author Bernhard
 */
public class streamListener implements Runnable {
    private boolean running;
    private Connection con;
    private Login owner;

    public streamListener(Connection con, Login owner) {
        this.con = con;
        this.owner =owner;
        this.running =false;
    }
    
    @Override
    public void run() {
        running =true;
        while (running) {
            try {
                Messages.Message m =con.read();
                
                switch (m.handle()) {
                    case greeting:
                        Messages.UserConnection.Greeting greet =(Messages.UserConnection.Greeting)m;
                        if (greet.isSuccessful()) {
                            owner.setResponse("Success");
                            owner.setMyAccount(new Colleague(greet.getUserID(), greet.getUsername(), greet.getEmail(), greet.getAvatar()));
                            owner.successful();
                            running =false;
                        } else {
                            owner.setResponse("Invalid username or password");
                        }
                        break;
                    default:
                        Log.write(this, "Received unhandled message");
                        break;
                }
                
            } catch (IOException | ClassNotFoundException ex) {
            }
            
        }
    }
    
}
