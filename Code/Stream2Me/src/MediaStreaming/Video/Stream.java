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
class Stream implements Runnable {
    public ObjectOutputStream oos =null;
    public ScreenCapture screen =null;
    
    private String img ="";
    private VideoStream previousMessage =null;
    private long interval =0;
    
    public Stream(ObjectOutputStream oos, ScreenCapture sc,
            long fps, VideoStream vs) {
        this.oos =oos;
        this.screen =sc;
        previousMessage =vs;
        this.interval =fps/1000;
    }

    @Override
    public void run() {
        while (!isStopped()) {
            if (isPlaying()) {
                img =encodeToString(screen.getScreenImage(), "png");
                previousMessage =new VideoStream(previousMessage);
                System.out.println(img);
                previousMessage.img =img;
            }
            send(previousMessage);
            sleep();
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
        }
    }
    
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
    
    public void start() {
        video =state.PLAY;
    }
    
    public void stop() {
        video =state.STOP;
    }
    
    public void pause() {
        video =state.PAUSE;
    }
    
    public boolean isPlaying() {
        return (video == state.PLAY);
    }
    
    public boolean isPaused() {
        return (video == state.PAUSE);
    }
    
    public boolean isStopped() {
        return (video == state.STOP);
    }
}
