/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import Messages.*;
import client.GUI.GUI;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Lecton
 * @author Bernhard
 * @author Zenadia
 */
public class inStream implements Runnable {
    private ObjectInputStream ois = null;
    private GUI userInterface =null;

    /**
     * Constructor that initializes and creates an incoming stream and specifies 
     * it an interface.
     * @param userInterface 
     */
    public inStream(GUI userInterface) {
        this.userInterface =userInterface;
    }
    
    /**
     * Sets the object input stream for this specific incoming stream via a connection.
     * @param ois - the object input stream.
     */
    public void setInputStream(ObjectInputStream ois) {
        this.ois =ois;
    }

    /**
     * Runs the incoming stream via the connection, determines the type of message 
     * and performs the required action for each different message type.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Object o = ois.readObject();
                Message m =(Message)o;
                if (m instanceof Greeting) {
                    greetUserMessage(m);
                } else if (m instanceof NewUser) {
                    newUserMessage(m);
                } else if (m instanceof RemoveUser) {
                    removeUserMessage(m);
                } else if (m instanceof UpdateUser) {
                    updateUserMessage(m);
                } else if (m instanceof StringMessage) {
                    stringUserMessage(m);
                } else if (m instanceof AudioStream) {
                    audioStreamMessage(m);
                } else if (m instanceof VideoStream) {
                    videoStreamMessage(m);
                } else {
                    System.out.println("Message: "+m.getMessage());
                }
            } catch (IOException ioe) {
                System.err.println("RUN - IOException");
            } catch (ClassNotFoundException cnfe) {
                System.err.println("RUN - ClassNotFoundException");
            }
        }
    }

    /**
     * Updates the system and colleague information as the colleague is created,
     * and assigns the colleague a space in the interface, and sends a greeting
     * message to the new colleague/client.
     * @param m - the message.
     */
    private void greetUserMessage(Message m) {
        Greeting g = (Greeting) m;
        userInterface.ID = g.ID;
        Colleague cc = new Colleague();
        
        cc.ID = g.ID;
        cc.name = userInterface.name;
        cc.localName = userInterface.name;
        userInterface.ContactPane.addContact(cc);

        for (int i = 0; i < g.size; i++) {
            Colleague c = new Colleague();
            c.ID = g.colleagueIDs[i];
            c.name = g.colleagueNames[i];
            c.localName = c.name;
            userInterface.ContactPane.addContact(c);
        }
    }
    
    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user has been added and has connected.
     * @param m - the message.
     */
    private void newUserMessage(Message m) {
        NewUser um =(NewUser)m;
        
        if (um.ID != userInterface.ID) {
            Colleague tempColleague =new Colleague();
            tempColleague.ID =um.ID;
            tempColleague.name =um.name;
            tempColleague.localName =um.name;
            userInterface.ContactPane.addContact(tempColleague);
//            colleagues.add(tempColleague);
//            userInterface.updateGUI(tempColleague, GUI.Action.ADD);
        }
    }

    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user has been removed or is no longer active.
     * @param m - the message.
     */
    private void removeUserMessage(Message m) {
        RemoveUser ru =(RemoveUser)m;
        
        userInterface.ContactPane.removeContact(ru.ID);
        System.out.println("Removed User");
    }

    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user has had information changed.
     * @param m - the message.
     */
    private void updateUserMessage(Message m) {
        UpdateUser uu =(UpdateUser) m;
        userInterface.ContactPane.updateUser(uu);
    }

    /**
     * 
     * @param m - the message.
     */
    private void stringUserMessage(Message m) {
        StringMessage sm =(StringMessage)m;
        userInterface.ContactPane.acceptMessage(sm);
    }
    
    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user wishes to send messages that allow for the streaming 
     * of audio data.
     * @param m 
     */
    private void audioStreamMessage(Message m){
    try
    {
      AudioStream as =(AudioStream)m;
      byte audio[] = as.buffer;
      InputStream input =  new ByteArrayInputStream(audio);
      final AudioFormat format = getFormat();
      final AudioInputStream ais = new AudioInputStream(input, format, 
                                                audio.length / format.getFrameSize());
      
      DataLine.Info info = new DataLine.Info(
                                    SourceDataLine.class, format);
      
      final SourceDataLine line = (SourceDataLine)
                                    AudioSystem.getLine(info);
      
      line.open(format);
      line.start();

        Runnable runner = new Runnable()
        {
          int bufferSize = (int) format.getSampleRate() 
            * format.getFrameSize();
          byte buffer[] = new byte[bufferSize];

          public void run() {
            try {
              int count;
              while ((count = ais.read(
                  buffer, 0, buffer.length)) != -1) {
                if (count > 0) {
                  line.write(buffer, 0, count);
                }
              }
              line.drain();
              line.close();
            } catch (IOException e) {
              System.err.println("I/O problems: " + e);
              System.exit(-3);
            }
          }
        };
        
      Thread playThread = new Thread(runner);
      playThread.start();
      
    }
    catch(LineUnavailableException e)
    {
      System.err.println("Line unavailable: " + e);
      System.exit(-4);
    } 
  }

    /**
     * Determines and returns the format of an audio files to be streamed via the 
     * connection.
     * @return 
     */
    private AudioFormat getFormat() {
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;

        return new AudioFormat(sampleRate, 
                sampleSizeInBits, channels, signed, bigEndian);
    }
    
    /**
     * Sends a message to update the system and informing other colleagues
     * that a client/user wishes to send messages that allow for the streaming 
     * of video data.
     * @param m 
     */
    private void videoStreamMessage(Message m) {
        VideoStream vs =(VideoStream)m;
        
        BufferedImage img =decodeToImage(vs.img);
        if (img != null) {
            userInterface.imgBlock.setIcon(new ImageIcon(img));
            userInterface.imgBlock.updateUI();
        }
    }
    
    /**
     * Decodes an image string to be used in the streaming of video data.
     * @param imageString - the image defines as a string which needs decoding.
     * @return \
     */
    private BufferedImage decodeToImage(String imageString) {
        
        
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}