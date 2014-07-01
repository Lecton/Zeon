import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
  DataOutputStream dOut = null;
  
  public AudioCapture()
  {
//    Container content = frame.getContentPane();




    
    
    try {
        String serverHostname = new String("127.0.0.1");
        PrintWriter output = null;
        BufferedReader input = null;
            socket = new Socket(serverHostname, 10007);
        dOut = new DataOutputStream(socket.getOutputStream());
        
        DataLine.Info info = new DataLine.Info(
                                  TargetDataLine.class, getFormat());
        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(getFormat());
        line.start();
    } catch (UnknownHostException ex) {
        Logger.getLogger(AudioCapture.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(AudioCapture.class.getName()).log(Level.SEVERE, null, ex);
    } catch (LineUnavailableException e){
      System.err.println("Line unavailable: " + e);
      System.exit(-2);
    }


  }

  protected void captureAudio() throws UnknownHostException, IOException 
  {
        Runnable runner = new Runnable(){
        int bufferSize = (int)getFormat().getSampleRate() * getFormat().getFrameSize();
        byte buffer[] = new byte[bufferSize];
 
        public void run(){
            out = new ByteArrayOutputStream();
            running = true;
            
            try
            {
              while (running) 
              {
                int count = line.read(buffer, 0, buffer.length);
                if (count > 0){
                  out.write(buffer, 0, count);
                  dOut.writeInt(count);
                  dOut.write(buffer); 
                }
              }
              out.close();
            }catch(IOException e){
              System.err.println("I/O problems: " + e);
              System.exit(-1);
            }
        }
      };
      Thread captureThread = new Thread(runner);
      captureThread.start();
  }


  private AudioFormat getFormat(){
    float sampleRate = 8000;
    int sampleSizeInBits = 8;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;
    
    return new AudioFormat(sampleRate, 
        sampleSizeInBits, channels, signed, bigEndian);
  }

}