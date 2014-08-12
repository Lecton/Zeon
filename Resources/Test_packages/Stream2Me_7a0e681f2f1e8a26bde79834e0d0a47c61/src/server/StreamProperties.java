/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

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

    public StreamProperties(int ownerID, String ID, int[] allowedID) {
        this.ownerID = ownerID;
        this.ID = ID;
        this.allowedID = new ArrayList<>();
        this.acceptedID = new ArrayList<>();
        
        addAll(allowedID);
    }
    
    private void addAll(int[] add) {
        for (int addID: add) {
            allowedID.add(addID);
        }
    }
    
    public boolean compareID(String StreamID) {
        return ID.equals(StreamID);
    }
    
    public boolean accept(int ID) {
        for (int allowed: allowedID) {
            if (allowed == ID) {
                acceptedID.add(ID);
                return true;
            }
        }
        return false;
    }
    
    /**
     * ID has accepted and now does not want it anymore
     * @param ID
     * @return 
     */
    public boolean refuse(int ID) {
        for (int accepted: acceptedID) {
            if (accepted == ID) {
                acceptedID.remove(accepted);
                return true;
            }
        }
        return false;
    }
    
    public boolean remove(int ID) {
        refuse(ID);
        for (int allowed: allowedID) {
            if (allowed == ID) {
                allowedID.remove(allowed);
                return true;
            }
        }
        return false;
    }
    
    public int[] getTargets() {
        int[] result =new int[acceptedID.size()];
        for (int i=0; i<result.length; i++) {
            result[i] =acceptedID.get(i);
        }
        return result;
    }
    
}
