/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author Lecton
 */
public class Group {
    String groupID;
    String ownerID;
    String name;

    public Group() {
    }

    public Group(String groupID, String ownerID, String name) {
        this.groupID = groupID;
        this.ownerID = ownerID;
        this.name = name;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getName() {
        return name;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}
