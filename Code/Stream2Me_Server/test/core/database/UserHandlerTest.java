/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.database;

import biz.source_code.base64Coder.Base64Coder;
import channel.ClientChannel;
import core.database.objects.BaseUser;
import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;
import messages.Message;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateProfileMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LoginMessage;
import messages.userConnection.NewUserMessage;
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
public class UserHandlerTest {
    
    public UserHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Database.INSTANCE.connect();
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
     * Test of userLogin method, of class UserHandler.
     */
    @Test
    public void testUserLogin() {
        System.out.println("userLogin");
        Channel ch = null;
        LoginMessage message = new LoginMessage("Bernhard", Base64Coder.encodeString("cos301"));
        GreetingMessage expResult = null;
        GreetingMessage result = UserHandler.userLogin(ch, message);
        assertTrue("Login failed", result.isSuccessful());
    }

    /**
     * Test of getUser method, of class UserHandler.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String userID = "";
        BaseUser expResult = null;
        BaseUser result = UserHandler.getUser(userID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logoff method, of class UserHandler.
     */
    @Test
    public void testLogoff() {
        System.out.println("logoff");
        String userID = "";
        String connectionID = "";
        UserHandler.logoff(userID, connectionID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAvatar method, of class UserHandler.
     */
    @Test
    public void testUpdateAvatar() {
        System.out.println("updateAvatar");
        UpdateAvatarMessage msg = null;
        Message expResult = null;
        Message result = UserHandler.updateAvatar(msg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateProfile method, of class UserHandler.
     */
    @Test
    public void testUpdateProfile() {
        System.out.println("updateProfile");
        UpdateProfileMessage msg = null;
        Message expResult = null;
        Message result = UserHandler.updateProfile(msg);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGroupUsers method, of class UserHandler.
     */
    @Test
    public void testGetGroupUsers() {
        System.out.println("getGroupUsers");
        String userID = "";
        String[] expResult = null;
        String[] result = UserHandler.getGroupUsers(userID);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNewUserMessage method, of class UserHandler.
     */
    @Test
    public void testGetNewUserMessage() {
        System.out.println("getNewUserMessage");
        String userID = "";
        String targetID = "";
        NewUserMessage expResult = null;
        NewUserMessage result = UserHandler.getNewUserMessage(userID, targetID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateGroups method, of class UserHandler.
     */
    @Test
    public void testGenerateGroups() {
        System.out.println("generateGroups");
        UserHandler.generateGroups();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGroupID method, of class UserHandler.
     */
    @Test
    public void testGetGroupID() {
        System.out.println("getGroupID");
        String userID = "";
        String expResult = "";
        String result = UserHandler.getGroupID(userID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnectionID method, of class UserHandler.
     */
    @Test
    public void testGetConnectionID() {
        System.out.println("getConnectionID");
        String userID = "";
        String expResult = "";
        String result = UserHandler.getConnectionID(userID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
