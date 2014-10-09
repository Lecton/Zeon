/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc.model.connection;

/**
 *
 * @author Bernhard
 */
public class Entries {
    private String IP;
    private int PORT;
    private String name;

    public Entries(String IP, int PORT, String name) {
        this.IP = IP;
        this.PORT = PORT;
        this.name =name;
    }

    public String getIP() {
        return IP;
    }

    public int getPORT() {
        return PORT;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return IP+"::"+PORT+"::"+name;
    }
}
