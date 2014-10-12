/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.abstractInterface;

import core.database.objects.StreamProperty;
import messages.media.creation.StreamPropertyMessage;

/**
 *
 * @author Bernhard
 */
public interface StreamHandler {

    /**
     * Adds a stream property to the database with the userID as the owner
     * and the stream Name.
     * Cannot be added if a stream for the provided owner and name already exists
     * @param userID - ID of the stream owner
     * @param connectionID
     * @param streamName - Name of the stream
     * @param type
     * @return success of the stream creation
     */
    public abstract StreamPropertyMessage createStreamProperty(String userID, String connectionID, String streamName, int type);

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
    public abstract StreamProperty removeStreamProperty(String userID, String streamID);

    /**
     * Adds an entry to the database for the provided stream and the user. If the user
     * is allowed on the stream, then it sets the stream data to the provided response
     * @param streamID - ID of the stream
     * @param userID - ID of the user
     * @param response - User response to the stream
     * @return success of the response
     */
    public abstract boolean respondStream(String streamID, String userID, String connectionID, boolean response);

    /**
     * Adds/Removes a user to/from the stream. Provided the owner has a 
     * stream with the stream ID
     * @param streamID - ID of the stream
     * @param ownerID - Owner of the stream
     * @param affectedUserID - User affected by the update
     * @param connectionID - Session ID of the affected user
     * @param action - Add or remove flag. 0 - Remove. 1 - Add
     * @return success of the request
     */
    public abstract boolean updateStream(String streamID, String ownerID, String affectedUserID, String affectedConnectionID, boolean action);

    /**
     * Generate a stream property object from the stream data for the owner and streamID.
     * The stream is identified using the ownerID and streamID
     * @param ownerID - Owner of the stream
     * @param streamID - ID of the stream
     * @return Stream Property for the found stream. Null if nothing found
     */
    public abstract StreamProperty getStreamProperty(String ownerID, String streamID, boolean withTargets);
}
