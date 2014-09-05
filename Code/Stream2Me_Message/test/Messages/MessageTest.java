/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages;

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
public class MessageTest {
    
    public MessageTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMessage method, of class Message.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        Message instance = new MessageImpl();
        String result = instance.getMessage();
        assertNull("The test case is implemented and should be abstract.", result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of getSender method, of class Message.
     */
    @Test
    public void testGetSender() {
        System.out.println("getSender");
        Message instance = new MessageImpl();
        String expResult = "";
        String result = instance.getSender();
        assertTrue("The sender has been set and is not blank.", expResult.equals(result));
    }

    /**
     * Test of getUserID method, of class Message.
     */
    @Test
    public void testGetUserID() {
        System.out.println("getUserID");
        Message instance = new MessageImpl();
        int expResult = Message.IGNORE;
        int result = instance.getUserID();
        assertEquals("The User ID was changed from it default -1.",expResult, result);
    }

    /**
     * Test of getTimestamp method, of class Message.
     */
    @Test
    public void testGetTimestamp() {
        System.out.println("getTimestamp");
        Message instance = new MessageImpl();
        String expResult = "";
        String result = instance.getTimestamp();
        assertNotNull("The timestamp was not set automatically.", result);
    }

    /**
     * Test of getTargetID method, of class Message.
     */
    @Test
    public void testGetTargetID() {
        System.out.println("getTargetID");
        Message instance = new MessageImpl();
        int expResult = Message.IGNORE;
        int result = instance.getTargetID();
        assertEquals("The Message Target ID was changed from it default -1.",expResult, result);
    }

    /**
     * Test of setSender method, of class Message.
     */
    @Test
    public void testSetSender() {
        System.out.println("setSender");
        String Sender = "This is a Sender";
        Message instance = new MessageImpl();
        instance.setSender(Sender);
        assertEquals("The Message sender did not get set.", instance.getSender(), Sender);
    }

    /**
     * Test of setUserID method, of class Message.
     */
    @Test
    public void testSetUserID() {
        System.out.println("setUserID");
        int userID = 55;
        Message instance = new MessageImpl();
        instance.setUserID(userID);
        assertEquals("The user ID did not get set.", instance.getUserID(), userID);
    }
    /**
     * Test of setTimestamp method, of class Message.
     */
    @Test
    public void testSetTimestamp() {
        System.out.println("setTimestamp");
        String timestamp = "2014/08/30 17:26:00";
        Message instance = new MessageImpl();
        instance.setTimestamp(timestamp);
        assertEquals("The timestamp did not get set.", instance.getTimestamp(), timestamp);
    }

    /**
     * Test of setTargetID method, of class Message.
     */
    @Test
    public void testSetTargetID() {
        System.out.println("setTargetID");
        int targetID = 67;
        Message instance = new MessageImpl();
        instance.setTargetID(targetID);
        assertEquals("The target ID did not get set.", instance.getTargetID(), targetID);
    }

    /**
     * Test of handle method, of class Message.
     */
    @Test
    public void testHandle() {
        System.out.println("handle");
        Message instance = new MessageImpl();
        Message.MessageType result = instance.handle();
        assertNull("The handle should be null since it is abstract.", result);
    }

    public class MessageImpl extends Message {

        public String getMessage() {
            return null;
        }

        public MessageType handle() {
            return null;
        }
    }
    
}
