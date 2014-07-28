package Messages.UserConnection;

import Messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class Greeting extends Message {
    
    public Greeting(int userID) {
        this.userID = userID;
        this.Title = "Greeting";
    }
    
    @Override
    public String getMessage() {
        return "Greetings "+"User ID "+this.userID+".";
    }

    @Override
    public MessageType handle() {
        return MessageType.greeting;
    }
}
