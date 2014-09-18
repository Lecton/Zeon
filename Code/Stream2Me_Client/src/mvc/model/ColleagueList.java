/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bernhard
 */
public class ColleagueList {
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
}