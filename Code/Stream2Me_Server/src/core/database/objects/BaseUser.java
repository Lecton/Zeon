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
public class BaseUser {
    final String userID;
    String groupID;
    boolean LoggedIn =false;

    public BaseUser(String userID, String groupID) {
        this.userID = userID;
        this.groupID = groupID;
    }

    public String getUserID() {
        return userID;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isLoggedIn() {
        return LoggedIn;
    }

    public void setLoggedIn(boolean LoggedIn) {
        this.LoggedIn = LoggedIn;
    }
}
