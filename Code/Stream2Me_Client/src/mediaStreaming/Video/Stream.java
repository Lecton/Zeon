package mediaStreaming.Video;

import Utils.ImageUtils;
import Utils.Log;
import Utils.MessageFactory;

/**
 *
 * @author Bernhard
 */
public class Stream implements Runnable {
    private final Connection.Connection con;
    private Messages.Media.VideoStream vs;
    private final mediaStreaming.Video.ScreenCapture screen;
    
    private boolean running =false;

    public Stream(Connection.Connection con, Messages.Media.VideoStream vs, mediaStreaming.Video.ScreenCapture screen) {
        this.con = con;
        this.vs = vs;
        this.screen = screen;
    }
    
    public void stop() {
        Log.write(this, "Stream "+vs.getStreamID()+" stopped");
        running =false;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        Log.write(this, "Stream "+vs.getStreamID()+" started");
        running =true;
        while (running) {
            try {
                String img =ImageUtils.encodeToString(screen.getScreenImage(), "png");
                vs =MessageFactory.clone(vs);
                vs.setImg(img);
                con.writeSafe(vs);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {}
            } catch (NullPointerException npe) {
            }
        }
    }
}