/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.Date;

/**
 *
 * @author Lecton
 */
public class User {
    String userID;
    String groupID;
    String username;
    String name;
    String surname;
    String email;
    String avatar;
    String title;
    Date registration;
    boolean loggedIn;

    public User() {
    }

    public User(String userID, String groupID, String username, String name, String surname, String email, String avatar, String title, Date registration, boolean loggedIn) {
        this.userID = userID;
        this.groupID = groupID;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatar = avatar;
        this.title = title;
        this.registration = registration;
        this.loggedIn = loggedIn;
    }

    public Date getRegistration() {
        return registration;
    }
       
    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTitle() {
        return title;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setRegistration(Date registration) {
        this.registration = registration;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
