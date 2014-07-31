package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class Login extends Message {
    private String passwordHash ="";
    
    public Login(String email, String passwordHash) {
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
