package messages.userConnection;

import messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class GreetingMessage extends Message {
    private boolean success;
    private String name;
    private String surname;
    private String email;
    private String avatar;
    
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
}
