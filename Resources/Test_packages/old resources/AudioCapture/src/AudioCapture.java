import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

public class AudioCapture 
{
  protected boolean running;
  ByteArrayOutputStream out;
  TargetDataLine line;
  Socket socket = null;
  DataOutputStream dos = null;
  String HOST = "127.0.0.1";
  DataLine.Info info;
  
  public AudioCapture()
  {
        try 
        {
            socket = new Socket(HOST, 10007);
            dos = new DataOutputStream(socket.getOutputStream());
            info = new DataLine.Info(TargetDataLine.class, getFormat());
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(getFormat());
            line.start();

        }
        catch (IOException | LineUnavailableException ex){
            ex.printStackTrace();
        }
  }

  protected void captureAudio() throws UnknownHostException, IOException 
  {
        Runnable runner = new Runnable()
        {
            int bufferSize = (int)getFormat().getSampleRate() * getFormat().getFrameSize();
            byte buffer[] = new byte[bufferSize];

            public void run()
            {
                out = new ByteArrayOutputStream();
                running = true;
                try
                {
                  while (running) 
                  {
                        int count = line.read(buffer, 0, buffer.length);
                        if (count > 0)
                        {
                          out.write(buffer, 0, count);
                          dos.writeInt(count);
                          dos.write(buffer); 
                        }
                  }
                  out.close();
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
    
    return new AudioFormat(sampleRate, 
                                    sampleSizeInBits, channels, signed, bigEndian);
  }

}