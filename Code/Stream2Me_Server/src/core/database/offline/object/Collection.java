/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.database.offline.object;

/**
 *
 * @author Bernhard
 */
public class Collection {
    public static Collection DEFAULT;
    String groupID;
    Client owner;
    String name;
    String password;
    
    public Collection(String groupID, Client owner, String name, String password) {
        this.groupID = groupID;
        this.owner = owner;
        this.name = name;
        this.password = password;
    }

    public String getGroupID() {
        return groupID;
    }

    public Client getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
