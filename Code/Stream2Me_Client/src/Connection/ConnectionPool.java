/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connection;

import Messages.Message;
import Utils.Log;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class ConnectionPool implements Runnable {
    private ArrayList<MessageEntries> pool =new ArrayList<MessageEntries>();
    private final ConnectionHandler ch;
    private boolean open =true;

    public ConnectionPool(ConnectionHandler ch) {
        this.ch =ch;
    }
    
    public boolean add(ChannelHandlerContext ctx, Message msg) {
        if (open) {
            return pool.add(new MessageEntries(ctx, msg, 0));
        }
        return false;
    }
    
    @Override
    public void run() {
        Log.write(this, "Connection Pool started");
        while (!ch.isPass()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        open =false;
        
        
        Log.write(this, "Emptying pool "+pool.size());
        while (!pool.isEmpty()) {
            MessageEntries poolMsg =pool.remove(0);
            try {
                ch.channelRead0(poolMsg.ctx, poolMsg.msg);
            } catch (Exception ex) {
                poolMsg.counter++;
                if (poolMsg.counter < 5) {
                    pool.add(poolMsg);
                } else if (poolMsg.counter < 20) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {}
                    pool.add(poolMsg);
                }
            }
        }
        Log.write(this, "Message pool closed");
    }
    
    private class MessageEntries {
        Message msg;
        ChannelHandlerContext ctx;
        int counter;

        public MessageEntries(ChannelHandlerContext ctx, Message msg, int counter) {
            this.ctx = ctx;
            this.msg = msg;
            this.counter =counter;
        }
    }
}
