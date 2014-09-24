/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.media.creation.StreamPropertyMessage;
import messages.media.creation.StreamTerminateMessage;
import mvc.controller.videoPlayer.VideoManager;
import mvc.model.mediaStreaming.audio.AudioStream;
import mvc.model.mediaStreaming.video.VideoStream;

/**
 *
 * @author Bernhard
 */
public class StreamControl {
    private final static Logger LOGGER = Logger.getLogger(StreamControl.class.getName());
    
    public static StreamControl INSTANCE =new StreamControl();
    
    private static AudioStream audioModel;
    private static VideoStream videoModel;
    
    private static AudioPlayer audioView =AudioPlayer.start();
    private static VideoManager videoView =new VideoManager();
    
    protected static void register(String userID) {
        if (audioModel != null) {
            audioModel.stop();
        }
        audioModel =new AudioStream(userID);
        
        if (videoModel != null) {
            videoModel.stop();
        }
        videoModel =new VideoStream(userID);
    }
    
    /**
     * Stop the video stream
     */
    protected static void stopVideo() {
        UserControl.INSTANCE.getUser().setVideoStreamID(null);
        UserControl.INSTANCE.getUser().setStreamingVideo(false);
        GUIControl.toggleStreamVideo(false);
        videoModel.stop();
        videoModel.clearStream();
    }

    /**
     * Start the video stream and return the streamID generated
     * @param msg
     */
    protected static void startVideo(StreamPropertyMessage msg) {
        String name =msg.getStreamName();
        if (name.equals(UserControl.INSTANCE.getUser().getVideoStreamName())) {
            if (msg.isSuccessful()) {
                UserControl.INSTANCE.getUser().setVideoStreamID(msg.getStreamID());
                UserControl.INSTANCE.getUser().setStreamingVideo(true);
                LOGGER.log(Level.INFO, "Video stream allowed");
                videoModel.setStream(msg.getStreamID());
                videoModel.start();
            } else {
                LOGGER.log(Level.WARNING, "Video stream not allowed");
            }
        } else {
            if (msg.isSuccessful()) {
                UserControl.INSTANCE.getUser().setVideoStreamID(msg.getStreamID());
                UserControl.INSTANCE.getUser().setStreamingVideo(true);
            }
            LOGGER.log(Level.WARNING, "Video stream does not match response");
        }
        GUIControl.toggleStreamVideo(true);
    }

    /**
     * Stop the audio stream
     */
    protected static void stopAudio() {
        UserControl.INSTANCE.getUser().setAudioStreamID(null);
        UserControl.INSTANCE.getUser().setStreamingAudio(false);
        GUIControl.toggleStreamAudio(false);
        audioModel.stop();
        audioModel.clearStream();
    }

    /**
     * Start the audio stream and return the streamID generated
     * @param msg
     */
    protected static void startAudio(StreamPropertyMessage msg) {
        String name =msg.getStreamName();
        System.out.println(name);
        if (name.equals(UserControl.INSTANCE.getUser().getAudioStreamName())) {
            if (msg.isSuccessful()) {
                UserControl.INSTANCE.getUser().setAudioStreamID(msg.getStreamID());
                UserControl.INSTANCE.getUser().setStreamingAudio(true);
                LOGGER.log(Level.INFO, "Audio stream allowed");
                audioModel.setStream(msg.getStreamID());
                audioModel.start();
            } else {
                LOGGER.log(Level.WARNING, "Audio stream not allowed");
            }
        } else {
            if (msg.isSuccessful()) {
                UserControl.INSTANCE.getUser().setAudioStreamID(msg.getStreamID());
                UserControl.INSTANCE.getUser().setStreamingAudio(true);
            }
            LOGGER.log(Level.WARNING, "Audio stream does not match response");
        }
        GUIControl.toggleStreamAudio(true);
    }

    public static void handleStreamProperty(StreamPropertyMessage msg) {
        if (msg.getType() == 0) {
            startVideo(msg);
        } else if (msg.getType() == 1) {
            startAudio(msg);
        } else {
            LOGGER.log(Level.SEVERE, "Stream response type unknown.");
        }
    }

    public static void handleStreamTerminate(StreamTerminateMessage msg) {
        if (msg.getType() == 0) {
            stopVideo();
        } else if (msg.getType() == 1) {
            stopAudio();
        } else {
            LOGGER.log(Level.SEVERE, "Stream response type unknown.");
        }
    }

    public static void handleAudioData(byte[] buffer) {
        audioView.write(buffer);
    }

    public static void handleVideoData(String userID, String imageBuffer) {
        videoView.write(userID, imageBuffer);
    }
    
    public void write(Message msg) {
        Control.INSTANCE.writeMessage(msg);
    }
}
