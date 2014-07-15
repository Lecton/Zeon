/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MediaStreaming.Video;

import Messages.VideoStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Bernhard
 */
class Stream implements Runnable{
    public ObjectOutputStream oos = null;
    public ScreenCapture screen = null;
    
    private String img = "";
    private VideoStream previousMessage = null;
    private long interval = 0;
    
    /**
     * Constructor that defines a stream when an object output stream, frames per
     * second, sender, ID, and screenshot is already specified.
     * @param oos -  the object output stream.
     * @param sc - the screen capture.
     * @param fps - the frames per second.
     * @param vs - the VideoStream message object
     */
    public Stream(ObjectOutputStream oos, ScreenCapture sc,
                                    long fps, VideoStream vs) {
        this.oos =oos;
        this.screen =sc;
        previousMessage =vs;
        this.interval =fps/1000;
    }

    /**
     * 
     */
    @Override
    public void run() {
        while (!isStopped()) {
            if (isPlaying()) {
                img =encodeToString(screen.getScreenImage(), "png");
                previousMessage =new VideoStream(previousMessage);
                //System.out.println(img);
                previousMessage.img = img;
            }
            send(previousMessage);
            sleep();;
        }
    }
    
    private static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    private void sleep() {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * 
     * @param m 
     */
    private void send(VideoStream m) {
        try {
            oos.writeObject(m);
        } catch (IOException ex) {
            System.err.println("Error writing");
        }
        
        try {
            oos.flush();
        } catch (IOException ex) {
            System.err.println("Error flushing");
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
        return (video == state.PLAY);
    }
    
    /**
     * Checks if the video sequence's state is Paused.
     * @return 
     */
    public synchronized boolean isPaused() {
        return (video == state.PAUSE);
    }
    
    /**
     * Checks if the video sequence's state is Stopped.
     * @return 
     */
    public synchronized boolean isStopped() {
        return (video == state.STOP);
    }
}
