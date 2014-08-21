/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package User;

/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class Profile {
    private int userID;
    private String username;
    private String email;
    private String avatar;
    private String password; 
    private String title;

    public Profile(int userID, String username, String email, String avatar, String title) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.title = title;
    }

    public int getUserID() {
        return this.userID;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getPassword() {
        return this.password;
    }
    
    public String getTitle(){
        return this.title;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
}
