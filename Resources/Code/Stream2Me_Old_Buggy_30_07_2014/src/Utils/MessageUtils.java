package Utils;

import MediaStreaming.Audio.AudioLine;
import javax.sound.sampled.AudioFormat;

/**
 *
 * @author Bernhard
 */
public class MessageUtils {
    private static AudioLine audioLine =new AudioLine();
    private static int ID =0;
    
    
    
    public static AudioLine getAudioLine() {
        return audioLine;
    }
    
    public static AudioFormat getFormat() {
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;

        return new AudioFormat(sampleRate, 
                sampleSizeInBits, channels, signed, bigEndian);
    }
    
    private static synchronized int getAndIncrementID() {
        return ID++;
    }
    
    public static synchronized String getID(int ID) {
        return ID+""+getAndIncrementID();
    }
    
    public static int[] intToArray(int value) {
        int[] result =new int[1];
        result[0] =value;
        return result;
    }
}
