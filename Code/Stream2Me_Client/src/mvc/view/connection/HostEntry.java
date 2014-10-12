/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.view.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Bernhard
 */
public class HostEntry {
    String IP;
    int PORT;
    String name;
    boolean active;

    public HostEntry(String IP, int PORT, String name) {
        this.IP = IP;
        this.PORT = PORT;
        this.name =name;
    }

    public boolean isActive() {
        this.active = ping(IP, PORT);
        return active;
    }
    
    private static boolean ping(String ipAddress, int PORT) {
        Socket socket = null;
        boolean reachable = false;
        try {
            socket = new Socket(ipAddress, PORT);
            reachable = true;
        } catch (IOException ex) {
        } finally {            
            if (socket != null) try { socket.close(); } catch(IOException e) {}
        }
        return reachable;
    }

    public Vector getData() {
        Vector v =new Vector();
        v.add(name);
        v.add(IP+":"+PORT);
        Delete d =new Delete();
        v.add(d);
        return v;
    }
}