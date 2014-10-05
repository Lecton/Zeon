package com.audioPlayer;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioPlayer implements Runnable {
    private final static Logger LOGGER = Logger.getLogger(AudioPlayer.class.getName());
    
    private int bufferSize =0;
    private byte[] buffer =new byte[bufferSize];
    
    private PipedInputStream pis =null;
    private PipedOutputStream pos =null;
    
    private AudioTrack line;
    
    private boolean running =false;
    private boolean dead =false;
    
    private String streamID;

    public AudioPlayer() {
        setupPipes();
        setupAudio();
    }
    
    private void setupAudio() {
    	bufferSize =16000;
    	line = new AudioTrack(AudioManager.STREAM_MUSIC, 16000, 
    			AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, 
                bufferSize /* 1 second buffer */,
                AudioTrack.MODE_STREAM);
    }
    
    private void setupPipes() {
        try {
            pos =new PipedOutputStream();
            pis =new PipedInputStream(pos);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Pipes could not be created");
            dead =true;
        }
    }
    
    public static AudioPlayer start(String streamID) {
        AudioPlayer player =new AudioPlayer();
        player.streamID =streamID;
        (new Thread(player)).start();
        return player;
    }
    
    public void stop() {
    	running =false;
    }
    
//    public void stop() {
//        running =false;
//    }
    
    public void write(String streamID, byte[] buffer) {
    	if (this.streamID != null && this.streamID.equals(streamID)) {
    		if (running) {
    			write(buffer);
    		}
    	}
    }
    
    private void write(byte[] buffer) {
        try {
            synchronized (pos) {
                pos.write(buffer);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Could not write to pipe buffer");
            dead =true;
        }
        
    }

    @Override
    public void run() {
        running =true;
        line.play();
        while (!dead && running) {
            try {
                buffer =new byte[bufferSize];
                int count = pis.read(buffer, 0, buffer.length);
//                System.out.println("AudioPlayer: read count: "+count);
                if (count > 0) {
                    line.write(buffer, 0, count);
                }
                
                if (count == -1) {
                	line.flush();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Could not read data from pipes");
                dead =true;
            }
            buffer =new byte[bufferSize];
        }
        line.stop();
    }

	public String getStreamID() {
		return streamID;
	}
}