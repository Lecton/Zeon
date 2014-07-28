package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class Logon extends Message {
    private String passwordHash ="";
    
    public Logon(String email, String passwordHash) {
        this.Sender = email;
        this.passwordHash =passwordHash;
    }
    
    @Override
    public String getMessage() {
        return "ClientInit: "+Sender+".";
    }

    @Override
    public MessageType handle() {
        return MessageType.logon;
    }
}
