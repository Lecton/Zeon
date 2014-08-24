package mediaStreaming.audio;

import connection.Connection;
import utils.Log;


/**
 *
 * @author Bernhard
 */
public class AudioStreamer {
    private Stream transmitter;
    private Messages.Media.AudioStream as =null;
    private final AudioLine audio;
    private final Connection con;

    public AudioStreamer(Connection con) {
        this.audio = new AudioLine();
        this.con = con;
    }
    
    public void setStream(Messages.Media.AudioStream as) {
        if (transmitter != null && transmitter.isRunning()) {
            Log.error(this, "Trying to set stream on a non-null running audio stream");
        } else {
            this.as =as;
            this.transmitter = new Stream(con, as, audio);
        }
    }
    
    public String getStreamID() {
        return as.getStreamID();
    }
    
    public void clearStream() {
        if (transmitter != null && transmitter.isRunning()) {
            Log.error(this, "Trying to clear stream on a non-null running audio stream");
        } else {
            this.as =null;
            this.transmitter = null;
        }
    }
    
    public void start() {
        if (transmitter != null && !transmitter.isRunning()) {
            (new Thread(transmitter)).start();
        }
    }
    
    public void stop() {
        transmitter.stop();
        clearStream();
    }
}
