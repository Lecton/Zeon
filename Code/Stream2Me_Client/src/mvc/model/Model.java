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
    private static User user =new User();
    private static ColleagueList colleagueList =new ColleagueList();

    public static ColleagueList getColleagueList() {
        return colleagueList;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User newUser) {
        user =newUser;
    }
}
