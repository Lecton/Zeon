/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class Colleague {
    public int ID =-1;
    public String name ="";
    public String localName ="";
//    public String content = "This is the label";
    
    private HashMap<String, String> chatHistory;
    
    /**
     * Create a new Colleague and set its default panel content
     */
    public Colleague() {
        chatHistory =new HashMap<String, String>();
    }
    
    public void addMessage(String Sender, String mess) {
        chatHistory.put(Sender, name);
    }
    
    public HashMap<String, String> getMessages() {
        return chatHistory;
    }

    /**
     * Checks if the current colleague is contained in the list
     * @param list Array of colleague objects
     * @return index if list if contained. -1 if not
     */
    public int contained(ArrayList<Colleague> list) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).ID == ID) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Updates the other colleague object with the details in the current one 
     * if the current one has the same ID and the other one
     * @param other Colleague to update
     * @return true if successfully update. false if not
     */
    public boolean update(Colleague other) {
        if (other.ID == ID) {
            if (other.localName.equalsIgnoreCase(other.name)) {
                other.localName =name;
            }
            other.name = name;
            return true;
        } else {
            return false;
        }
    }

    public void setName(String name) {
        if (this.name.equalsIgnoreCase(this.localName)) {
            this.localName =name;
        }
        this.name =name;
    }

    public void setlocalName(String localName) {
        this.localName =localName;
    }
}