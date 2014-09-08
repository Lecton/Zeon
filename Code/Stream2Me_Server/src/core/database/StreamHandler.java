/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

import core.database.objects.StreamProperty;
import messages.media.AudioStreamMessage;
import messages.media.VideoStreamMessage;

/**
 *
 * @author Bernhard
 */
public class StreamHandler {

    /**
     * Adds a stream property to the database with the userID as the owner
     * and the stream Name.
     * Cannot be added if a stream for the provided owner and name already exists
     * @param userID - ID of the stream owner
     * @param streamName - Name of the stream
     */
    public static void createStreamProperty(int userID, String streamName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Removes the stream property from the database and removes all data entries 
     * for the stream property from the database. Returns all the users added to 
     * the stream property. The accepted flag is not taken into account.
     * 
     * The owner ID and stream name are used to jointly identify the stream.
     * @param userID - ID of the stream owner
     * @param streamName - Name of the stream
     * @return StreamProperty with the required stream data for notifying everyone of removal
     */
    public static StreamProperty removeStreamProperty(int userID, String streamName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Adds an entry to the database for the provided stream and the user. If the user
     * is allowed on the stream, then it sets the stream data to the provided response
     * @param streamID - ID of the stream
     * @param userID - ID of the user
     * @param response - User response to the stream
     * @return success of the response
     */
    public static boolean respondStream(int streamID, int userID, boolean response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Adds/Removes a user to/from the stream. Provided the owner has a 
     * stream with the stream Name
     * @param streamName - Name of the stream
     * @param ownerID - Owner of the stream
     * @param affectedUserID - User affected by the update
     * @param action - Add or remove flag. 0 - Remove. 1 - Add
     * @return ID of the stream or -1 if not found or error
     */
    public static int updateStream(String streamName, int ownerID, int affectedUserID, int action) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Generate a stream property object from the stream data for the owner and streamName.
     * The stream is identified using the ownerID and stream name
     * @param ownerID - Owner of the stream
     * @param streamName - Name of the stream
     * @return Stream Property for the found stream. Empty stream property if nothing found
     */
    public static StreamProperty getStreamProperty(int ownerID, String streamName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
