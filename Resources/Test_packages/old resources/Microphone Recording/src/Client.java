import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Zen
 */
public class Client {
   int PORT;


    public Client() throws IOException {
        this.PORT = 8000;
           ServerSocket server = new ServerSocket(PORT);
           Socket client  = server.accept();
           
           OutputStream is = client.getOutputStream();
           DataOutputStream os = new DataOutputStream(is);
           
           os.writeUTF("Hi there");
           
           os.close();
           server.close();
           client.close();
    }
}
