/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author Lecton
 */
public class StreamProperty {
    String streamID;
    String userID;
    String type;
    String name;

    public StreamProperty() {
    }

    public StreamProperty(String streamID, String userID, String type, String name) {
        this.streamID = streamID;
        this.userID = userID;
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getStreamID() {
        return streamID;
    }

    public String getType() {
        return type;
    }

    public String getUserID() {
        return userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreamID(String streamID) {
        this.streamID = streamID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
