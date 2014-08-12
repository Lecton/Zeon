/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MediaStreaming.Video;

import Utils.MessageUtils;
import Messages.UpdateUser.UpdateUsername;
import Messages.Media.VideoStream;
import client.Connection;

/**
 *
 * @author Bernhard
 */
public class StreamVideo {
    private VideoStream vs;
    private long fps =5;
    
    private Connection con;
    private ScreenCapture sc;
    private Thread video;
    private Stream vStream;
    
    /**
     * Constructor to create a video stream object by which the captured video
     * is streamed to a recipient.
     * @param vs - the VideoStream message object for the current sender
     * @param _fps - the frames per second of the video being streamed
     * @param sc - the screen capture object.
     * @param oos - the object output stream.
     */
    public StreamVideo(VideoStream vs, long _fps, ScreenCapture sc, Connection con) {
        this.vs =vs;
        this.fps =_fps;
        this.sc =sc;
        this.con =con;
        vStream =new Stream(con, sc, fps, vs);
    }
    
    /**
     * Starts the current video stream and changes its state to Started.
     */
    public void start() {
//        con.writeSafe(new UpdateUser(vs.getID(), vs.getTo(), "Video Stream", MessageUtils.Update.STARTVIDEO));
        if (vStream.isStopped()) {
            video =new Thread(vStream);
            video.start();
        }
        vStream.start();
    }
    
    /**
     * Pauses the current video stream and changes its state to Paused.
     */
    public void pause() {
        vStream.pause();
    }
    
    /**
     * Stops the current video stream and changes its state to Stopped.
     */
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
