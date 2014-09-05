/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;
import userInterface.generalUI.videoMessages.VideoManager;

/**
 *
 * @author Zen
 */
public class main {
    VideoManager vPlayer;
    
    public main() {
        vPlayer =new VideoManager();
    }
    
    private String getImage(String filename) throws IOException {
        return messageUtils.ImageUtils.encodeToString(ImageIO.read(new File(filename)), "png");
    }
    
    private String getID() {
        int random =(int)(Math.random()*10.0);
        return "something_vs_"+random;
    }
    
    public void simulate() throws IOException {
        String id =getID();
        System.out.println(id+" gets a new Image");
        vPlayer.write(id, getImage("test.jpg"));
        id =getID();
        System.out.println(id+" gets a new Image");
        vPlayer.write(id, getImage("test.jpg"));
        id =getID();
        System.out.println(id+" gets a new Image");
        vPlayer.write(id, getImage("test.jpg"));
        id =getID();
        System.out.println(id+" gets a new Image");
        vPlayer.write(id, getImage("test.jpg"));
        id =getID();
        System.out.println(id+" gets a new Image");
        vPlayer.write(id, getImage("test.jpg"));
        id =getID();
        System.out.println(id+" gets a new Image");
        vPlayer.write(id, getImage("test.jpg"));
        id =getID();
        System.out.println(id+" gets a new Image");
        vPlayer.write(id, getImage("test.jpg"));
        id =getID();
        System.out.println(id+" gets a new Image");
        vPlayer.write(id, getImage("test.jpg"));
    }
    
    public static void main(String[] args) {
        main m =new main();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            
        }
        System.out.println("Starting simulator");
        
        try {
            m.simulate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
