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
public class StreamProp {
    
    public static StreamProp queryUserAndType(Client user, String type) {
        StreamProp sp =new StreamProp();
        sp.user =user;
        sp.type =type;
        return sp;
    }
    
    public static StreamProp queryIDAndUser(String streamID, Client user) {
        StreamProp sp =new StreamProp();
        sp.user =user;
        sp.streamID =streamID;
        return sp;
    }

    public static StreamProp queryUser(Client user) {
        StreamProp sp =new StreamProp();
        sp.user =user;
        return sp;
    }
    
    String streamID;
    Client user;
    String type;
    String name;
    Connection con;

    public StreamProp() {
    }
    
    public StreamProp(String streamID) {
        this.streamID =streamID;
    }

    public StreamProp(String streamID, Client user, String type, String name, Connection con) {
        this.streamID = streamID;
        this.user = user;
        this.type = type;
        this.name = name;
        this.con = con;
    }

    public String getStreamID() {
        return streamID;
    }

    public Client getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Connection getCon() {
        return con;
    }
}
