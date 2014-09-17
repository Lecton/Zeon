/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.model;

/**
 *
 * @author Bernhard
 */
public class User {
    private String userID;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String avatar;
    private String title;
    private String aboutMe;
    
    public User(){}

    public User(String userID, String username, String name, String surname, String email, String avatar, String title, String aboutMe) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatar = avatar;
        this.title = title;
        this.aboutMe = aboutMe;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getTitle() {
        return title;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
