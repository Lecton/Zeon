/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages;

import client.GUI.GUI;
import server.clientConnection;

/**
 *
 * @author Bernhard
 */
public class AudioProperty extends Message {
    int type =0; //1 on  -  0 off
    String streamID ="";
    int[] allowed =null;

    public AudioProperty(int ID, int to, String streamID, int type) {
        this.ID =ID;
        this.to =to;
        this.streamID =streamID;
        this.type =type;
    }

    @Override
    public String getMessage() {
        return "Audio Property";
    }

    @Override
    public void handle(GUI userInterface) {
        System.out.println("Audio Stream Property parsed");
        int myID =userInterface.getID();
        if (contained(myID)) {
            userInterface.getContactPane().getColleague(myID).setIncomingAudio(type == 1);
            userInterface.getConnection().writeSafe(new AudioResponse(myID, this.streamID, true));
        }
    }

    @Override
    public Message repackage(clientConnection cc) {
        if (type == 1) {
            cc.addStreamProperty(streamID, allowed);
            System.out.println("StreamProperty added "+streamID);
        }
        return this;
    }

    public void setAllowed(int[] allowed) {
        this.allowed =allowed;
    }

    public String getStreamID() {
        return streamID;
    }
    
    public int getType() {
        return type;
    }
    
    private boolean contained(int ID) {
        if (allowed == null) return false;
        
        for (int i=0; i<allowed.length; i++) {
            if (allowed[i] == ID) {
                return true;
            }
        }
        return false;
    }
}
