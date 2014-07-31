package MediaStreaming.Audio;

import Messages.Factory;
import Messages.Media.AudioStream;
import client.Connection;
import java.io.IOException;
import javax.sound.sampled.TargetDataLine;

public class Stream implements Runnable {
    private boolean running;
    private AudioStream as;
    private int bufferSize;
    private TargetDataLine line;
    private Connection con = null;
    
    public Stream(Connection con, AudioStream as, AudioLine al)
    {
        this.as = as;
        this.con = con;
        this.line =al.getLine();
        bufferSize =al.getBufferSize();
    }
    
    @Override
    public void run() {
        running = true;
        try {
            while (running) {
                this.as = Factory.cloneAudioStream(as);
                this.as.buffer = new byte[bufferSize];
                int count = line.read(as.buffer, 0, as.buffer.length);
                if (count > 0) {
//                    System.out.println("STREAM - StreamID: "+as.getStreamID());
                    con.write(as);
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void stop(){
        running = false;
    }
}