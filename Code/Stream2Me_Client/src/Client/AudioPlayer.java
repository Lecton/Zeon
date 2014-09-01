package client;

import utils.Log;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import mediaStreaming.audio.AudioLine;

/**
 *
 * @author Bernhard
 */
public class AudioPlayer implements Runnable {
    private int bufferSize =0;
    private byte[] buffer =new byte[bufferSize];
    
    private AudioFormat format =null;
    
    private PipedInputStream pis =null;
    private PipedOutputStream pos =null;
    private SourceDataLine line =null;
    private DataLine.Info info =null;
    
    private boolean running =false;
    private boolean dead =false;

    public AudioPlayer() {
        format =AudioLine.createFormat();
        
        setupPipes();
        setupAudioLines();
    }
    
    private void setupPipes() {
        try {
            pos =new PipedOutputStream();
            pis =new PipedInputStream(pos);
        } catch (IOException ex) {
            Log.error(this, "Pipes could not be created");
            dead =true;
        }
    }
    
    private void setupAudioLines() {
        try {
            info =new DataLine.Info(SourceDataLine.class, format);
            
            bufferSize = (int) format.getSampleRate() 
                    * format.getFrameSize();
            buffer = new byte[bufferSize];
            
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
        } catch (LineUnavailableException ex) {
            Log.error(this, "Audio Player could not open the audio line.");
            dead =true;
        }
    }
    
    public static AudioPlayer start() {
        AudioPlayer player =new AudioPlayer();
        (new Thread(player)).start();
        return player;
    }
    
//    public void stop() {
//        running =false;
//    }
    
    public void write(byte[] buffer) {
        try {
            synchronized (pos) {
                pos.write(buffer);
            }
        } catch (IOException ex) {
            Log.error(this, "Could not write to pipe buffer");
            dead =true;
        }
    }

    @Override
    public void run() {
        running =true;
        while (!dead && running && line.isOpen()) {
            try {
                buffer =new byte[bufferSize];
                int count = pis.read(buffer, 0, buffer.length);
//                System.out.println("AudioPlayer: read count: "+count);
                if (count > 0) {
                    line.write(buffer, 0, count);
                }
                
                if (count == -1) {
                    line.drain();
                }
            } catch (IOException ex) {
                Log.error(this, "Could not read data from pipes");
                dead =true;
            }
            buffer =new byte[bufferSize];
        }
        line.close();
    }
}
