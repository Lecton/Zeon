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
public class StreamData {
    public static StreamData query(String streamID) {
        StreamData sd =new StreamData();
        sd.streamID =streamID;
        return sd;
    }
    
    public static StreamData query(String streamID, boolean accepted) {
        StreamData sd =new StreamData();
        sd.streamID =streamID;
        sd.setAccepted(accepted);
        return sd;
    }
    
    public static StreamData query(String userID, String connectionID, String streamID) {
        StreamData sd =new StreamData();
        sd.userID =userID;
        sd.connectionID =connectionID;
        sd.streamID =streamID;
        return sd;
    }

    public static Object queryUser(String userID, String connectionID) {
        StreamData sd =new StreamData();
        sd.userID =userID;
        sd.connectionID =connectionID;
        return sd;
    }
    
    String streamDataID;
    String streamID;
    String userID;
    String accepted;
    String connectionID;

    public StreamData() {
        streamDataID =null;
        streamID =null;
        userID =null;
        accepted =null;
        connectionID =null;
    }

    public StreamData(String streamDataID, String streamID, String userID, String connectionID) {
        this.streamDataID = streamDataID;
        this.streamID = streamID;
        this.userID = userID;
        this.connectionID = connectionID;
        this.accepted ="f";
    }

    public boolean isAccepted() {
        return accepted.equals("t");
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted?"t":"f";
    }

    public String getStreamDataID() {
        return streamDataID;
    }

    public String getConnectionID() {
        return connectionID;
    }

    public String getStreamID() {
        return streamID;
    }

    public String getUserID() {
        return userID;
    }

    public void setStreamID(String streamID) {
        this.streamID = streamID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
