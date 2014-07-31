/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import Messages.StringMessage;
import Utils.Log;
import java.util.ArrayList;

/**
 *
 * @author Bernhard
 */
public class Colleague {
    private int userID =-10;
    private String username ="User";
    private String email ="User@user.com";
    private String avatar ="";
    
    private ArrayList<StringMessage> chatHistory;

    public Colleague(int userID, String username, String email) {
        this.userID =userID;
        this.username =username;
        this.email =email;
        this.avatar ="";
        this.chatHistory =new ArrayList<>();
    }

    public Colleague(int userID, String username, String email, String avatar) {
        this.userID =userID;
        this.username =username;
        this.email =email;
        this.avatar =avatar;
        this.chatHistory =new ArrayList<>();
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
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

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void addMessage(StringMessage sm) {
        chatHistory.add(sm);
    }
}
