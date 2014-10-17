/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database.offline;

import com.db4o.ObjectSet;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import connection.messageChannel.MessageBuilder;
import core.database.DatabaseHandler;
import core.database.abstractInterface.StreamHandler;
import core.database.objects.StreamProperty;
import core.database.offline.object.Client;
import core.database.offline.object.Connection;
import core.database.offline.object.StreamData;
import core.database.offline.object.StreamProp;
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
public class OfflineStreamHandler implements StreamHandler {
    
    private boolean streamPropertyExists(String userID, int type) throws UnsupportedOperationException {
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
            ObjectSet<Client> resultC =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client person =resultC.next();
            
            ObjectSet<StreamProp> result =OfflineDatabase.INSTANCE.db.queryByExample(StreamProp.queryUserAndType(person, streamType));
            
            if(result.hasNext()) {
                int count =result.size();
                return count > 0;
            }
            return false;
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Query did not execute and broke.");
    }
    
    @Override
    public StreamPropertyMessage createStreamProperty(String userID, String connectionID, String streamName, int type) {
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
            ObjectSet<Client> resultC =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client person =resultC.next();
            ObjectSet<Connection> resultCon =OfflineDatabase.INSTANCE.db.queryByExample(new Connection(null, userID));
            Connection connection =resultCon.next();
            
            String streamID =UUID.randomUUID().toString();
            StreamProp sp =new StreamProp(streamID, person, streamType, streamName, connection);
            OfflineDatabase.INSTANCE.db.store(sp);
            OfflineDatabase.INSTANCE.db.commit();
            return MessageBuilder.generateStreamResponse(userID, streamID, streamName, type, true);
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        StreamPropertyMessage msg =MessageBuilder.generateStreamResponse(userID, null, streamName, type, false);
        msg.setError("Stream creation error.");
        return msg;
    }

    @Override
    public StreamProperty removeStreamProperty(String userID, String streamID) {
        try {
            StreamProperty sp =getStreamProperty(userID, streamID, true);
            ObjectSet<Client> resultC =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client person =resultC.next();
            
            ObjectSet<StreamProp> result =OfflineDatabase.INSTANCE.db.queryByExample(StreamProp.queryIDAndUser(streamID, person));
            
            if (result.size() == 1) {
                OfflineDatabase.INSTANCE.db.delete(result.next());
                OfflineDatabase.INSTANCE.db.commit();
                return sp;
            } else if (result.size() == 0) {
                throw new UnsupportedOperationException("Query did not do anything.");
            } else {
                throw new UnsupportedOperationException("Query did not return a valid result.");
            }
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Query did not execute and broke.");
    }

    @Override
    public boolean respondStream(String streamID, String userID, String connectionID, boolean response) {
        try {
            ObjectSet<Client> resultC =OfflineDatabase.INSTANCE.db.queryByExample(new Client(userID));
            Client person =resultC.next();
            
            ObjectSet<StreamData> result =OfflineDatabase.INSTANCE.db.queryByExample(StreamData.query(userID, connectionID, streamID));
            StreamData sd =result.next();
            sd.setAccepted(response);
            OfflineDatabase.INSTANCE.db.store(sd);
            OfflineDatabase.INSTANCE.db.commit();
            return true;
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean updateStream(String streamID, String ownerID, String affectedUserID, String affectedConnectionID, boolean action) {
        if (action) {
            StreamProperty sp =getStreamProperty(ownerID, streamID, false);
            if (sp != null) {
                try {
                    String uID =UUID.randomUUID().toString();
                    StreamData sd =new StreamData(uID, streamID, affectedUserID, affectedConnectionID);
                    OfflineDatabase.INSTANCE.db.store(sd);
                    return true;
                } catch (DatabaseReadOnlyException ex) {
                    Logger.getLogger(OfflineUserHandler.class.getName())
                            .log(Level.SEVERE, "INSERT EXCEPTION", ex);
                }
            }
            System.out.println("Could not find the stream property.");
            return false;
        } else {
            StreamProperty sp =getStreamProperty(ownerID, streamID, false);
            if (sp != null) {
                try {
                    StreamData sd =new StreamData();
                    sd.setStreamID(streamID);
                    sd.setUserID(affectedUserID);
                    ObjectSet<StreamData> result =OfflineDatabase.INSTANCE.db.queryByExample(sd);
                    System.out.println(result.size());
                    sd =result.next();
                    OfflineDatabase.INSTANCE.db.delete(sd);
                    return true;
                } catch (Db4oIOException | IllegalStateException ex) {
                    Logger.getLogger(OfflineUserHandler.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
            return false;
        }
    }

    @Override
    public StreamProperty getStreamProperty(String ownerID, String streamID, boolean withTargets) {
        try {
            ObjectSet<StreamProp> result =OfflineDatabase.INSTANCE.db.queryByExample(new StreamProp(streamID));
            
            if(result.hasNext()) {
                StreamProp sp =result.next();
                String type =sp.getType();
                
                if (withTargets) {
                    return new StreamProperty(getStreamDataActiveTargetIDs(streamID), streamID, sp.getName(), DatabaseHandler.userHandler.getGroupID(ownerID), type.equals("video") ? 0 : 1); 
                }
                
                return new StreamProperty(new String[0], streamID, sp.getName(), DatabaseHandler.userHandler.getGroupID(ownerID), type.equals("video") ? 0 : 1);
            }
            return null;
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("SELECT query did not execute and broke.");
    }

    private String[] getStreamDataTargetIDs(String streamID) {
        try {
            ObjectSet<StreamData> result =OfflineDatabase.INSTANCE.db.queryByExample(StreamData.query(streamID));
            
            List<String> userIDs =new ArrayList<>();
            while (result.hasNext()) {
                StreamData sd =result.next();
                userIDs.add(sd.getUserID());
            }
            return userIDs.toArray(new String[0]);
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("SELECT query did not execute and broke.");
    }
    
    private String[] getStreamDataActiveTargetIDs(String streamID) {
        try {
            ObjectSet<StreamData> result =OfflineDatabase.INSTANCE.db.queryByExample(StreamData.query(streamID, true));
            
            List<String> userIDs =new ArrayList<>();
            while (result.hasNext()) {
                StreamData sd =result.next();
                userIDs.add(sd.getUserID());
            }
            return userIDs.toArray(new String[0]);
        } catch (Db4oIOException ex) {
            Logger.getLogger(OfflineUserHandler.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("SELECT query did not execute and broke.");
    }
}
