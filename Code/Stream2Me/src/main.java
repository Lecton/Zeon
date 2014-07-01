/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ClientOld.Client;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.stream.events.StartDocument;
import server.RelayServer;

/**
 *
 * @author Bernhard
 */
public class main {
    /**
     * @param args the comm and line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            RelayServer rs =new RelayServer(2014);
            rs.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
