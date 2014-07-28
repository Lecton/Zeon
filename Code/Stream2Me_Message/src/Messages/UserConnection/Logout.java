package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Lecton
 */
public class Logout extends Message {

    public Logout(int userID, String Sender) {
        this.userID = userID;
        this.Sender = Sender;
        this.Title = "User left";
    }
    
    @Override
    public String getMessage() {
        return userID + " left the chat.";
    }

    @Override
    public MessageType handle() {
        return MessageType.logout;
    }
}
