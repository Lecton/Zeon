/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.objects;

/**
 *
 * @author Bernhard
 */
public class User extends BaseUser {
    String username;
    String name;
    String surname;
    String email;
    String title;
    String aboutMe;
    String avatar;

    public User(String userID, String username, String groupID, String name, String surname, String email, String title, String aboutMe, String avatar) {
        super(userID, groupID);
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.title = title;
        this.aboutMe = aboutMe;
        this.avatar = avatar;
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

    public String getAboutMe() {
        return aboutMe;
    }

    public String getTitle() {
        return title;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
