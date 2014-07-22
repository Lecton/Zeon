/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import MediaStreaming.Audio.AudioCapture;
import MediaStreaming.Video.ScreenCapture;
import MediaStreaming.Video.StreamVideo;
import Messages.AudioStream;
import Messages.StringMessage;
import Messages.VideoStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Bernhard
 * @author Lecton
 * @author Zenadia
 */
public class Colleague {
    public int ID =-1;
    public String name ="";
    public String localName ="";
    private AudioCapture ac;
    private StreamVideo sv;
//    public String content = "This is the label";
    
    private ArrayList<StringMessage> chatHistory;
    
    /**
     * Create a new Colleague and set its default panel content
     */
    public Colleague() {
        chatHistory =new ArrayList<StringMessage>();
    }
    
    public Colleague(int ID, String name, String localName) {
        chatHistory =new ArrayList<StringMessage>();
        this.ID =ID;
        this.name =name;
        this.localName =localName;
    }
    
    /**
     * Adds a message to the colleague's message history.
     * @param sm - the message to be added to the chat history.
     */
    public void addMessage(StringMessage sm) {
        chatHistory.add(sm);
    }
    
    /**
     * Retrieves the sent messages for a colleague.
     * @return 
     */
    public ArrayList<StringMessage> getMessages() {
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

    /**
     * Changes the colleague's name and updates it.
     * @param name - the name of the colleague.
     */
    public void setName(String name) {
        if (this.name.equalsIgnoreCase(this.localName)) {
            this.localName =name;
        }
        this.name =name;
    }

    /**
     * Changes the colleague's local name and updates it.
     * @param localName - the colleague's local name.
     */
    public void setlocalName(String localName) {
        this.localName =localName;
    }
    
    public void initializeStreams(Connection con, String name, int ID) {
        ac = new AudioCapture(con, new AudioStream(name, ID, this.ID));
        sv = new StreamVideo(new VideoStream(name, ID, this.ID), 1, new ScreenCapture(), con);
    }
    
    public void startAudioStream() {
        System.out.println("Starting audio");
        ac.start();
    }
    
    public void stopAudioStream() {
        System.out.println("Stopping audio");
        ac.stop();
    }
    
    public void startVideoStream() {
        System.out.println("Starting video");
        sv.start();
    }
    
    public void stopVideoStream() {
        System.out.println("Stopping video");
        sv.stop();
    }
}