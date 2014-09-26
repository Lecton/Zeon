package mediaStreaming.video;

import connection.Connection;
import messageUtils.ImageUtils;
import messages.media.VideoStreamMessage;
import utils.Log;
import utils.MessageFactory;

/**
 *
 * @author Bernhard
 */
public class Stream implements Runnable {
    private final Connection con;
    private VideoStreamMessage vs;
    private final ScreenCapture screen;
    
    private boolean running =false;

    public Stream(Connection con, VideoStreamMessage vs, ScreenCapture screen) {
        this.con = con;
        this.vs = vs;
        this.screen = screen;
    }
    
    public void stop() {
        Log.write(this.getClass(), "Stream "+vs.getStreamID()+" stopped");
        running =false;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        Log.write(this.getClass(), "Stream "+vs.getStreamID()+" started");
        running =true;
        while (running) {
            try {
                String img =ImageUtils.encodeToString(screen.getScreenImage(), "png");
                vs =MessageFactory.clone(vs);
                vs.setImg(img);
                con.writeSafe(vs);
//                try {
//                    Thread.sleep(1500);
//                } catch (InterruptedException e) {}
            } catch (NullPointerException npe) {
            }
        }
    }
}