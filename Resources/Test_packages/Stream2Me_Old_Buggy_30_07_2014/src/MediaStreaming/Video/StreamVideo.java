package MediaStreaming.Video;

import Messages.Media.VideoStream;
import client.Connection;

public class StreamVideo {
    private VideoStream vs;
    private long fps =5;
    
    private Connection con;
    private ScreenCapture sc;
    private Thread video;
    private Stream vStream;
    
    public StreamVideo(VideoStream vs, long _fps, ScreenCapture sc, Connection con) {
        this.vs =vs;
        this.fps =_fps;
        this.sc =sc;
        this.con =con;
        vStream =new Stream(con, sc, fps, vs);
    }
    
    public void start() {
//        con.writeSafe(new UpdateUser(vs.getID(), vs.getTo(), "Video Stream", MessageUtils.Update.STARTVIDEO));
        if (vStream.isStopped()) {
            video =new Thread(vStream);
            video.start();
        }
        vStream.start();
    }
    
    public void pause() {
        vStream.pause();
    }
    
    public void stop() {
//        con.writeSafe(new UpdateUser(vs.getID(), vs.getTo(), "Video Stream", MessageUtils.Update.STOPVIDEO));
        vStream.stop();
        try {
            video.join();
        } catch (InterruptedException ex) {
            System.out.println("Could not wait");
        }
    }
}
