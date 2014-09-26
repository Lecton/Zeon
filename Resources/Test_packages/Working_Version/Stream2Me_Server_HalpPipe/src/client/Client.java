/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import channel.group.ClientChannel;
import io.netty.channel.Channel;

/**
 *
 * @author Bernhard
 */
public class Client extends ClientChannel {
    private int userID;
    boolean loggedIn =false;

    public Client(int userID, Channel ch) {
        super(ch);
        this.userID = userID;
        loggedIn =true;
    }

    public int getUserID() {
        return userID;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    
    public Channel getChannel() {
        return ch;
    }
}
