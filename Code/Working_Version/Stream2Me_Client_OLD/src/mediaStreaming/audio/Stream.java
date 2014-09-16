package mediaStreaming.audio;

import connection.Connection;
import java.io.IOException;
import messages.media.AudioStreamMessage;
import utils.Log;
import utils.MessageFactory;

/**
 *
 * @author Bernhard
 */
public class Stream implements Runnable {
    private final Connection con;
    private AudioStreamMessage as;
    private final AudioLine audio;
    
    private boolean running =false;

    public Stream(Connection con, AudioStreamMessage as, AudioLine audio) {
        this.con = con;
        this.as = as;
        this.audio = audio;
    }
    
    public void stop() {
        Log.write(this.getClass(), "Stream "+as.getStreamID()+" stopped");
        running =false;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        Log.write(this.getClass(), "Stream "+as.getStreamID()+" started");
        running =true;
        audio.open();
        
            while (running) {
                try {
                    as = MessageFactory.clone(as);
                    as.buffer = new byte[audio.getBufferSize()];
                    int count = audio.read(as.buffer, 0, as.buffer.length);
//                    System.out.println("AudioStream: write count: "+count);
                    if (count > 0) {
                        con.write(as);
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
            
        audio.close();
    }
}