/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

/**
 *
 * @author Lecton
 */
public class AudioStream extends Message {
    
    public long count = -1;
    public int bufferSize = 0;
    public byte[] buffer = new byte[bufferSize];
    
    /**
     * Constructor which initializes and creates the audio stream that transfers
     * the chosen audio to its location.
     * @param Sender - the name of the sender that is streaming the audio.
     * @param ID - the ID of the sender that is streaming the audio.
     * @param count - a counter.
     */
    public AudioStream(int ID, long count) {
        this.ID = ID;
        this.count = count;
    }    
    
    /**
     * Constructor which initializes and creates the audio stream that transfers
     * the chosen audio to its location, when there exists a cloned audio stream. 
     * @param clone - an audio stream object that we wish to clone in this one.
     */
    public AudioStream(AudioStream clone) {
        this.ID = clone.ID;
        this.to = clone.to;
        this.count = clone.count+1;
    }
    
    /**
     * A function to retrieve the message and all associated information such as 
     * the message type (audio), the ID, the sender and its buffer size.
     * @return 
     */
    @Override
    public String getMessage() {
        String result ="Audio Message {\n";
        result +="\tID: "+ID+"\n";
        result +="\tBuffer Size: "+bufferSize+"\n";
        result +="}";
        
        return result;
    }
}
