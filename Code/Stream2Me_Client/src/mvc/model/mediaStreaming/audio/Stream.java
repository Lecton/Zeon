package mvc.model.mediaStreaming.audio;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.media.AudioStreamMessage;
import mvc.controller.StreamControl;

/**
 *
 * @author Bernhard
 */
public class Stream implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(Stream.class.getName());
    
    private AudioStreamMessage as;
    private final AudioLine audio;
    
    private boolean running =false;

    public Stream(AudioStreamMessage as, AudioLine audio) {
        this.as = as;
        this.audio = audio;
    }
    
    public void stop() {
        LOGGER.log(Level.INFO, "Stream "+as.getStreamID()+" stopped");
        running =false;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Stream "+as.getStreamID()+" started");
        running =true;
        audio.open();
        
        while (running) {
            as = new AudioStreamMessage(as);
            as.buffer = new byte[audio.getBufferSize()];
            int count = audio.read(as.buffer, 0, as.buffer.length);
//                    System.out.println("AudioStream: write count: "+count);
            if (count > 0) {
                StreamControl.INSTANCE.write(as);
            }
        }
            
        audio.close();
    }
}