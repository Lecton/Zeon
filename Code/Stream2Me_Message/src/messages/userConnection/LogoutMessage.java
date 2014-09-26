package messages.userConnection;

import messages.Message;

/**
 *
 * @author Lecton
 */
public class LogoutMessage extends Message {

    public LogoutMessage(String userID) {
        this.userID =userID;
        this.targetID =Message.SERVER;
    }

    public LogoutMessage(String userID, String targetID) {
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
