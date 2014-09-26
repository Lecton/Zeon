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
public class Notifier extends Person {
    private boolean video =false;
    private boolean audio =false;
    private boolean message =false;

    public Notifier(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        super(userID, name, surname, email, avatar, title, aboutMe);
    }

    public boolean hasVideo() {
        return video;
    }
    
    public boolean hasAudio() {
        return audio;
    }

    public boolean hasMessage() {
        return message;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }
}
