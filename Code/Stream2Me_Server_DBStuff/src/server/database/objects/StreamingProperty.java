package server.database.objects;
import java.util.ArrayList;

/**
 *
 * @author Zenadia
 */
public class StreamingProperty{
    private int ownerID;
    private String streamID;
    private ArrayList<String> acceptedID;
    private ArrayList<String> allowedID;
    
    public StreamingProperty(int ownerID, String streamID){
        this.ownerID = ownerID;
        this.streamID = streamID;
        this.allowedID = new ArrayList<String>();
        this.acceptedID = new ArrayList<String>();        
    }
    
    public boolean compareID(String StreamID){
        return streamID.equals(StreamID);
    }
    
    public boolean accept(int userID){
        for (String a: allowedID){
            int allowed = Integer.parseInt(a);
            if (allowed == userID){
                acceptedID.add("" + userID);
                return true;
            }
        }
        return false;
    }
    
    public boolean refuse(int userID){
        for (String a: acceptedID){
            int accepted = Integer.parseInt(a);
            if (accepted == userID){
                acceptedID.remove("" + accepted);
                return true;
            }
        }
        return false;
    }
    
    public boolean remove(int ownerID, int userID){
        refuse(userID);
        for (String a: allowedID){
            int allowed = Integer.parseInt(a);
            if (allowed == userID){
                allowedID.remove("" + allowed);
                return true;
            }
        }
        return false;
    }
    
    public void add(int ownerID, int userID){
        if (ownerID == this.ownerID){
            allowedID.add("" + userID);
        }
    }
    
    public int[] getTargets(){
        int[] result = new int[acceptedID.size()];
        for (int i = 0; i < result.length; i++){
            result[i] = Integer.parseInt(acceptedID.get(i));
        }
        return result;
    }
    
}