package MediaStreaming.Audio;

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
  
    /**
     * Constructor to initialize the audio stream capture object.
     * Streaming audio is identified as a set of data lines and the format is 
     * determined once the first line is retrieved.
     * @param oos - the object output stream.
     * @param _name - the name of this specific audio stream.
     * @param _ID - the ID of this specific audio stream.
     */
    public AudioCapture(Connection con, AudioStream as)
    {
        this.as = as;
        this.con = con;
        audio =new Stream(con, as, MessageUtils.getAudioLine());
    }

    public AudioStream getAudioStream() {
        return as;
    }

    public void setAudioStream(AudioStream as) {
        this.as = as;
    }
    
    public void start() {
        audioThread =new Thread(audio);
        audioThread.start();
    }

    public void stop(){
        audio.stop();
    }
}