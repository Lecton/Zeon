package MediaStreaming.Video;

import Messages.Factory;
import Messages.Media.VideoStream;
import Utils.*;
import client.Connection;
import java.io.IOException;

class Stream implements Runnable{
    private Connection con = null;
    private ScreenCapture screen = null;
    
    private String img = "";
    private VideoStream previousMessage = null;
    private long interval = 0;
    
    public Stream(Connection con, ScreenCapture sc,
                                    long fps, VideoStream vs) {
        this.con =con;
        this.screen =sc;
        previousMessage =vs;
        this.interval =fps/1000;
    }
    
    @Override
    public void run() {
        while (!isStopped()) {
            if (isPlaying()) {
                img =ImageUtils.encodeToString(screen.getScreenImage(), "png");
                previousMessage =Factory.cloneVideoStream(previousMessage);
                //System.out.println(img);
                previousMessage.setImg(img);
            }
            send(previousMessage);
            sleep();;
        }
    }
    
    private void sleep() {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    private void send(VideoStream m) {
        try {
            con.write(m);
        } catch (IOException ex) {
            System.err.println("Error sending");
            this.stop();
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
        video = state.PAUSE;
    }
    
    public synchronized boolean isPlaying() {
        return (video == state.PLAY);
    }
    
    public synchronized boolean isPaused() {
        return (video == state.PAUSE);
    }
    
    public synchronized boolean isStopped() {
        return (video == state.STOP);
    }
}
