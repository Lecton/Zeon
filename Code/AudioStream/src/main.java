import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.io.BufferedInputStream; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.player.Player; 

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 *
 * @author Zen
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(
        "audiofile"));
//    stream = AudioSystem.getAudioInputStream(new URL(
  //      "http://hostname/audiofile"));

    AudioFormat format = stream.getFormat();
    if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
      format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
              format.getSampleRate(), 
              format.getSampleSizeInBits() * 2, 
              format.getChannels(), 
              format.getFrameSize() * 2, 
              format.getFrameRate(),
              true); // big endian
      stream = AudioSystem.getAudioInputStream(format, stream);
    }

    SourceDataLine.Info info = new DataLine.Info(SourceDataLine.class, 
                                                stream.getFormat(), 
                                                ((int) stream.getFrameLength() * format.getFrameSize()));
    SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
    line.open(stream.getFormat());
    line.start();

    int numRead = 0;
    byte[] buf = new byte[line.getBufferSize()];
    while ((numRead = stream.read(buf, 0, buf.length)) >= 0) {
      int offset = 0;
      while (offset < numRead) {
        offset += line.write(buf, offset, numRead - offset);
      }
    }
    line.drain();
    line.stop();
    /*
        audioPlayer mp3 = new audioPlayer("c:/07.mp3");  
        mp3.playAudio();
        */
    }
}
/***********Play audio from directory*************/
class audioPlayer {
    private String file;
    private Player player;
    BufferedInputStream bis;
    
    public audioPlayer(String file) throws FileNotFoundException {
       this.file = file;
       FileInputStream fis = new FileInputStream(file);
       bis = new BufferedInputStream(fis);
    }
    
    public void playAudio() {
        try {  
            player = new Player(bis);  
            player.play();  
        }  
        catch (Exception e) {  
            System.out.println("Cannot play the audio file specified: " + file);  
            System.out.println(e);  
        }  
    }
}

