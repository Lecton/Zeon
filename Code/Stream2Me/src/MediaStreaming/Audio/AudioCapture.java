package MediaStreaming.Audio;

import Messages.AudioProperty;
import Messages.AudioStream;
import Messages.MessageUtils;
import client.Connection;

/**
 * 
 * @author Bernhard
 */
public class AudioCapture {
    private AudioStream as;
    private Connection con = null;
    private Stream audio;
    private Thread audioThread;
    private String streamID;
  
    /**
     * Constructor to initialize the audio stream capture object.
     * @param con
     * @param as 
     */
    public AudioCapture(Connection con, AudioStream as)
    {
        this.as = as;
        this.con = con;
        this.streamID =MessageUtils.getID(as.getID());
//        System.out.println("StreamID: "+as.getID());
        as.setStreamID(streamID);
        audio =new Stream(con, as, MessageUtils.getAudioLine());
    }

    public AudioStream getAudioStream() {
        return as;
    }

    public void setAudioStream(AudioStream as) {
        this.as = as;
    }
    
    public void start(int[] allowed) {
        AudioProperty ap =new AudioProperty(as.getID(), as.getTo(), streamID, 1);
        ap.setAllowed(allowed);
        con.writeSafe(ap);
        audioThread =new Thread(audio);
//        System.out.println("AS StreamID: "+as.getStreamID());
        audioThread.start();
        System.out.println("Audio Stream started");
    }

    public void stop(){
        con.writeSafe(new AudioProperty(as.getID(), as.getTo(), streamID, 0));
        audio.stop();
        System.out.println("Audio Stream stopped");
    }
}