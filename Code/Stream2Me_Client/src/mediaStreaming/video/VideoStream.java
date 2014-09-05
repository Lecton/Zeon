package mediaStreaming.video;

import connection.Connection;
import messages.media.VideoStreamMessage;
import utils.Log;


/**
 *
 * @author Bernhard
 */
public class VideoStream {
    private Stream transmitter;
    private VideoStreamMessage vs =null;
    private final ScreenCapture screen;
    private final Connection con;

    public VideoStream(ScreenCapture screen, Connection con) {
        this.screen = screen;
        this.con = con;
    }
    
    public void setStream(VideoStreamMessage vs) {
        if (transmitter != null && transmitter.isRunning()) {
            Log.error(this.getClass(), "Trying to set stream on a non-null running video stream");
        } else {
            this.vs =vs;
            this.transmitter = new Stream(con, vs, screen);
        }
    }
    
    public String getStreamID() {
        return vs.getStreamID();
    }
    
    public void clearStream() {
        if (transmitter != null && transmitter.isRunning()) {
            Log.error(this.getClass(), "Trying to clear stream on a non-null running video stream");
        } else {
            this.vs =null;
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
