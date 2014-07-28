/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import Server.RelayServer;

/**
 *
 * @author Bernhard
 */
public class main {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            RelayServer rs = new RelayServer(2014);
            rs.start();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
