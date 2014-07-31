import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {

        String serverHostname = new String ("127.0.0.1");

        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        socket = new Socket(serverHostname, 10007);

        byte[] message = new byte[] {1, 6, 3};
        while (true) {
        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

        dOut.writeInt(message.length); // write length of the message
        dOut.write(message);           // write the message                
        Thread.sleep(10000);
        }
    }
}
