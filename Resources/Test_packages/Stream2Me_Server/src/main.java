/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import Server.RelayServer;
import java.util.Scanner;

/**
 *
 * @author Bernhard
 */
public class main {
    /**
     * 
     * @param args
     */
    static RelayServer rs;
    
    public static void main(String[] args) throws InterruptedException {
        (new Thread(new listener())).start();
        
        try{
            rs = new RelayServer(2014);
            rs.start();
        } 
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static class listener implements Runnable {
        @Override
        public void run() {
            Scanner in = new Scanner(System.in);
            boolean running = true;
            while (running) {
                String line = in.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    running = false;
                }
            }
            rs.stop();
        }
    }
}
