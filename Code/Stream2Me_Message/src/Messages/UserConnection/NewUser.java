package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Lecton
 */
public class NewUser extends Message {
    private String avatar ="";
    private String email ="";
    
    public NewUser(int userID, String username, String email, String avatar) {
        this.userID = userID;
        this.Sender = username;
        this.email = email;
        this.avatar = avatar;
        this.Title = "New User";
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
        return avatar;
    }

    public String getEmail() {
        return email;
    }
    
    public String getUsername() {
        return Sender;
    }

    @Override
    public MessageType handle() {
        return MessageType.newUser;
    }
}
