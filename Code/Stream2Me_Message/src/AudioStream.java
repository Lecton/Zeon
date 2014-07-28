//
//
//import Messages.Message;
//
///**
// *
// * @author Bernhard
// * @author Lecton
// */
//public class AudioStream extends Message {
//    private int bufferSize = 0;
//    public byte[] buffer = new byte[bufferSize];
//    private String streamID ="";
//    
//    /**
//     * Constructor which initializes and creates the audio stream that transfers
//     * the chosen audio to its location.
//     * @param Sender - the name of the sender that is streaming the audio.
//     * @param ID - the ID of the sender that is streaming the audio.
//     */
//    public AudioStream(String Sender, int ID) {
//        this.Sender = Sender;
//        this.ID = ID;
//        this.to =-1;
//    }
//    
//    /**
//     * Constructor which initializes and creates the audio stream that transfers
//     * the chosen audio to its location, when there exists a cloned audio stream. 
//     * @param clone - an audio stream object that we wish to clone in this one.
//     */
//    public AudioStream(AudioStream clone) {
//        this.Sender = clone.Sender;
//        this.ID = clone.ID;
//        this.to = clone.to;
//        this.streamID = clone.streamID;
//    }
//
//    public void setStreamID(String streamID) {
//        this.streamID = streamID;
//    }
//
//    public String getStreamID() {
//        return streamID;
//    }
//    
//    /**
//     * A function to retrieve the message and all associated information such as 
//     * the message type (audio), the ID, the sender and its buffer size.
//     * @return 
//     */
//    @Override
//    public String getMessage() {
//        String result ="Audio Message {\n";
//        result +="\tSender: "+Sender+"\n";
//        result +="\tID: "+ID+"\n";
//        result +="\tBuffer Size: "+bufferSize+"\n";
//        result +="}";
//        
//        return result;
//    }
//}
