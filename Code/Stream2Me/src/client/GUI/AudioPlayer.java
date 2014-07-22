/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.GUI;

import Messages.MessageUtils;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Bernhard
 */
public class AudioPlayer implements Runnable {
    private enum STATE {PLAYING, RECORDING, STOP, AVAILABLE, DEAD}
    private STATE media =STATE.STOP;
    private AudioFormat format =null;
    private int bufferSize =0;
    private byte buffer[] =null;
    
    private AudioInputStream ais;
    private PipedInputStream pis;
    private PipedOutputStream pos;
    private SourceDataLine line =null;
    private DataLine.Info info;
    
    public AudioPlayer() {
        try {
            
            format =MessageUtils.getFormat();
            info = new DataLine.Info(SourceDataLine.class, format);
            
            bufferSize = (int) format.getSampleRate() 
                    * format.getFrameSize();
            buffer = new byte[bufferSize];
            
            pos =new PipedOutputStream();
            pis =new PipedInputStream(pos);
            createLine();
        } catch (IOException ioe) {
            System.err.println("Could not create AudioPlayer pipes. AudioPlayer instance set to DEAD");
            media =STATE.DEAD;
        }
    }
    
    private void setAudioInputStream() {
        try {
            ais =new AudioInputStream(pis, format, pis.available()/format.getFrameSize());
        } catch (IOException e) {
            System.out.println("Error setting audio input stream.");
        }
    }
    
    public void createLine() {
        try {
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            setAudioInputStream();
        } catch (LineUnavailableException lue) {
            
        }
        
        
    }
    
    /**
     * Writes b.length bytes from the specified byte array to this
     * output stream. The general contract for write(b) is that it 
     * should have exactly the same effect as a write(b, 0, b.length) call.
     * @param data - the data.
     * @throws IOException - if an I/O error occurs.
     */
    public void write(byte[] data) throws IOException {
        pos.write(data);
    }
    
    public void play() {
        if (media != STATE.DEAD) {
            media =STATE.PLAYING;
        }
    }
    
    public void record() {
        if (media != STATE.DEAD) {
            media =STATE.RECORDING;
        }
    }
    
    public void stop() {
        if (media != STATE.DEAD) {
            media =STATE.STOP;
        }
    }
    
    public boolean isActive() {
        return media == STATE.PLAYING || media == STATE.RECORDING;
    }
    
    public boolean isDead() {
        return media == STATE.DEAD;
    }
    
    @Override
    public void run() {
        while (isActive() && line.isOpen() && !isDead()) {
            try {
                buffer =new byte[bufferSize];
                int count = pis.read(buffer, 0, buffer.length);
                if (count > 0) {
                    line.write(buffer, 0, count);
                }
                if (count == -1) {
                    line.drain();
                    setAudioInputStream();
                }
            } catch (IOException ex) {
                System.err.println("I/O problems: " + ex);
                System.exit(-3);
            }
            buffer =new byte[bufferSize];
        }
        line.close();
    }
}