/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package MediaStreaming.Audio;

import Utils.MessageUtils;
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
            this.format =MessageUtils.getFormat();
            info = new DataLine.Info(TargetDataLine.class, format);
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
        } catch (LineUnavailableException ex) {
        }
    }
    
    public TargetDataLine getLine() {
        return line;
    }
    
    public int getBufferSize() {
        return (int)format.getSampleRate() * format.getFrameSize();
    }
}
