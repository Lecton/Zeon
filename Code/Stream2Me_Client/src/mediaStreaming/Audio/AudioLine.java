package mediaStreaming.audio;

import utils.Log;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Bernhard
 */
public class AudioLine {
    private DataLine.Info info;
    private TargetDataLine line;
    private AudioFormat format;
    
    public AudioLine() {
        try {
            this.format =createFormat();
            info = new DataLine.Info(TargetDataLine.class, format);
            line = (TargetDataLine) AudioSystem.getLine(info);
        } catch (LineUnavailableException ex) {
            Log.error(this.getClass(), "Line unavailable");
        }
    }
    
    public void open() {
        try {
            line.open(format);
            line.start();
        } catch (LineUnavailableException ex) {
            Log.error(this.getClass(), "Line unavailable. Could not open");
        }
    }
    
    public void close() {
        line.close();
    }
    
    public int getBufferSize() {
        return (int)format.getSampleRate() * format.getFrameSize();
    }
    
    public AudioFormat getFormat() {
        return format;
    }
    
    public static AudioFormat createFormat() {
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;

        return new AudioFormat(sampleRate, 
                sampleSizeInBits, channels, signed, bigEndian);
    }

    int read(byte[] buffer, int i, int length) {
        return line.read(buffer, i, length);
    }
}
