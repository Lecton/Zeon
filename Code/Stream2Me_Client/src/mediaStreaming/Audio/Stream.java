package mediaStreaming.Audio;

import Utils.Log;
import Utils.MessageFactory;
import java.io.IOException;

/**
 *
 * @author Bernhard
 */
public class Stream implements Runnable {
    private final Connection.Connection con;
    private Messages.Media.AudioStream as;
    private final mediaStreaming.Audio.AudioLine audio;
    
    private boolean running =false;

    public Stream(Connection.Connection con, Messages.Media.AudioStream as, mediaStreaming.Audio.AudioLine audio) {
        this.con = con;
        this.as = as;
        this.audio = audio;
    }
    
    public void stop() {
        Log.write(this, "Stream "+as.getStreamID()+" stopped");
        running =false;
        audio.getLine().close();
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void run() {
        Log.write(this, "Stream "+as.getStreamID()+" started");
        running =true;
        
            while (running) {
                try {
                    as = MessageFactory.clone(as);
                    as.buffer = new byte[audio.getBufferSize()];
                    int count = audio.read(as.buffer, 0, as.buffer.length);
                    System.out.println("AudioStream: write count: "+count);
                    if (count > 0) {
                        con.write(as);
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
    }
}