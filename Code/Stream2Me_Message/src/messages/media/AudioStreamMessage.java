package messages.media;

import messages.Message;

/**
 *
 * @author Bernhard
 * @author Lecton
 */
public class AudioStreamMessage extends Message {
    public byte[] buffer = new byte[0];
    private String streamID ="";
    
    public AudioStreamMessage(int userID, int targetID, String streamID) {
        this.userID = userID;
        this.targetID =targetID;
        this.streamID =streamID;
    }
    
    /**
     * Constructor which initializes and creates the audio stream that transfers
     * the chosen audio to its location, when there exists a cloned audio stream. 
     * @param clone - an audio stream object that we wish to clone in this one.
     */
    public AudioStreamMessage(AudioStreamMessage clone) {
        this.userID = clone.userID;
        this.targetID = clone.targetID;
        this.streamID = clone.streamID;
    }

    public String getStreamID() {
        return streamID;
    }

    public byte[] getBuffer() {
        return buffer;
    }
    
    /**
     * A function to retrieve the message and all associated information such as 
     * the message type (audio), the ID, the sender and its buffer size.
     * @return 
     */
    @Override
    public String getMessage() {
        String result ="Audio Message {\n";
        result +="\tUser ID: "+userID+"\n";
        result +="\tStream ID: "+streamID+"\n";
        result +="\tBuffer Size: "+buffer.length+"\n";
        result +="}";
        
        return result;
    }

    @Override
    public MessageType handle() {
        return MessageType.auido;
    }
}
