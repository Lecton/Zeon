/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

import java.util.ArrayList;

/**
 *
 * @author Bernhard
 */
public class ColleagueList {
    private ArrayList<Colleague> colleagues =new ArrayList<>();
    
    public int add(Colleague person) {
        colleagues.add(person);
        return colleagues.indexOf(person);
    }
    
    public void remove(Colleague person) {
        colleagues.remove(person);
    }
    
    public Colleague get(String userID) {
        for (Colleague person: colleagues) {
            if (person.getUserID().equals(userID)) {
                return person;
            }
        }
        return null;
    }
}