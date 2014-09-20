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
public class Streamer extends MessageUser {
    private String videoName =null;
    private String audioName =null;
    
    private boolean streamingVideo;
    private boolean streamingAudio;

    public Streamer(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        super(userID, name, surname, email, avatar, title, aboutMe);
    }

    public String getAudioName() {
        return audioName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
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
}
