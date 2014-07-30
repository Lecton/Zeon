package MediaStreaming.Audio;

import Messages.Factory;
import Messages.Media.AudioStream;
import Utils.MessageUtils;
import client.Connection;

public class AudioCapture {
    private AudioStream as;
    private Connection con = null;
    private Stream audio;
    private Thread audioThread;
    private String streamID;
    
    public AudioCapture(Connection con, AudioStream as)
    {
        this.as = as;
        this.con = con;
        this.streamID =MessageUtils.getID(as.getUserID());
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
        con.writeSafe(Factory.getStreamProperty(as.getUserID(), as.getTargetID(), streamID, 1, allowed));
        
        audioThread =new Thread(audio);
//        System.out.println("AS StreamID: "+as.getStreamID());
        audioThread.start();
        System.out.println("Audio Stream started");
    }

    public void stop(){
        con.writeSafe(Factory.getStreamProperty(as.getUserID(), as.getTargetID(), streamID, 0, null));
        audio.stop();
        System.out.println("Audio Stream stopped");
    }
}