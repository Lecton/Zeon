package server.database.objects;

import com.db4o.ObjectSet;
import server.database.Database;
import utils.Log;

/**
 *
 * @author Zenadia
 */
public class StreamingHandler {
    public static Database db = null;
    
    public static void createStreamProperty(int userID, String StreamID){
        StreamingProperty sp = new StreamingProperty(userID, StreamID);
        db.addStreamProperties(sp);
    }
    
    public static boolean removeStreamProperty(int userID, String streamID){
        StreamingProperty sp = getStreamProperties(streamID);
        if (sp != null){
            db.removeStreamProperties(sp);
            Log.error(new StreamingHandler(), "Implement notification of everyone on list");            
            return true;
        }
        return false;
    }
    
    public static StreamingProperty getStreamProperties(String StreamID){
        ObjectSet<StreamingProperty> streams = db.getStreamProperty(StreamID);
        for (StreamingProperty sp: streams){
            if (sp.compareID(StreamID)){
                return sp;
            }
        }
        return null;
    }

    public static boolean respondStream(String streamID, int userID, boolean response){
        StreamingProperty sp = getStreamProperties(streamID);
        if (sp != null){
            if (response){
                sp.accept(userID);
            } 
            else{
                sp.refuse(userID);
            }
            return true;
        }
        return false;
    }

    public static boolean updateStream(String streamID, int userID, int affectedUserID, int action){
        StreamingProperty sp = getStreamProperties(streamID);
        if (sp != null){
            if (action == 1){
                sp.add(userID, affectedUserID);
            } 
            else if (action == 0){
                sp.remove(userID, affectedUserID);
            } 
            else{
                return false;
            }            
            return true;
        }
        return false;
    }
}