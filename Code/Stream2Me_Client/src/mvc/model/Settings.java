/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Bernhard
 */
public class Settings {
    private String groupID;
    private String groupName;
    private String ownerName;
    private boolean owner;
    
    private Map<String, String> groups;

    public Settings() {
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public void setGroupName(String GroupName) {
        this.groupName = GroupName;
    }

    public void setGroupID(String GroupID) {
        this.groupID = GroupID;
    }
    
    public void reset() {
        this.groupID =null;
        this.groupName =null;
        this.owner =false;
        this.groups =new HashMap<>();
    }

    public void setOwnerName(String ownerName) {
        this.ownerName =ownerName;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Map<String, String> getGroups() {
        return groups;
    }

    public String getGroupListID(int index, String groupName) {
        Set<String> keys =groups.keySet();
        if (index >= 0 && index < keys.size()) {
            String groupID =(String)keys.toArray()[index];
            if (groups.get(groupID).equals(groupName)) {
                return groupID;
            }
        }
        return null;
    }

    public void clear() {

        groupID =null;
        groupName =null;
        ownerName =null;
        owner =false;

        groups =null;
    }
}
