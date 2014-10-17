/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.controller.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mvc.controller.Control;
import mvc.model.connection.Entries;
import mvc.model.connection.connection;
import mvc.view.connection.HostEntry;

/**
 *
 * @author Bernhard
 */
public class ConnectionControl {
    private final static Logger LOGGER = Logger.getLogger(ConnectionControl.class.getName());

    public static ConnectionControl INSTANCE;
    
    private static boolean save =false;
    private static boolean changed =false;
    
    private connection model;

    public ConnectionControl() {
        model =new connection();
        readHostFile();
    }
    
    public List<HostEntry> getHostData() {
        List<HostEntry> result =new ArrayList<>();
        List<Entries> list =model.getHostData();
        for (Entries entry: list) {
            result.add(new HostEntry(entry.getIP(), entry.getPORT(), entry.getName()));
        }
        return result;
    }
    
    private void readHostFile() {
        try {
            File f =new File("host.conf");
            BufferedReader br =new BufferedReader(new FileReader(f));
            String line =null;
            while ((line =br.readLine()) != null) {
                String[] data =line.split("::");
                if (data.length == 3) {
                    try {
                        String IP =data[0];
                        String sPORT =data[1];
                        String Name =data[2];
                        int PORT =Integer.parseInt(sPORT);
                        model.addHostEntry(IP, PORT, Name);
                    } catch (NumberFormatException e) {}
                }
            }
        } catch (FileNotFoundException fnfe) {
            
        } catch (IOException ioe) {
            
        }
    }
    
    private void writeHostFile() {
        try {
            PrintWriter pw =new PrintWriter(new File("host.conf"));
            List<Entries> list =model.getHostData();
            for (Entries entry: list) {
                pw.write(entry.toString()+"\n");
            }
            pw.close();
            changed =false;
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Host file could not be written.", ex);
        }
    }

    public String getIP() {
        return model.getIP();
    }

    public int getPORT() {
        return model.getPORT();
    }

    public void removeHostDataRow(int row) {
        model.removeHostDataRow(row);
        changed =true;
    }

    public void connect(String IP_PORT, boolean save) {
        try {
            String[] data =IP_PORT.split(":");
            String ipAddress =data[0];
            int PORT =Integer.parseInt(data[1]);
            model.setConnection(ipAddress, PORT);
            Control.INSTANCE.makeConnection();
            Control.INSTANCE.initiate(0);
            ConnectionControl.save =save;
            
            if (changed) {
                writeHostFile();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Connection error. Please try again", 
                    "Connection error", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public boolean requiresSave() {
        return save;
    }

    public void setServerName(String serverName) {
        if (save) {
            save =false;
            model.addHostEntry(model.getIP(), model.getPORT(), serverName);
            writeHostFile();
        }
    }
}