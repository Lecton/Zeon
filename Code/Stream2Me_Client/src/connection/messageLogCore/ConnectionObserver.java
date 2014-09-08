/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messageLogCore;

import java.util.ArrayList;
import messages.Message;

/**
 *
 * @author Bernhard
 */
public class ConnectionObserver implements Runnable {
    static final ArrayList<TimeStampedMessage> messages =new ArrayList<TimeStampedMessage>();
    static int size =0;
    
    private static MessageLog log =new MessageLog();
    
    private boolean runnable =true;
    
    public ConnectionObserver() {
    }
    
    @Override
    public void run() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.setLocation(1000, 0);
//                log.setVisible(true);
            }
        });
        
        
        System.out.println("Starting observer");
        while (runnable) {
            if (size != messages.size()) {
                synchronized(messages) {
                    TimeStampedMessage msg =messages.get(size);
                    size++;
                    log.add(msg);
                }
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e){}
        }
    }
    
    public static void close() {
        log.close();
    }
    
    public static void write(Message msg) {
        synchronized (messages) {
            messages.add(new TimeStampedMessage(msg));
        }
    }
}