package messages.userConnection;

import messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class LoginMessage extends Message {
    private String passwordHash ="";
    
    public LoginMessage(String email, String passwordHash) {
        this.Sender = email;
        this.passwordHash =passwordHash;
        this.targetID =Message.SERVER;
    }
    
    @Override
    public String getMessage() {
        return "ClientInit: "+Sender+".";
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public MessageType handle() {
        return MessageType.login;
    }
}
