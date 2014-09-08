/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messageChannel;

import core.database.objects.User;
import messages.userConnection.GreetingMessage;
import messages.userConnection.NewUserMessage;

/**
 *
 * @author Bernhard
 */
public class MessageBuilder {
    public static GreetingMessage generateGreeting(User person, boolean success) {
        GreetingMessage msg =new GreetingMessage(success);
        if (success) {
            msg.setUserID(person.getUserID());
            msg.setUsername(person.getUsername());
            msg.setName(person.getName());
            msg.setSurname(person.getSurname());
            msg.setEmail(person.getEmail());
            msg.setTitle(person.getTitle());
            msg.setAboutMe(person.getAboutMe());
            msg.setAvatar(person.getAvatar());
                    
        }
        return msg;
    }

    public static NewUserMessage generateNewUser(User person) {
        NewUserMessage msg =new NewUserMessage(person.getUserID(), person.getName()
                , person.getSurname(), person.getEmail(), person.getAvatar()
                , person.getTitle(), person.getAboutMe());
        return msg;
    }
}