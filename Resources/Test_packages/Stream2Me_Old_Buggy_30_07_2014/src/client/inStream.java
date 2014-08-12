package client;

import Messages.*;
import client.GUI.GUI;
import java.io.IOException;

public class inStream implements Runnable {
    private Connection con = null;
    private GUI userInterface =null;

    public inStream(GUI userInterface) {
        this.userInterface =userInterface;
    }
    
    public void setInputStream(Connection con) {
        this.con =con;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Message m =con.read();
                MessageHandler.handle(userInterface, m);
                
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.err.println("RUN - IOException");
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
                System.err.println("RUN - ClassNotFoundException");
            }
        }
    }
}