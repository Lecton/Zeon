/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import java.util.ArrayList;

/**
 *
 * @author Bernhard
 */
public class StreamProperties {
    private int ownerID;
    private String ID;
    private ArrayList<Integer> acceptedID;
    private ArrayList<Integer> allowedID;

    public StreamProperties(int ownerID, String ID) {
        this.ownerID = ownerID;
        this.ID = ID;
        this.allowedID = new ArrayList<>();
        this.acceptedID = new ArrayList<>();
    }
    
    public boolean compareID(String StreamID) {
        return ID.equals(StreamID);
    }
    
    public boolean accept(int userID) {
        for (int allowed: allowedID) {
            if (allowed == userID) {
                acceptedID.add(userID);
                return true;
            }
        }
        return false;
    }
    
    public boolean refuse(int userID) {
        for (int accepted: acceptedID) {
            if (accepted == userID) {
                acceptedID.remove(accepted);
                return true;
            }
        }
        return false;
    }
    
    public boolean remove(int ownerID, int userID) {
        refuse(userID);
        for (int allowed: allowedID) {
            if (allowed == userID) {
                allowedID.remove(allowed);
                return true;
            }
        }
        return false;
    }
    
    public void add(int ownerID, int userID) {
        if (ownerID == this.ownerID) {
            allowedID.add(userID);
        }
    }
    
    public int[] getTargets() {
        int[] result =new int[acceptedID.size()];
        for (int i=0; i<result.length; i++) {
            result[i] =acceptedID.get(i);
        }
        return result;
    }
    
}
