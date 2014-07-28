//
//
//import Messages.Message;
//
///**
// *
// * @author Bernhard
// */
//public class AudioProperty extends Message {
//    int type =0; //1 on  -  0 off
//    String streamID ="";
//    int[] allowed =null;
//
//    public AudioProperty(int ID, int to, String streamID, int type) {
//        this.ID =ID;
//        this.to =to;
//        this.streamID =streamID;
//        this.type =type;
//    }
//
//    @Override
//    public String getMessage() {
//        return "Audio Property";
//    }
//
//    public void setAllowed(int[] allowed) {
//        this.allowed =allowed;
//    }
//
//    public String getStreamID() {
//        return streamID;
//    }
//    
//    public int getType() {
//        return type;
//    }
//    
//    private boolean contained(int ID) {
//        if (allowed == null) return false;
//        
//        for (int i=0; i<allowed.length; i++) {
//            if (allowed[i] == ID) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
