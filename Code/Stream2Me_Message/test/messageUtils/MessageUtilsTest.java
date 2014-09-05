/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messageUtils;

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
public class MessageUtilsTest {
    
    public MessageUtilsTest() {
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
     * Test of generateStreamID method, of class MessageUtils.
     */
    @Test
    public void testGenerateStreamID() {
        System.out.println("generateStreamID");
        String pre = "streamID";
        MessageUtils.StreamType type = MessageUtils.StreamType.VIDEO;
        String expResult = "streamID_vs_0";
        String result = MessageUtils.generateStreamID(pre, type);
        assertEquals("The resulting video stream ID did not generate correctly ",expResult, result);
        
        pre = "streamID";
        type = MessageUtils.StreamType.AUDIO;
        expResult = "streamID_as_1";
        result = MessageUtils.generateStreamID(pre, type);
        assertEquals("The resulting did not generate correctly",expResult, result);
        
        pre = "streamID";
        type = MessageUtils.StreamType.MEDIA;
        expResult = "streamID_ms_2";
        result = MessageUtils.generateStreamID(pre, type);
        assertEquals("The resulting did not generate correctly",expResult, result);
    }

    /**
     * Test of getStreamType method, of class MessageUtils.
     */
    @Test
    public void testGetStreamType() {
        System.out.println("getStreamType");
        String streamID = "streamID_vs_0";
        MessageUtils.StreamType expResult = MessageUtils.StreamType.VIDEO;
        MessageUtils.StreamType result = MessageUtils.getStreamType(streamID);
        assertEquals("Video stream ID did not decode correctly",expResult, result);
        
        streamID = "streamID_as_0";
        expResult = MessageUtils.StreamType.AUDIO;
        result = MessageUtils.getStreamType(streamID);
        assertEquals("Audio stream ID did not decode correctly",expResult, result);
        
        streamID = "streamID_ms_0";
        expResult = MessageUtils.StreamType.MEDIA;
        result = MessageUtils.getStreamType(streamID);
        assertEquals("Media stream ID did not decode correctly",expResult, result);
    }
    
}
