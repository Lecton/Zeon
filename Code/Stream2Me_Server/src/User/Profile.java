/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package User;

/**
 *
 * @author Bernhard
 */
public class Profile {
    private int userID;
    private String username;
    private String email;
    private String avatar;
    private String password; 

    public Profile(int userID, String username, String email, String avatar) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.avatar = avatar;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getPassword() {
        return password;
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
}
