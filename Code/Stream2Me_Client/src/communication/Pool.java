/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication;

import io.netty.channel.ChannelHandlerContext;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import messages.Message;
import mvc.controller.Control;
import util.Log;

/**
 *
 * @author Bernhard
 */
public class Pool implements Runnable {
    private static final Queue<PoolEntry> pool =new ConcurrentLinkedQueue<>();
    private static boolean running =true;
    
    public static boolean add(ChannelHandlerContext ctx, Message msg) {
        System.out.println("Message added: "+msg.handle());
        return pool.add(new PoolEntry(ctx, msg, 0));
    }
    
    private static boolean add(ChannelHandlerContext ctx, Message msg, int counter) {
        if (counter >= 10) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Error sleeping");
            }
            return pool.add(new PoolEntry(ctx, msg, counter+1));
        } else if (counter >= 30) {
            System.out.println("Message discarded");
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
        Log.write(this.getClass(), "Connection Pool started");
        while (running) {
            if (!pool.isEmpty()) {
                PoolEntry msg =pool.remove();
                boolean result =Control.handleMessage(msg.message);
                if (!result) {
                    add(msg.ctx, msg.message, msg.counter);
                }
            }
        }
        Log.write(this.getClass(), "Message pool closed");
    }
}
