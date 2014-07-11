/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MediaStreaming.Video;

import java.io.ObjectOutputStream;

/**
 *
 * @author Bernhard
 */
public class StreamVideo {
    private String Sender = "User";
    private int ID = -1;
    private long fps = 5;
    
    private ScreenCapture sc;
    private Stream vStream;
    private Thread video;
    
    /**
     * Constructor to create a video stream object by which the captured video
     * is streamed to a recipient.
     * @param _Sender - the name of the sender of the current video stream.
     * @param _ID - the ID of the video stream.
     * @param _fps - the frames per second of the video being streamed
     * @param sc - the screen capture object.
     * @param oos - the object output stream.
     */
    public StreamVideo(String _Sender, int _ID, long _fps, ScreenCapture sc, ObjectOutputStream oos) {
        this.Sender =_Sender;
        this.ID =_ID;
        this.fps =_fps;
        this.sc =sc;
        this.vStream =new Stream(oos, sc, fps, Sender, ID);
    }
    
    /**
     * Starts the current video stream and changes its state to Started.
     */
    void start() {
        if (vStream.isStopped()) {
            video =new Thread(vStream);
            video.start();
        }
        vStream.start();
    }
    
    /**
     * Pauses the current video stream and changes its state to Paused.
     */
    void pause() {
        vStream.pause();
    }
    
    /**
     * Stops the current video stream and changes its state to Stopped.
     */
    void stop() {
        vStream.stop();
        try {
            video.join();
        } catch (InterruptedException ie) {
            
        }
    }
}
