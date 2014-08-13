/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Messages.Media.StreamNotify;
import Utils.Log;
import java.util.ArrayList;

/**
 *
 * @author Bernhard
 */
public class StreamHandler {
    private static ArrayList<StreamProperties> streams =new ArrayList<StreamProperties>();
    
    public static void createStreamProperty(int userID, String StreamID) {
        StreamProperties sp =new StreamProperties(userID, StreamID);
        streams.add(sp);
    }
    
    public static boolean removeStreamProperty(int userID, String streamID) {
        StreamProperties sp =getStreamProperties(streamID);
        if (sp != null) {
            streams.remove(sp);
            Log.error(new StreamHandler(), "Implement notification of everyone on list");
            //notify everyone
            
            return true;
        }
        return false;
    }
    
    public static StreamProperties getStreamProperties(String StreamID) {
        for (StreamProperties sp: streams) {
            if (sp.compareID(StreamID)) {
                return sp;
            }
        }
        return null;
    }

    public static boolean respondStream(String streamID, int userID, boolean response) {
        StreamProperties sp =getStreamProperties(streamID);
        if (sp != null) {
            if (response) {
                sp.accept(userID);
            } else {
                sp.refuse(userID);
            }
            return true;
        }
        return false;
    }

    public static boolean updateStream(String streamID, int userID, int affectedUserID, int action) {
        StreamProperties sp =StreamHandler.getStreamProperties(streamID);
        if (sp != null) {
            if (action == 1) {
                sp.add(userID, affectedUserID);
            } else if (action == 0) {
                sp.remove(userID, affectedUserID);
            } else {
                return false;
            }
            
            return true;
        }
        return false;
    }
}
