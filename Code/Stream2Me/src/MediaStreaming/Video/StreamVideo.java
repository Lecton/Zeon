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
    private String Sender ="User";
    private int ID =-1;
    private long fps =5;
    
    private ScreenCapture sc;
    private Stream vStream;
    private Thread video;
    
    public StreamVideo(String _Sender, int _ID, long _fps, ScreenCapture sc, ObjectOutputStream oos) {
        this.Sender =_Sender;
        this.ID =_ID;
        this.fps =_fps;
        this.sc =sc;
        this.vStream =new Stream(oos, sc, fps, Sender, ID);
    }
    
    void start() {
        if (vStream.isStopped()) {
            video =new Thread(vStream);
            video.start();
        }
        vStream.start();
    }
    
    void pause() {
        vStream.pause();
    }
    
    void stop() {
        vStream.stop();
        try {
            video.join();
        } catch (InterruptedException ie) {
            
        }
    }
}
