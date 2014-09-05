/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import messages.userConnection.GreetingMessage;
import messages.userConnection.NewUserMessage;
import user.Profile;


/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class MessageBuilder {
    
    public static GreetingMessage generateGreeting(Profile person, boolean success) {
        GreetingMessage g;
        
        g = new GreetingMessage(success);
        if (success) {
            g.setUserID(person.getUserID());
            g.setName(person.getName());
            g.setSurname(person.getSurname());
            g.setEmail(person.getEmail());
            g.setAvatar(person.getAvatar());
            g.setTargetID(person.getUserID());
        }
        
        return g;
    }

    public static NewUserMessage generateNewUser(Profile userProfile) {
        return new NewUserMessage(userProfile.getUserID(), 
                userProfile.getName(), userProfile.getSurname(), userProfile.getEmail(), 
                userProfile.getAvatar());
    }
}
