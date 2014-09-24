/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model.person;

/**
 *
 * @author Bernhard
 */
public class Streamer extends Notifier {
    private String videoStreamID =null;
    private String audioStreamID =null;
    
    private String videoStreamName =null;
    private String audioStreamName =null;
    
    private boolean streamingVideo;
    private boolean streamingAudio;

    public Streamer(String userID, String name, String surname, 
            String email, String avatar, String title, String aboutMe) {
        super(userID, name, surname, email, avatar, title, aboutMe);
    }

    public String getAudioStreamID() {
        return audioStreamID;
    }

    public String getVideoStreamID() {
        return videoStreamID;
    }

    public void setAudioStreamID(String audioStreamID) {
        this.audioStreamID = audioStreamID;
    }

    public void setVideoStreamID(String videoStreamID) {
        this.videoStreamID = videoStreamID;
    }

    public boolean isStreamingAudio() {
        return streamingAudio;
    }

    public boolean isStreamingVideo() {
        return streamingVideo;
    }

    public void setStreamingAudio(boolean streamingAudio) {
        this.streamingAudio = streamingAudio;
    }

    public void setStreamingVideo(boolean streamingVideo) {
        this.streamingVideo = streamingVideo;
    }

    public void setVideoStreamName(String videoStreamName) {
        this.videoStreamName = videoStreamName;
    }

    public void setAudioStreamName(String audioStreamName) {
        this.audioStreamName = audioStreamName;
    }

    public String getVideoStreamName() {
        return videoStreamName;
    }

    public String getAudioStreamName() {
        return audioStreamName;
    }
}
