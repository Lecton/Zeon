package messages.userConnection;

import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class GreetingMessage extends Message {
    private boolean success;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String avatar;
    private String title;
    private String aboutMe;
    
    private String response;
    
    public GreetingMessage(boolean success) {
        this.success =success;
    }
    
    @Override
    public String getMessage() {
        return "Greetings "+success+".";
    }

    @Override
    public MessageType handle() {
        return MessageType.greeting;
    }

    public boolean isSuccessful() {
        return success;
    }

    public String getUsername() {
        return username;
    }
    
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getTitle() {
        return title;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
