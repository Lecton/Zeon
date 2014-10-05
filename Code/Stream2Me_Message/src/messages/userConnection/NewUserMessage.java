package messages.userConnection;

import messages.Message;
import messages.MessageType;

/**
 *
 * @author Bernhard
 * @author Lecton
 * @author Zenadia
 */
public class NewUserMessage extends Message {
    private String name;
    private String surname;
    private String email;
    private String avatar;
    private String title;
    private String aboutMe;

    public NewUserMessage(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        this.userID = userID;
        this.name =name;
        this.surname =surname;
        this.email = email;
        this.avatar = avatar;
        this.title =title;
        this.aboutMe =aboutMe;
        this.targetID = Message.ALL;
    }
    
    /**
     * A function to retrieve the message and return the message being sent as
     * a string.
     * @return 
     */
    @Override
    public String getMessage() {
        return "New User " + ":" + name+" "+surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getTitle() {
        return title;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    @Override
    public MessageType handle() {
        return MessageType.newUser;
    }
}
