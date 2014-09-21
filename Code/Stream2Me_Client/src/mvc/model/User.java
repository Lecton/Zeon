/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

import java.util.logging.Logger;
import mvc.model.person.Streamer;

/**
 *
 * @author Bernhard
 */
public class User extends Streamer {
    private final static Logger LOGGER = Logger.getLogger(User.class.getName());
    
    private String username;

    public User(String userID, String username, String name, String surname, String email, String avatar, String title, String aboutMe) {
        super(userID, name, surname, email, avatar, title, aboutMe);
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
}
