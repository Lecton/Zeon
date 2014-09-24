/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

import connection.messageChannel.MessageBuilder;
import core.database.objects.StreamProperty;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.media.creation.StreamPropertyMessage;

/**
 *
 * @author Bernhard
 */
public class StreamHandler {
    
    private static boolean streamPropertyExists(String userID, int type) throws UnsupportedOperationException {
        PreparedStatement statement =null;
        try {
            String streamType =null;
            if (type == 0) {
                streamType ="video";
            } else if (type == 1) {
                streamType ="audio";
            } else {
                System.out.println("Unknown stream type");
                throw new UnsupportedOperationException("Stream type could not be found");
            }
            
            String query = "SELECT count(1) FROM streamproperty " +
                    "WHERE userID = ? " +
                    "AND type = ?";
            statement = Database.INSTANCE.getPreparedStatement(query);
            statement.setString(1, userID);
            statement.setString(2, streamType);
            ResultSet result =statement.executeQuery();
            
            if(result.next()) {
                int count =result.getInt(1);
                return count > 0;
            }
            throw new UnsupportedOperationException("Query did not return a result.");
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        throw new UnsupportedOperationException("Query did not execute and broke.");
    }
    

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
    public static StreamPropertyMessage createStreamProperty(String userID, String connectionID, String streamName, int type) {
        PreparedStatement statement =null;
        try {
            if (streamPropertyExists(userID, type)) {
                StreamPropertyMessage msg =MessageBuilder.generateStreamResponse(userID, null, streamName, type, false);
                msg.setError("Stream of this type already exists for user.");
                return msg;
            }
            
            String streamType =null;
            if (type == 0) {
                streamType ="video";
            } else if (type == 1) {
                streamType ="audio";
            } else {
                StreamPropertyMessage msg =MessageBuilder.generateStreamResponse(userID, null, streamName, type, false);
                msg.setError("Stream type could not be identified.");
                return msg;
            }
            
            String streamID =UUID.randomUUID().toString();
            
            String query = "INSERT INTO streamproperty " +
                    "(streamID, userID, name, connectionID, type)" +
                    "VALUES (?, ?, ?, ?, ?)";
            statement = Database.INSTANCE.getPreparedStatement(query);
            statement.setString(1, streamID);
            statement.setString(2, userID);
            statement.setString(3, streamName);
            statement.setString(4, connectionID);
            statement.setString(5, streamType);
            statement.execute();
            return MessageBuilder.generateStreamResponse(userID, streamID, streamName, type, true);
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        StreamPropertyMessage msg =MessageBuilder.generateStreamResponse(userID, null, streamName, type, false);
        msg.setError("Stream creation error.");
        return msg;
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
    public static StreamProperty removeStreamProperty(String userID, String streamID) {
        PreparedStatement statement =null;
        try {
            StreamProperty sp =getStreamProperty(userID, streamID, true);
            String query = "DELETE FROM streamproperty " +
                    "WHERE streamID = ? " +
                    "AND userID = ?";
            
            statement = Database.INSTANCE.getPreparedStatement(query);
            statement.setString(1, streamID);
            statement.setString(2, userID);
            int result =statement.executeUpdate();
            if (result == 1) {
                return sp;
            } else if (result == 0) {
                throw new UnsupportedOperationException("Query did not do anything.");
            } else {
                throw new UnsupportedOperationException("Query did not return a valid result.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        throw new UnsupportedOperationException("Query did not execute and broke.");
    }

    /**
     * Adds an entry to the database for the provided stream and the user. If the user
     * is allowed on the stream, then it sets the stream data to the provided response
     * @param streamID - ID of the stream
     * @param userID - ID of the user
     * @param response - User response to the stream
     * @return success of the response
     */
    public static boolean respondStream(String streamID, String userID, String connectionID, boolean response) {
        PreparedStatement statement;
        ResultSet result = null;
        String query = "UPDATE streamData " +
                        "SET accepted = ? " +
                        "WHERE userID = ?" +
                        "AND connectionID = ? " +
                        "AND streamID = ? ";
        statement = Database.INSTANCE.getPreparedStatement(query);
        try {
            statement.setBoolean(1, response);
            statement.setString(2, userID);
            statement.setString(3, connectionID);
            statement.setString(4, streamID);
            int lines =statement.executeUpdate();
            if (lines == 1) {
                return true;
            } else {
//                System.out.println(lines);
//                Logger.getLogger(UserHandler.class.getName())
//                        .log(Level.SEVERE, "Lines changed error", 
//                                new SQLException("SQL returned with invalid "
//                                        + "line change amount", 
//                                        statement.toString()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(statement != null) {
                    statement.close();
                }
            } catch(SQLException ex) {
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

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
    public static boolean updateStream(String streamID, String ownerID, String affectedUserID, String affectedConnectionID, boolean action) {
        if (action) {
            StreamProperty sp =getStreamProperty(ownerID, streamID, false);
            if (sp != null) {
                PreparedStatement statement =null;
                try {
                    String query = "INSERT INTO streamData " +
                            "(streamdataid, streamid, userid, connectionid) " +
                            "VALUES (?, ?, ?, ?)";
                    statement = Database.INSTANCE.getPreparedStatement(query);
                    statement.setString(1, UUID.randomUUID().toString());
                    statement.setString(2, streamID);
                    statement.setString(3, affectedUserID);
                    statement.setString(4, affectedConnectionID);
                    int result =statement.executeUpdate();

                    if(result == 1) {
                        return true;
                    }
                    throw new UnsupportedOperationException("Query did not return a result.");
                } catch (SQLException ex) {
                    Logger.getLogger(UserHandler.class.getName())
                            .log(Level.SEVERE, "INSERT EXCEPTION", ex);
                } finally {
                    try{
                        if(statement != null){
                            statement.close();
                        }
                    }catch(SQLException ex){
                        Logger.getLogger(UserHandler.class.getName())
                                .log(Level.SEVERE, "CLOSING EXCEPTION", ex);
                    }
                }
            }
            System.out.println("Could not find the stream property.");
            return false;
        } else {
            StreamProperty sp =getStreamProperty(ownerID, streamID, false);
            if (sp != null) {
                PreparedStatement statement =null;
                try {
                    String query = "DELETE FROM streamData " +
                            "WHERE streamID = ? " +
                            "AND userID = ?";
                    statement = Database.INSTANCE.getPreparedStatement(query);
                    statement.setString(1, streamID);
                    statement.setString(2, affectedUserID);
                    int result =statement.executeUpdate();
                    if (result > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UserHandler.class.getName())
                            .log(Level.SEVERE, null, ex);
                } finally {
                    try{
                        if(statement != null){
                            statement.close();
                        }
                    }catch(SQLException ex){
                        Logger.getLogger(UserHandler.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
            }
            return false;
        }
    }

    /**
     * Generate a stream property object from the stream data for the owner and streamID.
     * The stream is identified using the ownerID and streamID
     * @param ownerID - Owner of the stream
     * @param streamID - ID of the stream
     * @return Stream Property for the found stream. Null if nothing found
     */
    public static StreamProperty getStreamProperty(String ownerID, String streamID, boolean withTargets) {
        PreparedStatement statement =null;
        try {            
            String query = "SELECT * FROM streamproperty " +
                    "WHERE streamID = ? ";
            statement = Database.INSTANCE.getPreparedStatement(query);
            statement.setString(1, streamID);
            ResultSet result =statement.executeQuery();
            
            if(result.next()) {
                String type =result.getString("type");
                
                if (withTargets) {
                    return new StreamProperty(getStreamDataActiveTargetIDs(streamID), streamID, UserHandler.getGroupID(ownerID), type.equals("video") ? 0 : 1); 
                }
                
                return new StreamProperty(new String[0], streamID, UserHandler.getGroupID(ownerID), type.equals("video") ? 0 : 1);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        throw new UnsupportedOperationException("SELECT query did not execute and broke.");
    }

    private static String[] getStreamDataTargetIDs(String streamID) {
        PreparedStatement statement =null;
        try {            
            String query = "SELECT userID FROM streamdata " +
                    "WHERE streamID = ? ";
            statement = Database.INSTANCE.getPreparedStatement(query);
            statement.setString(1, streamID);
            ResultSet result =statement.executeQuery();
            
            List<String> userIDs =new ArrayList<>();
            while (result.next()) {
                userIDs.add(result.getString(1));
            }
            return userIDs.toArray(new String[0]);
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        throw new UnsupportedOperationException("SELECT query did not execute and broke.");
    }
    
    private static String[] getStreamDataActiveTargetIDs(String streamID) {
        PreparedStatement statement =null;
        try {            
            String query = "SELECT userID FROM streamdata " +
                    "WHERE streamID = ? " +
                    "AND accepted  = TRUE";
            statement = Database.INSTANCE.getPreparedStatement(query);
            statement.setString(1, streamID);
            ResultSet result =statement.executeQuery();
            
            List<String> userIDs =new ArrayList<>();
            while (result.next()) {
                userIDs.add(result.getString(1));
            }
            return userIDs.toArray(new String[0]);
        } catch (SQLException ex) {
            Logger.getLogger(UserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        } finally {
            try{
                if(statement != null){
                    statement.close();
                }
            }catch(SQLException ex){
                Logger.getLogger(UserHandler.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        throw new UnsupportedOperationException("SELECT query did not execute and broke.");
    }
}
