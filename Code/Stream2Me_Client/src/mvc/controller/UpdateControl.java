/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.controller.generalUI.message.ChatControl;
import mvc.controller.generalUI.contacts.ContactListControl;
import mvc.controller.generalUI.UserControl;
import mvc.controller.generalUI.GUIControl;
import java.awt.Event;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class UpdateControl {
    private final static Logger LOGGER = Logger.getLogger(UpdateControl.class.getName());
    
    public static UpdateControl INSTANCE =new UpdateControl();

    public static void clear() {
        INSTANCE.updates.clear();
        while (INSTANCE.usage.get() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
    
    private final ConcurrentLinkedQueue<Event> updates =new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Observer> threadPool =new ConcurrentLinkedQueue<>();
    private final AtomicInteger usage =new AtomicInteger(0);
    
    public static final int LOGIN =0;
    public static final int NEWUSER =1;
    public static final int REMOVEUSER =2;
    public static final int UPDATEDETAILS =3;
    public static final int UPDATECONTENT =4;
    public static final int STRINGMESSAGE =5;
    public static final int UPDATEAVATAR =6;
    public static final int VIDEONOTIFICATION =7;
    public static final int AUDIONOTIFICATION =8;
    
    public UpdateControl() {
        for (int i=0; i<10; i++) {
            Observer ob =new Observer();
            new Thread(ob).start();
            threadPool.add(ob);
        }
    }
    
    public void stop() {
        for (Observer ob: threadPool) {
            ob.stop();
        }
    }
    
    public void add(Object target, int action) {
        updates.add(new Event(target, action, null));
    }
    
    public void add(Object target, int action, Object arg) {
        updates.add(new Event(target, action, arg));
    }
    
    private Event getEvent() {
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
                        case LOGIN:
                            UserControl.INSTANCE.update();
                            break;
                        case NEWUSER:
                            ContactListControl.INSTANCE.addProfile((String)e.target);
                            break;
                        case REMOVEUSER:
                            ContactListControl.INSTANCE.removeProfile((String)e.target);
                            break;
                        case UPDATEDETAILS:
                            ContactListControl.INSTANCE.update((String)e.target);
                            //
                            
//                                ContactProfile nameProfile =ContactListControl.INSTANCE.getContact((String)e.target);
//                                nameProfile.setName((String)e.arg);
                            break;
                        case UPDATEAVATAR:
//                                ContactProfile avatarProfile =ContactListControl.INSTANCE.getContact((String)e.target);
//                                avatarProfile.setAvatar((String)e.arg);
                            break;
                        case UPDATECONTENT:
                            GUIControl.updateContent(e.target, (int)e.arg);
                            break;
                        case STRINGMESSAGE:
                            ChatControl.INSTANCE.checkMessage((String)e.target, (int)e.arg);
                            break;
                        case VIDEONOTIFICATION:
                            ContactListControl.INSTANCE.alertVideo((String)e.target, (boolean)e.arg);
                            break;
                        case AUDIONOTIFICATION:
                            ContactListControl.INSTANCE.alertAudio((String)e.target, (boolean)e.arg);
                            break;
                        default:

                    }
                    usage.decrementAndGet();
                }
            }
        }
    }
}
