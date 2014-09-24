/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

import core.database.objects.StreamProperty;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.media.creation.StreamPropertyMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bernhard
 */
public class StreamHandlerTest {
    static String connectionID =UUID.randomUUID().toString();
    static String userID = "bca2414d-7ee0-40d9-a130-def36c44dc49";
    static String affectedUserID = "dd124987-5599-4adf-8b6a-e465b23cfc61";
    static String streamID;
    
    public StreamHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Database.INSTANCE.connect();
        PreparedStatement statement =null;
        try{
            String query = "INSERT INTO connection " +
                    "(connectionID, userID)" +
                    "VALUES (?, ?)";
            statement = Database.INSTANCE.getPreparedStatement(query);
            statement.setString(1, connectionID);
            statement.setString(2, userID);
            statement.execute();
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
    
    @AfterClass
    public static void tearDownClass() {
        PreparedStatement statement =null;
        try{
            String query ="DELETE FROM connection " +
                    "WHERE connectionID = ?";
            statement = Database.INSTANCE.getPreparedStatement(query);
            statement.setString(1, connectionID);
            statement.execute();
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
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of updateStream method, of class StreamHandler.
     */
    @Test
    public void testUpdateStreamAdd() {
        System.out.println("updateStream");
        boolean result = StreamHandler.updateStream(streamID, userID, affectedUserID, connectionID, true);
        assertTrue("Stream could not be updated", result);
    }

    /**
     * Test of createStreamProperty method, of class StreamHandler.
     */
    @Test
    public void testCreateStreamProperty() {
        System.out.println("createStreamProperty");
        String streamName = "testStream";
        int type = 0;
        StreamPropertyMessage result = StreamHandler.createStreamProperty(userID, connectionID, streamName, type);
        streamID =result.getStreamID();
        assertTrue("Creation unsuccessful.", result.isSuccessful());
    }
    
    /**
     * Test of respondStream method, of class StreamHandler.
     */
    @Test
    public void testRespondStreamAccept() {
        System.out.println("respondStream_Accept");
        boolean response = true;
        boolean result = StreamHandler.respondStream(streamID, affectedUserID, connectionID, response);
        assertTrue("Could not update my stream invite", result);
    }
    
    /**
     * Test of respondStream method, of class StreamHandler.
     */
    @Test
    public void testRespondStreamReject() {
        System.out.println("respondStream_Reject");
        boolean response = false;
        boolean result = StreamHandler.respondStream(streamID, affectedUserID, connectionID, response);
        assertTrue("Could not update my stream invite", result);
    }

    /**
     * Test of updateStream method, of class StreamHandler.
     */
    @Test
    public void testUpdateStreamRemove() {
        System.out.println("updateStream");
        boolean result = StreamHandler.updateStream(streamID, userID, affectedUserID, connectionID, false);
        assertTrue("Removal failed", result);
    }

    /**
     * Test of getStreamProperty method, of class StreamHandler.
     */
    @Test
    public void testGetStreamProperty() {
        System.out.println("getStreamProperty");
        StreamProperty result = StreamHandler.getStreamProperty(userID, streamID, true);
        assertNotNull("Did not find stream property", result);
    }

    /**
     * Test of removeStreamProperty method, of class StreamHandler.
     */
    @Test
    public void testRemoveStreamProperty() {
        System.out.println("removeStreamProperty");
        StreamProperty result = StreamHandler.removeStreamProperty(userID, streamID);
        assertNotNull("Stream did not get removeed", result);
    }
}
