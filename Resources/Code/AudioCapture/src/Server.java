import java.net.*; 
import java.io.*; 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Server 
{ 
 public static void main(String[] args) throws IOException 
   { 
    ServerSocket serverSocket = null; 
    serverSocket = new ServerSocket(10007); 

    Socket socket = null; 
    System.out.println ("Waiting for connection.....");
    socket = serverSocket.accept();
    
    System.out.println ("Connection successful");

    
    //Bytes
    DataInputStream dIn = new DataInputStream(socket.getInputStream());
    byte[] message;
    System.out.println("DataInputStream created.");
    int length = dIn.readInt();                   // read length of incoming message
    
        while(true){
            if(length>0) {
                message = new byte[length];
                dIn.readFully(message, 0, message.length); // read the message
                System.out.println(message.length);
                playAudio(message);
            }
        }
    }

  private static void playAudio(byte [] out){
    try
    {
      byte audio[] = out;
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
  
  private static AudioFormat getFormat(){
    float sampleRate = 8000;
    int sampleSizeInBits = 8;
    int channels = 1;
    boolean signed = true;
    boolean bigEndian = true;
    
    return new AudioFormat(sampleRate, 
        sampleSizeInBits, channels, signed, bigEndian);
  }  
} 