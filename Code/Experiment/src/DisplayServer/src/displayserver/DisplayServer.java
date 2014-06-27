package displayserver;

import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.ImageIcon;

public class DisplayServer extends Thread {
    ServerSocket ss = null;
    ServerSocket admin = null;
    Vector<ClientThread> clientList =new Vector<ClientThread>();
    long sleepInterval = 125;

    public DisplayServer(long interval) throws Exception{
        ss = new ServerSocket(2020);
        admin = new ServerSocket(2021);
        sleepInterval = interval;
        this.setPriority(MIN_PRIORITY);
        this.start();
        startServer();
    }

    private void startServer() {
        System.out.println("Starts listening for clients");
        ClientThread client = null;
        while(true) {
            try {
                client = new ClientThread(ss.accept());
                clientList.addElement(client);
                client.start();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        String localHost = "127.0.0.1";
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch(Exception ex) {}
        
        while(true) {
            try {
                Socket ad = admin.accept(); 
                if(ad.getInetAddress().getHostAddress().equals(localHost) &&
                ad.getInputStream().read() == 'X') {
                    ss.close();
                    int clientListSize=clientList.size();
                    for(int i=0; i<clientListSize; i++)
                        clientList.get(i).client.close();
                    System.exit(0);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void removeMe(Socket s) {
        clientList.removeElement(s);
    }

    class ClientThread extends Thread {
        Socket client = null;
        ObjectOutputStream os = null;
        InputStream is = null;
        
        public ClientThread(Socket s) {
            client = s;
            try {
                os = new ObjectOutputStream(s.getOutputStream());
                is = s.getInputStream();
                System.out.println("Client from "+s.getInetAddress().getHostAddress()+" connected");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            BufferedImage img = null;
            Robot r = null;
            try {
                r = new Robot();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rect = new Rectangle(0,0,size.width, size.height);
            ImageIcon icon = null;

            while(true) {
                try {
                    System.gc();
                    img = r.createScreenCapture(rect);
                    icon = new ImageIcon(img);
                    os.writeObject(icon);
                    os.flush();
                    icon = null;
                    System.gc();
                    try {
                        Thread.currentThread().sleep(sleepInterval);
                    } catch(Exception e) {}
                } catch(Exception ex) { 
                    closeAll();
                    break;
                }
            } 
        }

        private void closeAll() {
            DisplayServer.this.removeMe(client);
            try {
                os.close();
                is.close();
                client.close();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String arg[]) throws Exception {
        long interval = 80;
        if(arg.length == 1)
            interval = Long.parseLong(arg[0]);
        new DisplayServer(interval);
    }
}