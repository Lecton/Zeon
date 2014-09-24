package mvc.model.mediaStreaming.video;

import java.util.logging.Level;
import java.util.logging.Logger;
import messages.media.VideoStreamMessage;


/**
 *
 * @author Bernhard
 */
public class VideoStream {
    private final static Logger LOGGER = Logger.getLogger(VideoStream.class.getName());
    
    private Stream transmitter;
    private VideoStreamMessage vs =null;
    private final ScreenCapture screen;

    public VideoStream(String userID) {
        this.screen = ScreenCapture.INSTANCE;
        vs =new VideoStreamMessage(userID, null);
    }
    
    public void setStream(String streamID) {
        if (transmitter != null && transmitter.isRunning()) {
            LOGGER.log(Level.SEVERE, "Trying to set stream on a non-null running video stream");
        } else {
            vs.setStreamID(streamID);
            this.transmitter = new Stream(vs, screen);
        }
    }
    
    public String getStreamID() {
        return vs.getStreamID();
    }
    
    public void clearStream() {
        if (transmitter != null && transmitter.isRunning()) {
            LOGGER.log(Level.SEVERE, "Trying to clear stream on a non-null running video stream");
        } else {
            this.vs.setStreamID(null);
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
