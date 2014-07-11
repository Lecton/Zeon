/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MediaStreaming.Video;

import Messages.VideoStream;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class StreamVideo {
    private int ID =-1;
    private VideoStream vs;
    private long fps =5;
    
    private ObjectOutputStream oos;
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
    public StreamVideo(VideoStream vs, long _fps, ScreenCapture sc, ObjectOutputStream oos) {
        this.ID =vs.ID;
        this.vs =vs;
        this.fps =_fps;
        this.sc =sc;
        this.oos =oos;
        vStream =new Stream(oos, sc, fps, vs);
    }
    
    /**
     * Starts the current video stream and changes its state to Started.
     */
    public void start() {
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
        vStream.stop();
        try {
            video.join();
        } catch (InterruptedException ex) {
            System.out.println("Could not wait");
        }
    }
    
//    private class Stream implements Runnable {
//        
//        private BufferedImage img =null;
//        private VideoStream previousMessage =null;
//        private long interval =0;
//
//        @Override
//        public void run() {
//            previousMessage =vs;
//            while (!isStopped()) {
//                if (isPlaying()) {
//                    previousMessage =new VideoStream(previousMessage);
//                    previousMessage.img =
//                    sc.getScreenImage();
//                }
//                
//                send(previousMessage);
//                sleep();
//            }
//        }
//        
//        private void sleep() {
//            try {
//                Thread.sleep(fps/1000);
//            } catch (InterruptedException ex) {
//                System.err.println("Coffee Overload");
//            }
//        }
//        
//        private void send(VideoStream vs) {
//            try {
//                StreamVideo.this.oos.writeObject(vs);
//                StreamVideo.this.oos.flush();
//            } catch (IOException ex) {
//                System.err.println("Could not write");
//            }
//        }
//        
//        public state video =state.STOP;
//
//        public void start() {
//            video =state.PLAY;
//        }
//
//        public void stop() {
//            video =state.STOP;
//        }
//
//        public void pause() {
//            video =state.PAUSE;
//        }
//
//        public boolean isPlaying() {
//            return (video == state.PLAY);
//        }
//
//        public boolean isPaused() {
//            return (video == state.PAUSE);
//        }
//
//        public boolean isStopped() {
//            return (video == state.STOP);
//        }
//    }
}
