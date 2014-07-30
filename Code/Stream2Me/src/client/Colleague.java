package client;

import MediaStreaming.Audio.AudioCapture;
import MediaStreaming.Video.ScreenCapture;
import MediaStreaming.Video.StreamVideo;
import Messages.Factory;
import Messages.StringMessage;
import java.util.ArrayList;

public class Colleague {
    private int ID =-1;
    private String username ="";
    private AudioCapture ac;
    private StreamVideo sv;
    private String defaultURLName;
    private String customURLName;
    private final String defaultURLPath =".\\assets\\";
    private final String customURLPath =".\\assets\\profile\\";
    private boolean incomingAudio =false;
    private boolean incomingVideo =false;
    private String streamID ="";
    
    private ArrayList<StringMessage> chatHistory;
    
    public Colleague() {
        chatHistory =new ArrayList<StringMessage>();
        defaultURLName ="default_profile.png";
        customURLName ="default_profile.png";
    }
    
    public Colleague(int ID, String name) {
        chatHistory =new ArrayList<StringMessage>();
        this.ID =ID;
        this.username =name;
        defaultURLName ="default_profile.png";
        customURLName =ID + ".png";
    }
    
    public void addMessage(StringMessage sm) {
        chatHistory.add(sm);
    }
    
    public ArrayList<StringMessage> getMessages() {
        return chatHistory;
    }
    
    public int contained(ArrayList<Colleague> list) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i).ID == ID) {
                return i;
            }
        }
        return -1;
    }
    
    public boolean update(Colleague other) {
        if (other.ID == ID) {
            other.username = username;
            return true;
        } else {
            return false;
        }
    }
    
    public void setUsername(String name) {
        this.username =name;
    }

    public int getID() {
        return ID;
    }
    
    public String getUsername() {
        return username;
    }

    public String getDefaultURL() {
        return getDefaultURLPath()+defaultURLName;
    }

    public void setDefaultURLName(String defaultURL) {
        this.defaultURLName = defaultURL;
    }

    public String getCustomURL() {
        return getCustomURLPath()+customURLName;
    }

    public void setCustomURLName(String customURL) {
        this.customURLName = customURL;
    }
    
    public String getCustomURLPath() {
        return customURLPath;
    }
    
    public String getDefaultURLPath() {
        return defaultURLPath;
    }

    public String getDefaultURLName() {
        return defaultURLName;
    }

    public String getCustomURLName() {
        return customURLName;
    }
    
    public void initializeStreams(Connection con, String name, int ID) {
        int target =this.ID;
        if (this.ID == ID) {
            target = -1;
        }
        
        ac = new AudioCapture(con, Factory.getAudioStream(name, ID, -1));
        sv = new StreamVideo(Factory.getVideoStream(name, ID, target), 1, new ScreenCapture(), con);
    }
    
    public void startAudioStream(int[] allowed) {
        System.out.println("Starting audio");
        ac.start(allowed);
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

    public void setIncomingAudio(boolean incomingAudio, String streamID) {
        this.incomingAudio = incomingAudio;
        
        if (incomingAudio) {
            this.streamID =streamID;
        } else {
            this.streamID ="";
        }
    }

    public void setIncomingVideo(boolean incomingVideo) {
        this.incomingVideo = incomingVideo;
    }

    public boolean getIncomingAudio() {
        return incomingAudio;
    }

    public boolean getIncomingVideo() {
        return incomingVideo;
    }

    public String getStreamID() {
        return streamID;
    }
}