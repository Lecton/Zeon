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
public class Connection {
    final String connectionid;
    final String userid;
    final String timestamp ="";

    public Connection(String connectionid, String userid) {
        this.connectionid = connectionid;
        this.userid = userid;
    }

    public String getConnectionid() {
        return connectionid;
    }

    public String getUserid() {
        return userid;
    }
}
