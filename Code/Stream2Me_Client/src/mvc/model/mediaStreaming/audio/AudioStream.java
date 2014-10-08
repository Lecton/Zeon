package mvc.model.mediaStreaming.audio;

import java.util.logging.Level;
import java.util.logging.Logger;
import messages.media.AudioStreamMessage;



/**
 *
 * @author Bernhard
 */
public class AudioStream {
    private final static Logger LOGGER = Logger.getLogger(AudioStream.class.getName());
    
    private Stream transmitter;
    private AudioStreamMessage as =null;
    private final AudioLine audio;

    public AudioStream(String userID) {
        as =new AudioStreamMessage(userID, null);
        this.audio = AudioLine.INSTANCE;
        transmitter =new Stream(as, audio);
    }
    
    public void setStream(String streamID) {
        if (transmitter != null && transmitter.isRunning()) {
            LOGGER.log(Level.SEVERE, "Trying to set stream on a non-null running audio stream");
        } else {
            as.setStreamID(streamID);
            this.transmitter = new Stream(as, audio);
        }
    }
    
    public String getStreamID() {
        return as.getStreamID();
    }
    
    public void clearStream() {
        if (transmitter != null && transmitter.isRunning()) {
            LOGGER.log(Level.SEVERE, "Trying to clear stream on a non-null running audio stream");
        } else {
            this.as.setStreamID(null);
            this.transmitter = null;
        }
    }
    
    public void start() {
        if (transmitter != null && !transmitter.isRunning()) {
            (new Thread(transmitter)).start();
        }
    }
    
    public void stop() {
        if (transmitter != null) {
            transmitter.stop();
            clearStream();
        }
    }
}
