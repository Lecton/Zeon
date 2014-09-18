/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.awt.Event;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Bernhard
 */
public class UpdateControl {
    public static UpdateControl INSTANCE =new UpdateControl();

    static void clear() {
        INSTANCE.updates.clear();
        while (INSTANCE.usage.get() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
    
    private ConcurrentLinkedQueue<Event> updates =new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Observer> threadPool =new ConcurrentLinkedQueue<>();
    private AtomicInteger usage =new AtomicInteger(0);
    
    public static final int LOGIN =0;
    public static final int NEWUSER =1;
    public static final int REMOVEUSER =2;
    public static final int UPDATENAME =3;
    public static final int UPDATEAVATAR =4;
    
    public UpdateControl() {
        for (int i=0; i<10; i++) {
            Observer ob =new Observer();
            new Thread(ob).start();
            threadPool.add(ob);
        }
    }
    
    protected void stop() {
        for (Observer ob: threadPool) {
            ob.stop();
        }
    }
    
    public void add(Object target, int action) {
        updates.add(new Event(target, action, null));
    }
    
    protected Event getEvent() {
        return updates.poll();
    }
    
    private class Observer implements Runnable {
        private boolean running =true;

        public void stop() {
            running =false;
        }

        @Override
        public void run() {
            while (running) {
                Event e =UpdateControl.INSTANCE.getEvent();
                if (e == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                    }
                } else {
                    usage.incrementAndGet();
                    switch (e.id) {
                        case UpdateControl.LOGIN:
                            String userID =(String)e.target;
                            UserControl.INSTANCE.update(userID);
                            break;
                        case UpdateControl.NEWUSER:
                            ContactListControl.INSTANCE.addProfile((String)e.target);
                            break;
                        case UpdateControl.REMOVEUSER:
                            ContactListControl.INSTANCE.removeProfile((String)e.target);
                            break;
                        case UpdateControl.UPDATENAME:
//                                ContactProfile nameProfile =ContactListControl.INSTANCE.getContact((String)e.target);
//                                nameProfile.setName((String)e.arg);
                            break;
                        case UpdateControl.UPDATEAVATAR:
//                                ContactProfile avatarProfile =ContactListControl.INSTANCE.getContact((String)e.target);
//                                avatarProfile.setAvatar((String)e.arg);
                            break;
                        default:

                    }
                    usage.decrementAndGet();
                }
            }
        }
    }
}
