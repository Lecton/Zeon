package MediaStreaming.Audio;

import Messages.AudioStream;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.sound.sampled.*;

public class AudioCapture 
{
  public boolean running;
  
  AudioStream as;
  TargetDataLine line;
  Socket socket = null;
  ObjectOutputStream oos = null;
  String HOST;
  int PORT;
  int ID;
  public String name;
  DataLine.Info info;
  
  public AudioCapture(ObjectOutputStream oos,String _name,int _ID)
  {
        try 
        {
            this.name = _name;
            this.ID = _ID;
            as = new AudioStream(this.name, this.ID, -1);
            as.to =-1;
            this.oos = oos;
            info = new DataLine.Info(TargetDataLine.class, getFormat());
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(getFormat());
            line.start();

        }
        catch (LineUnavailableException ex){
            ex.printStackTrace();
        }
  }

  public void captureAudio() throws UnknownHostException, IOException 
  {
        Runnable runner = new Runnable()
        {
            int bufferSize = (int)getFormat().getSampleRate() * getFormat().getFrameSize();
//            byte buffer[] = new byte[bufferSize];
            int counter = 0;
            public void run()
            {
                running = true;
                try
                {
                  while (running) 
                  {
                        AudioStream as =new AudioStream(name, ID, counter);
                        as.buffer =new byte[bufferSize];
                        int count = line.read(as.buffer, 0, as.buffer.length);
                        if (count > 0)
                        {
                            oos.writeObject(as);
                            oos.flush();
                            counter++;
                        }
                  }
                }
                catch(IOException ex)
                {
                  ex.printStackTrace();
                }
            }
      };
        
      Thread captureThread = new Thread(runner);
      captureThread.start();
  }


  private AudioFormat getFormat()
  {
    float sampleRate = 8000;
    int sampleSizeInBits = 8;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;
    
    return new AudioFormat(sampleRate,sampleSizeInBits, channels, signed, bigEndian);
  }

}