/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DatabaseBuilder;


/**
 *
 * @author Bernhard
 */
public class MessageBuilder {
    
    public static Messages.UserConnection.Greeting generateGreeting(User.Profile person, boolean success) {
        Messages.UserConnection.Greeting g;
        
        g = new Messages.UserConnection.Greeting(success);
        g.setUserID(person.getUserID());
        g.setUsername(person.getUsername());
        g.setEmail(person.getEmail());
        g.setAvatar(person.getAvatar());
        g.setTargetID(person.getUserID());
        
        return g;
    }

    public static Messages.UserConnection.NewUser generateNewUser(User.Profile userProfile) {
        return new Messages.UserConnection.NewUser(userProfile.getUserID(), 
                userProfile.getUsername(), userProfile.getEmail(), 
                userProfile.getAvatar());
    }
}
