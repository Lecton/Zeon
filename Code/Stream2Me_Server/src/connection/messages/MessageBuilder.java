package connection.messages;
/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class MessageBuilder {
    
    public static Messages.UserConnection.Greeting generateGreeting(server.database.Users person, boolean success) {
        Messages.UserConnection.Greeting g;
        
        g = new Messages.UserConnection.Greeting(success);
        g.setUserID(person.getUserID());
        g.setUsername(person.getUsername());
        g.setAvatar(person.getAvatar());
        g.setTargetID(person.getUserID());
        
        return g;
    }

    public static Messages.UserConnection.NewUser generateNewUser(server.database.Users userProfile) {
        return new Messages.UserConnection.NewUser(userProfile.getUserID(), 
                userProfile.getUsername(), userProfile.getUsername(), 
                userProfile.getAvatar(), userProfile.getTitle());
    }
}
