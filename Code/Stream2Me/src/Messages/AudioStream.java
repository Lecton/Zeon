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
    
    public long count =-1;
    public int bufferSize =0;
    public byte[] buffer =new byte[bufferSize];
    
    public AudioStream(String Sender, int ID, long count) {
        this.Sender = Sender;
        this.ID =ID;
        this.count = count;
    }    
    
    public AudioStream(AudioStream clone) {
        this.Sender = clone.Sender;
        this.ID =clone.ID;
        this.to =clone.to;
        this.count = clone.count+1;
    }
    
    @Override
    public String getMessage() {
        String result ="Audio Message {\n";
        result +="\tSender: "+Sender+"\n";
        result +="\tID: "+ID+"\n";
        result +="\tBuffer Size: "+bufferSize+"\n";
        result +="}";
        
        return result;
    }
}
