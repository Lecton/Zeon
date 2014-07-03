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
    
    public Stream(ObjectOutputStream oos, ScreenCapture sc,
            long fps, String Sender, int ID) {
        this.oos =oos;
        this.screen =sc;
        previousMessage =new VideoStream(Sender,"Desktop","",-1);
        previousMessage.ID =ID;
        this.interval =fps/60 * 100;
    }

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
    
    private synchronized void sleep() {
        try {
            this.wait(interval);
        } catch (InterruptedException ex) {
        }
    }
    
    private void send(VideoStream m) {
        try {
            oos.writeObject(m);
            oos.flush();
        } catch (IOException ex) {
            
        }
    }
    
    
    
    static private enum state {PLAY, PAUSE, STOP};
    public state video =state.STOP;
    
    public synchronized void start() {
        video =state.PLAY;
    }
    
    public synchronized void stop() {
        video =state.STOP;
    }
    
    public synchronized void pause() {
        video =state.PAUSE;
    }
    
    public synchronized boolean isPlaying() {
        return (video == state.PLAY ? true : false);
    }
    
    public synchronized boolean isPaused() {
        return (video == state.PAUSE ? true : false);
    }
    
    public synchronized boolean isStopped() {
        return (video == state.STOP ? true : false);
    }
}
