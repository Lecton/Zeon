/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import MediaStreaming.Audio.AudioLine;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
    
    /**
     * Creates and returns the AudioFormat to be used for the current stream
     * @return Audio Format to be used to read this audio stream
     */
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
