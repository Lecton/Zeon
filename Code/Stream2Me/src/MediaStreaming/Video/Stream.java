/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MediaStreaming.Video;

import Messages.VideoStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author Bernhard
 */
class Stream implements Runnable {
    public ObjectOutputStream oos =null;
    public ScreenCapture screen =null;
    
    private BufferedImage img =null;
    private VideoStream previousMessage =null;
    private long interval =0;
    
    /**
     * Constructor that defines a stream when an object output stream, frames per
     * second, sender, ID, and screenshot is already specified.
     * @param oos -  the object output stream.
     * @param sc - the screen capture.
     * @param fps - the frames per second.
     * @param Sender - the source that is sending the stream.
     * @param ID - the ID of the stream object.
     */
    public Stream(ObjectOutputStream oos, ScreenCapture sc,
            long fps, String Sender, int ID) {
        this.oos = oos;
        this.screen = sc;
        previousMessage = new VideoStream(Sender,"Desktop","",-1);
        previousMessage.ID = ID;
        this.interval = fps/60 * 100;
    }

    /**
     * 
     */
    @Override
    public void run() {
        while (!isStopped()) {
            if (isPlaying()) {
                img =screen.getScreenImage();
                VideoStream m =new VideoStream(previousMessage);
                m.img =img;
                previousMessage =m;
            }
            send(previousMessage);
            sleep();
        }
    }
    
    /**
     * 
     */
    private synchronized void sleep() {
        try {
            this.wait(interval);
        } 
        catch (InterruptedException ex) {
        }
    }
    
    /**
     * 
     * @param m 
     */
    private void send(VideoStream m) {
        try {
            oos.writeObject(m);
            oos.flush();
        } catch (IOException ex) {
            
        }
    }
    
      
    static private enum state {PLAY, PAUSE, STOP};
    public state video =state.STOP;
    
    /**
     * Changes the video sequence's state to Playing.
     */
    public synchronized void start() {
        video =state.PLAY;
    }
    
    /**
     * Changes the video sequence's state to Stopped.
     */
    public synchronized void stop() {
        video =state.STOP;
    }
    
    /**
     * Changes the video sequence's state to Paused.
     */
    public synchronized void pause() {
        video = state.PAUSE;
    }
    
    /**
     * Checks if the video sequence's state is Playing.
     * @return 
     */
    public synchronized boolean isPlaying() {
        return (video == state.PLAY ? true : false);
    }
    
    /**
     * Checks if the video sequence's state is Paused.
     * @return 
     */
    public synchronized boolean isPaused() {
        return (video == state.PAUSE ? true : false);
    }
    
    /**
     * Checks if the video sequence's state is Stopped.
     * @return 
     */
    public synchronized boolean isStopped() {
        return (video == state.STOP ? true : false);
    }
}
