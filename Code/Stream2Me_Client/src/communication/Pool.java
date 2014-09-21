/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication;

import io.netty.channel.ChannelHandlerContext;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import mvc.controller.Control;

/**
 *
 * @author Bernhard
 */
public class Pool implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(Pool.class.getName()); 
    
    private static final Queue<PoolEntry> pool =new ConcurrentLinkedQueue<>();
    private static boolean running =true;
    
    public static boolean add(ChannelHandlerContext ctx, Message msg) {
        LOGGER.log(Level.INFO, "Message added: "+msg.handle());
        return pool.add(new PoolEntry(ctx, msg, 0));
    }
    
    private static boolean add(ChannelHandlerContext ctx, Message msg, int counter) {
        if (counter >= 10) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Error sleeping");
            }
            return pool.add(new PoolEntry(ctx, msg, counter+1));
        } else if (counter >= 30) {
            LOGGER.log(Level.WARNING, "Message discarded");
            return false;
        } else {
            return pool.add(new PoolEntry(ctx, msg, counter+1));
        }
    }
    
    public static void stop() {
        running =false;
    }
    
    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Connection Pool started");
        while (running) {
            if (!pool.isEmpty()) {
                PoolEntry msg =pool.remove();
                boolean result =Control.handleMessage(msg.message);
                if (!result) {
                    add(msg.ctx, msg.message, msg.counter);
                }
            }
        }
        LOGGER.log(Level.INFO, "Message pool closed");
    }
}
