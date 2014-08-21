package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Lecton
 * @author Zenadia
 */
public class NewUser extends Message {
    private String avatar = "";
    private String email = "";
    private String title = "";
    
    public NewUser(int userID, String username, String email, String avatar, String title) {
        this.userID = userID;
        this.Sender = username;
        this.email = email;
        this.avatar = avatar;
        this.targetID = Message.ALL;
        this.title = title;
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
    
    public String getUsername() {
        return this.Sender;
    }

    public String getTitle() {
        return this.title;
    }
    
    @Override
    public MessageType handle() {
        return MessageType.newUser;
    }
}
