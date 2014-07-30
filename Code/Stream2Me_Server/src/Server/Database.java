package Server;

import Messages.UserConnection.NewUser;

/**
 *
 * @author Bernhard
 */
public class Database {
    int userID=0;
    
    public int login(String username, String passwordHash) {
        return userID++;
    }
    
    public void logoff(int ID) {
    }
    
    public NewUser getNewUser(int ID) {
        String username ="a";
        int userID =ID;
        String email ="a@a.a";
        String avatar ="";
        
        return new NewUser(userID, username, email, avatar);
    }
}
