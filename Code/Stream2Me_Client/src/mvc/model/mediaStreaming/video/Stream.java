package mvc.model.mediaStreaming.video;

import java.util.logging.Level;
import java.util.logging.Logger;
import messages.media.VideoStreamMessage;
import mvc.controller.generalUI.StreamControl;
import utils.ImageUtils;

/**
 *
 * @author Bernhard
 */
public class Stream implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(Stream.class.getName());
    
    private VideoStreamMessage vs;
    private final ScreenCapture screen;
    
    private boolean running =false;

    public Stream(VideoStreamMessage vs, ScreenCapture screen) {
        this.vs = vs;
        this.screen = screen;
    }
    
    public void stop() {
        LOGGER.log(Level.INFO, "Stream "+vs.getStreamID()+" stopped");
        running =false;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Stream "+vs.getStreamID()+" started");
        running =true;
        while (running) {
            try {
                String img =ImageUtils.encodeToString(screen.getScreenImage(), "jpg");
                vs =new VideoStreamMessage(vs);
                vs.setImg(img);
                StreamControl.INSTANCE.write(vs);
//                try {
//                    Thread.sleep(1500);
//                } catch (InterruptedException e) {}
            } catch (NullPointerException npe) {
            }
        }
    }
}