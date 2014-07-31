package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Lecton
 */
public class Logout extends Message {

    public Logout(int userID) {
        this.userID = userID;
        this.targetID =Message.SERVER;
    }
    
    public Logout(int userID, int targetID) {
        this.userID =userID;
        this.targetID =targetID;
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
