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
public class Model {
    public static Model INSTANCE =new Model();
    
    private ColleagueList list;
    private User user;

    public Model() {
        list =new ColleagueList();
    }
    
    public ColleagueList getColleagueList() {
        return list;
    }

    public String getUserID() {
        return user.getUserID();
    }
    
    public void setUser(String userID, String username, String name, String surname, String email, String avatar, String title, String aboutMe) {
        user =new User(userID, username, name, surname, email, avatar, title, aboutMe);
    }
}