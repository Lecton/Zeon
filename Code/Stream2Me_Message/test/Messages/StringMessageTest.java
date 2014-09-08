/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Messages;

import messages.Message;
import messages.StringMessage;
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
public class StringMessageTest {
    
    public StringMessageTest() {
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
     * Test of getMessage method, of class StringMessage.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        
        int userID =1;
        int targetID =2;
        String Message ="Message this is.";
        
        StringMessage instance = new StringMessage(userID, targetID, Message);
        assertEquals("Message did not get set.", Message, instance.getMessage());
    }

    /**
     * Test of handle method, of class StringMessage.
     */
    @Test
    public void testHandle() {
        System.out.println("handle");
        
        int userID =1;
        int targetID =2;
        String Message ="Message this is.";
        
        StringMessage instance = new StringMessage(userID, targetID, Message);
        assertEquals("Messagt type is incorrect.", messages.Message.MessageType.string, instance.handle());
    }
}