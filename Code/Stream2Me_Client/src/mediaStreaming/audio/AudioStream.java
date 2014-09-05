package mediaStreaming.audio;

import connection.Connection;
import messages.media.AudioStreamMessage;
import utils.Log;



/**
 *
 * @author Bernhard
 */
public class AudioStream {
    private Stream transmitter;
    private AudioStreamMessage as =null;
    private final AudioLine audio;
    private final Connection con;

    public AudioStream(AudioLine audio, Connection con) {
        this.audio = audio;
        this.con = con;
    }
    
    public void setStream(AudioStreamMessage as) {
        if (transmitter != null && transmitter.isRunning()) {
            Log.error(this.getClass(), "Trying to set stream on a non-null running audio stream");
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
            Log.error(this.getClass(), "Trying to clear stream on a non-null running audio stream");
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
