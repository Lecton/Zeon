/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import mvc.controller.UserControl;
import java.awt.Event;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;
import mvc.controller.ContactListControl;
import mvc.model.Colleague;
import mvc.view.generalUI.contacts.ContactProfile;

/**
 *
 * @author Bernhard
 */
public class UpdateControl {
    public static UpdateControl INSTANCE =new UpdateControl();
    private ConcurrentLinkedQueue<Event> updates =new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Observer> threadPool =new ConcurrentLinkedQueue<>();
    
    public static final int LOGIN =0;
    public static final int UPDATENAME =1;
    public static final int UPDATEAVATAR =2;
    public static final int NEWUSER =3;
    public static final int REMOVEUSER =4;
    
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
    
    public void add(Object target, int action, Object arg) {
        updates.add(new Event(target, action, arg));
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
//                    try {
                        switch (e.id) {
                            case UpdateControl.LOGIN:
                                UserControl.INSTANCE.setName((String)e.target);
                                UserControl.INSTANCE.setAvatar((String)e.arg);
                                break;
                            case UpdateControl.UPDATENAME:
//                                ContactProfile nameProfile =ContactListControl.INSTANCE.getContact((String)e.target);
//                                nameProfile.setName((String)e.arg);
                                break;
                            case UpdateControl.UPDATEAVATAR:
//                                ContactProfile avatarProfile =ContactListControl.INSTANCE.getContact((String)e.target);
//                                avatarProfile.setAvatar((String)e.arg);
                                break;
                            case UpdateControl.NEWUSER:
//                                ContactListControl.INSTANCE.addPerson((Colleague)e.target, (int)e.arg);
                                break;
                            case UpdateControl.REMOVEUSER:
//                                ContactListControl.INSTANCE.removePerson((String)e.target);
                                break;
                            default:
                                
                        }
//                    } catch (Exception ex) {
//                        UpdateControl.INSTANCE.add(e.target, e.id, e.arg);
//                    }
                }
            }
        }
    }
}
