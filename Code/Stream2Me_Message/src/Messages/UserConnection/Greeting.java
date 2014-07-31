package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class Greeting extends Message {
    private boolean success;
    private String username;
    private String email;
    private String avatar;
    
    public Greeting(boolean success) {
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

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
