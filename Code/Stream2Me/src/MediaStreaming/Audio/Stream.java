/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MediaStreaming.Audio;

import Messages.AudioStream;
import Messages.MessageUtils;
import client.Connection;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Lecton
 * @author Zenadia
 * @author Bernhard
 */
public class Stream implements Runnable {
    private boolean running;
    private AudioStream as;
    private int bufferSize;
    private TargetDataLine line;
    private Connection con = null;
  
    /**
     * Constructor to initialize the audio stream capture object.
     * Streaming audio is identified as a set of data lines and the format is 
     * determined once the first line is retrieved.
     * @param oos - the object output stream.
     * @param _name - the name of this specific audio stream.
     * @param _ID - the ID of this specific audio stream.
     */
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
                System.out.println("Hello");
                this.as = new AudioStream(as);
                this.as.buffer = new byte[bufferSize];
                int count = line.read(as.buffer, 0, as.buffer.length);
                if (count > 0) {
                    System.out.println("Sending");
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
