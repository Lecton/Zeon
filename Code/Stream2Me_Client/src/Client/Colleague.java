/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import utils.Log;
import java.util.ArrayList;
import messages.StringMessage;

/**
 *
 * @author Bernhard
 */
public class Colleague {
    private int userID =-10;
    private String name ="User";
    private String surname ="User";
    private String email ="User@user.com";
    private String avatar ="";
    
    private ArrayList<StringMessage> chatHistory;

    public Colleague(int userID, String name, String surname, String email) {
        this.userID =userID;
        this.name =name;
        this.surname =surname;
        this.email =email;
        this.avatar ="";
        this.chatHistory =new ArrayList<>();
    }

    public Colleague(int userID, String name, String surname, String email, String avatar) {
        this.userID =userID;
        this.name =name;
        this.surname =surname;
        this.email =email;
        this.avatar =avatar;
        this.chatHistory =new ArrayList<>();
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }

    public ArrayList<StringMessage> getMessageHistory() {
        return chatHistory;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void addMessage(StringMessage sm) {
        chatHistory.add(sm);
    }

    public String getFullName() {
        return name + " " + surname;
    }
}
