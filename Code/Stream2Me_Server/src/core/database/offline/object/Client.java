/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.offline.object;

import core.database.objects.User;

/**
 *
 * @author Bernhard
 */
public class Client {
    private static String defaultAvatar ="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";

    public static Client queryUsername(String username) {
        Client c =new Client(null);
        c.setUsername(username);
        return c;
    }
    
    public static Client queryEmail(String email) {
        Client c =new Client(null);
        c.setEmail(email);
        return c;
    }
    
    String userid;
    Collection group =Collection.DEFAULT;
    String name;
    String surname;
    String username;
    String password;
    String email;
    String avatar =defaultAvatar;
    String aboutme ="";
    String title ="";
    String registrationdate;
    String loggedin ="f";

    public Client() {
        userid =null;
        group =null;
        name =null;
        surname =null;
        username =null;
        password =null;
        email =null;
        avatar =null;
        aboutme =null;
        title =null;
        registrationdate =null;
        loggedin =null;
    }
    
    public Client(String userID) {
        userid =userID;
        group =null;
        name =null;
        surname =null;
        username =null;
        password =null;
        email =null;
        avatar =null;
        aboutme =null;
        title =null;
        registrationdate =null;
        loggedin =null;
    }
    
    public Client(String userid, String name, String surname, String username, String password, String email, String registrationDate) {
        this.userid =userid;
        this.name =name;
        this.surname =surname;
        this.username =username;
        this.password =password;
        this.email =email;
        this.registrationdate =registrationDate;
    }

    public String getUserid() {
        return userid;
    }

    public Collection getGroup() {
        return group;
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

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getAboutme() {
        return aboutme;
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

    public void setGroup(Collection group) {
        this.group = group;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin?"t":"f";
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLoggedin() {
        return loggedin.equals("t");
    }

    public User getUser() {
        return new User(userid, username, group.groupID, name, surname, email, title, aboutme, avatar);
    }
}
