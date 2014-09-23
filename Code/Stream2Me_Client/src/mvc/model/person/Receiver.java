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
public class Receiver extends Notifier {
    //ID of streams receiving
    private String videoStream =null;
    private String audioStream =null;
    
    //Flags to show if has accepted streams
    private boolean acceptedVideo =false;
    private boolean acceptedAudio =false;
    
    //Flags to show if has been invited to streams
    private boolean receivingVideo =false;
    private boolean receivingAudio =false;

    public Receiver(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        super(userID, name, surname, email, avatar, title, aboutMe);
    }
    
    public String getVideoStream() {
        return videoStream;
    }

    public String getAudioStream() {
        return audioStream;
    }

    public void setVideoStream(String videoStream) {
        this.videoStream = videoStream;
    }

    public void setAudioStream(String audioStream) {
        this.audioStream = audioStream;
    }

    public boolean acceptedAudio() {
        return acceptedAudio;
    }

    public boolean acceptedVideo() {
        return acceptedVideo;
    }

    public void setAcceptedAudio(boolean acceptedAudio) {
        this.acceptedAudio = acceptedAudio;
    }

    public void setAcceptedVideo(boolean acceptedVideo) {
        this.acceptedVideo = acceptedVideo;
    }

    public boolean isReceivingAudio() {
        return receivingAudio;
    }

    public boolean isReceivingVideo() {
        return receivingVideo;
    }

    public boolean haveAcceptedVideo() {
        return acceptedVideo;
    }

    public boolean haveAcceptedAudio() {
        return acceptedAudio;
    }

    public void setReceivingAudio(boolean receivingAudio) {
        this.receivingAudio = receivingAudio;
    }

    public void setReceivingVideo(boolean receivingVideo) {
        this.receivingVideo = receivingVideo;
    }
}
