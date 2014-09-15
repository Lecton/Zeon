/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author Lecton
 */
public class StreamData {
    String streamDataID;
    String streamID;
    String userID;
    boolean accepted;

    public StreamData() {
    }

    public StreamData(String streamDataID, String streamID, String userID, boolean accepted) {
        this.streamDataID = streamDataID;
        this.streamID = streamID;
        this.userID = userID;
        this.accepted = accepted;
    }

    public String getStreamDataID() {
        return streamDataID;
    }

    public String getStreamID() {
        return streamID;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public void setStreamDataID(String streamDataID) {
        this.streamDataID = streamDataID;
    }

    public void setStreamID(String streamID) {
        this.streamID = streamID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
