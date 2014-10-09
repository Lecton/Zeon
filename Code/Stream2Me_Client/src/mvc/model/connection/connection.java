/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.connection;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bernhard
 */
public class connection {
    String IP;
    int PORT;
    
    List<Entries> hostData;

    public connection() {
        hostData =new ArrayList<>();
    }
    
    public void setConnection(String IP, int PORT) {
        this.IP =IP;
        this.PORT =PORT;
    }

    public List<Entries> getHostData() {
        return hostData;
    }

    public String getIP() {
        return IP;
    }

    public int getPORT() {
        return PORT;
    }

    public void addHostEntry(String IP, int PORT, String Name) {
        hostData.add(new Entries(IP, PORT, Name));
    }

    public void removeHostDataRow(int row) {
        hostData.remove(row);
    }
}