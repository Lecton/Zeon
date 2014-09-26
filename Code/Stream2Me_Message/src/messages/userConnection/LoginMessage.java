package messages.userConnection;

import messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class LoginMessage extends Message {
    private String passwordHash;
    private String username;
    
    public LoginMessage(String username, String passwordHash) {
        this.username =username;
        this.passwordHash =passwordHash;
        this.targetID =Message.SERVER;
    }
    
    @Override
    public String getMessage() {
        return "ClientInit: "+username+".";
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public MessageType handle() {
        return MessageType.login;
    }
}
