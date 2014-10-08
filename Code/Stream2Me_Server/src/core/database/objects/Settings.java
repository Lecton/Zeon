/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.objects;

import java.util.Map;

/**
 *
 * @author Bernhard
 */
public class Settings {
    String groupID;
    String groupName;
    String ownerName;
    String ownerID;

    public Settings(String groupID, String groupName, String ownerName, String ownerID) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.ownerName = ownerName;
        this.ownerID = ownerID;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }
    
    public boolean isOwner(String userID) {
        return ownerID.equals(userID);
    }
}
