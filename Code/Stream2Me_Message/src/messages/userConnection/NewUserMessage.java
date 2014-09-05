package messages.userConnection;

import messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 * @author Zenadia
 */
public class NewUserMessage extends Message {
    private String avatar = "";
    private String email = "";
    private String name ="";
    private String surname ="";
    
    public NewUserMessage(int userID, String name, String surname, String email, String avatar) {
        this.userID = userID;
        this.Sender = email;
        this.name =name;
        this.surname =surname;
        this.email = email;
        this.avatar = avatar;
        this.targetID = Message.ALL;
    }
    
    /**
     * A function to retrieve the message and return the message being sent as
     * a string.
     * @return 
     */
    @Override
    public String getMessage() {
        return "New User " + ":" + Sender;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public MessageType handle() {
        return MessageType.newUser;
    }
}
