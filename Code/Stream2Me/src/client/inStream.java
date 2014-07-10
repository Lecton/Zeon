/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import Messages.*;
import client.GUI.GUI;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Lecton
 * @author Bernhard
 * @author Zenadia
 */
public class inStream implements Runnable {
    private ObjectInputStream ois = null;
    private GUI userInterface =null;

    public inStream(GUI userInterface) {
        this.userInterface =userInterface;
    }
    
    public void setInputStream(ObjectInputStream ois) {
        this.ois =ois;
    }

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

    private void greetUserMessage(Message m) {
        Greeting g =(Greeting) m;
        userInterface.ID =g.ID;
        Colleague cc =new Colleague();
        
        cc.ID =g.ID;
        cc.name =userInterface.name;
        cc.localName =userInterface.name;
        userInterface.ContactPane.addContact(cc);

        for (int i=0; i<g.size; i++) {
            Colleague c =new Colleague();
            c.ID =g.colleagueIDs[i];
            c.name =g.colleagueNames[i];
            c.localName =c.name;
            userInterface.ContactPane.addContact(c);
        }
    }
    
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

    private void removeUserMessage(Message m) {
        RemoveUser ru =(RemoveUser)m;
        
        userInterface.ContactPane.removeContact(ru.ID);
        System.out.println("Removed User");
    }

    private void updateUserMessage(Message m) {
        UpdateUser uu =(UpdateUser) m;
        userInterface.ContactPane.updateUser(uu);
    }

    private void stringUserMessage(Message m) {
        StringMessage sm =(StringMessage)m;
//        System.out.println("message received: " + sm.getMessage());
        
//        userInterface.setChatMessage(sm.getMessage());
        userInterface.ContactPane.acceptMessage(sm);
//        int index =-1;
//        if((index =find(sm.ID)) != -1){
//            userInterface.ContactPane.colleagues.get(index).content += m.getMessage() + "\n";
//            userInterface.updateGUI(colleagues.get(index), GUI.Action.UPDATE);
//        }
    }
    
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

    
    private AudioFormat getFormat() {
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;

        return new AudioFormat(sampleRate, 
                sampleSizeInBits, channels, signed, bigEndian);
    }    
}