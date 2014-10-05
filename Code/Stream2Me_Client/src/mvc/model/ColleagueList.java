/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

import communication.handlers.MessageFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import mvc.controller.Control;
import mvc.controller.UserControl;

/**
 *
 * @author Bernhard
 */
public class ColleagueList {
    private final static Logger LOGGER = Logger.getLogger(ColleagueList.class.getName());
    
    private final Map<String, Colleague> colleagues =Collections.synchronizedMap(new HashMap<String, Colleague>());
    
    public void add(Colleague person) {
        colleagues.put(person.getUserID(), person);
    }
    
    public void remove(String userID) {
        colleagues.remove(userID);
    }
    
    public Colleague get(String userID) {
        return colleagues.get(userID);
    }

    public List<String> resetVideoReceivers() {
        Collection<Colleague> colls =colleagues.values();
        List<String> users =new ArrayList<>();
        for (Colleague c: colls) {
            if (c.isReceivingVideo()) {
                users.add(c.getUserID());
            }
            c.setReceivingVideo(false);
        }
        return users;
    }

    public List<String> resetAudioReceivers() {
        Collection<Colleague> colls =colleagues.values();
        List<String> users =new ArrayList<>();
        for (Colleague c: colls) {
            if (c.isReceivingAudio()) {
                users.add(c.getUserID());
            }
            c.setReceivingAudio(false);
        }
        return users;
    }
}