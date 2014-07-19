package MediaStreaming.Audio;

import Messages.AudioStream;
import client.Connection;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.sound.sampled.*;

/**
 * 
 * @author Lecton
 * @author Zenadia
 * @author Bernhard
 */
public class AudioCapture 
{
  public boolean running;
  
  AudioStream as;
  TargetDataLine line;
  Socket socket = null;
  Connection con = null;
  String HOST;
  int PORT;
  int ID;
  public String name;
  DataLine.Info info;
  
  /**
   * Constructor to initialize the audio stream capture object.
   * Streaming audio is identified as a set of data lines and the format is 
   * determined once the first line is retrieved.
   * @param oos - the object output stream.
   * @param _name - the name of this specific audio stream.
   * @param _ID - the ID of this specific audio stream.
   */
  public AudioCapture(Connection con,String _name,int _ID)
  {
        try{
            this.name = _name;
            this.ID = _ID;
            as = new AudioStream(this.name, this.ID, -1);
            as.to = -1;
            this.con = con;
            info = new DataLine.Info(TargetDataLine.class, getFormat());
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(getFormat());
            line.start();

        }
        catch (LineUnavailableException ex){
            ex.printStackTrace();
        }
  }

  /**
   * Captures the audio by reading the data in the object output stream.
   * @throws UnknownHostException
   * @throws IOException 
   */
  public void start() throws UnknownHostException, IOException{
        Runnable runner = new Runnable(){
            int bufferSize = (int)getFormat().getSampleRate() * getFormat().getFrameSize();
            int counter = 0;
            public void run(){
                running = true;
                try{
                  while (running){
                        AudioStream as = new AudioStream(name, ID, counter);
                        as.buffer = new byte[bufferSize];
                        int count = line.read(as.buffer, 0, as.buffer.length);
                        if (count > 0){
                            con.write(as);
                            counter++;
                        }
                  }
                }
                catch(IOException ex){
                  ex.printStackTrace();
                }
            }
      };
        
      Thread captureThread = new Thread(runner);
      captureThread.start();
  }

  public void stop(){
      this.running = false;
  }
  
/**
 * Determines the format of the audio based on its sample rate, sample size, etc.
 * @return 
 */
  private AudioFormat getFormat(){
    float sampleRate = 8000;
    int sampleSizeInBits = 8;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;
    
    return new AudioFormat(sampleRate,sampleSizeInBits, channels, signed, bigEndian);
  }

}