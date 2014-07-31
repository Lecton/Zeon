package mediaStreaming.Video;

import Utils.Log;


/**
 *
 * @author Bernhard
 */
public class VideoStream {
    private Stream transmitter;
    private Messages.Media.VideoStream vs =null;
    private final mediaStreaming.Video.ScreenCapture screen;
    private final Connection.Connection con;

    public VideoStream(ScreenCapture screen, Connection.Connection con) {
        this.screen = screen;
        this.con = con;
    }
    
    public void setStream(Messages.Media.VideoStream vs) {
        if (transmitter != null && transmitter.isRunning()) {
            Log.error(this, "Trying to set stream on a non-null running video stream");
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
            Log.error(this, "Trying to clear stream on a non-null running video stream");
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
