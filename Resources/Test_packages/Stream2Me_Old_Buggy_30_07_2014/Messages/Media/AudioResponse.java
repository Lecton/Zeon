package Messages.Media;

import Messages.Message;
import client.GUI.GUI;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import server.clientConnection;

/**
 *
 * @author Bernhard
 */
public class AudioResponse extends Message {
    String streamID;
    boolean accept =false;

    public AudioResponse(int ID, String streamID, boolean accept) {
        this.ID =ID;
        this.streamID = streamID;
        this.accept =accept;
    }
    
    
    @Override
    public String getMessage() {
        return "Audio Response to ID: "+streamID+" with "+accept;
    }

    @Override
    public void handle(GUI userInterface) {
    }

    @Override
    public Message repackage(clientConnection cc) {
        System.out.println(getMessage());
        cc.getStreamProperties(streamID).accept(ID);
        return this;
    }

    @Override
    public void relay(ConcurrentLinkedQueue<clientConnection> clients, clientConnection cc) throws IOException {
    }
}
