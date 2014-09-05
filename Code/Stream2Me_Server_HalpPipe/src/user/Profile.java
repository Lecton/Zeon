/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

/**
 *
 * @author Bernhard
 * @author Zenadia
 */
public class Profile {
    private int userID;
    private String name;
    private String surname;
    private String email;
    private String avatar;
    private String password; 
    private String title;

    public Profile(int userID, String name, String surname, String email, String avatar, String title) {
        this.userID = userID;
        this.name =name;
        this.surname =surname;
        this.email = email;
        this.avatar = avatar;
        this.title = title;
    }

    public int getUserID() {
        return this.userID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
