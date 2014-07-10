/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ClientOld;

import Messages.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Defines a client with a port number, connection socket, a user interface, ID,
 * a name and its own input and output streams.
 * @author Bernhard
 * @author Lecton
 */

public class Client {
    /**
     * 
     */
    private int PORT = 2000;
    private Socket connection = null;
    private ObjectOutputStream os = null;
    private inStream is = null;
    private UI userInterface = null;
    
    public int ID = -1;
    public String name = "User";
    
    /**
     * 
     * @throws UnknownHostException
     * @throws IOException 
     * 
     * Constructs the new client if no additional information is provided.
     * Assigns the new client a default name "User", and port number 2000.
     */
    public Client() throws UnknownHostException, IOException {
        setup(PORT, name);
    }
    
    /**
     * Constructs the client when a port number is specified.
     * @param port - the port number specified for the new client to connect on.
     * @throws UnknownHostException
     * @throws IOException 
     */
    public Client(int port) throws UnknownHostException, IOException {
        setup(port, name);
    }
    /**
     * Constructs the client when a port number and name is specified.
     * @param port - the port number specified for the new client to connect on.
     * @param name - the name specified for the new client.
     * @throws UnknownHostException
     * @throws IOException  
     */
    public Client(int port, String name) throws UnknownHostException, IOException {
        setup(port, name);
    }
    
    /**
     * Constructs the new user interface, "userInterface".
     * @param port - the port number specified for the new client to connect on.
     * @param name - the name specified for the new client.
     * @throws UnknownHostException
     * @throws IOException      * 
     * 
     * Assigns the name and port number to the name and number specified.
     * Sets the connection by creating a new connection socket and assigning it
     * a default IP address and port number to connect on.
     * Assigns a new output stream the connection Socket's output stream.
     * Assigns a new input stream the connection Socket's input stream, 
     * along with the interface and the current client to have access to 
     * interface components to be able to update and communicate when activity 
     * on the UI occurs.
     * The object output stream os writes an object to the stream - 
     * clientInit (message that tells server details about the client when they 
     * log on).
     * flush() sends the message from the output stream.
     */
    private void setup(int port, String name) throws UnknownHostException, IOException {
        userInterface = new UI(name);
        this.PORT = port;
        this.connection = new Socket("127.0.0.1", port);
        this.name = name;
        System.out.println("I am setting up!");
        
        os = new ObjectOutputStream(connection.getOutputStream());
        
        is = new inStream(connection.getInputStream(), this, userInterface);
        System.out.println("Got streams");
        
        os.writeObject(new ClientInit(name));
        os.flush();
        
        System.out.println("I have finished setting up!");
    }
    
   /**
    * Starts the client.
    * Creates a scanner to retrieve input.
    * Creates a thread to run the input stream.
    * While the input stream is still running, the output stream writes an object
    * to the stream and sends messages fro the output stream.
    */
    public void start() {
        Scanner scan = new Scanner(System.in);
        Thread inputStreamThread = new Thread(is);
        inputStreamThread.start();
        
//        Thread outStreamThread =new Thread(os);
//        outStreamThread.start();
        
        while (true) {
            String line = scan.nextLine();
            try {
                os.writeObject(new StringMessage(line, ID));
                os.flush();
            } catch (IOException ex) {
                System.err.println("START - writeObject Error");
            }
        }
    }
    
    /**
     * 
     * @param args - not used.
     * 
     * Creates a client and starts it.
     */
    public static void main(String[] args){
        try {
            String name = JOptionPane.showInputDialog("Please enter your name.");
            System.out.println("My name is "+name+"!");
            
            Client client = (new Client(2014,name));
            client.start();
            
        } catch (IOException ex) {
            System.err.println("MAIN - start client error");
        }
    }
}