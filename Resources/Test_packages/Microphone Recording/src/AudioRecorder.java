import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.Player; 
import javax.sound.sampled.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/**
 *
 * @author Zenadia, Lecton, Bernhard
 */

public class AudioRecorder extends JFrame{
    AudioFormat audioFormat;
    TargetDataLine tdl;
    
    final JButton capBtn = new JButton("Capture");
    final JButton stopBtn = new JButton("Stop");
    
    final JPanel buttonPanel = new JPanel();
    
    final ButtonGroup btnGroup = new ButtonGroup();
    final JRadioButton aifcBtn = new JRadioButton("AIFC");    
    final JRadioButton aiffBtn = new JRadioButton("AIFF");
    final JRadioButton auBtn = new JRadioButton("AU", true);   
    final JRadioButton sndBtn = new JRadioButton("SND");   
    final JRadioButton waveBtn = new JRadioButton("WAVE");
    
    public static void main(String args[]) throws UnsupportedAudioFileException, IOException {
        AudioRecorder audioRecorder = new AudioRecorder();
    } 
    
    public AudioRecorder(){
        capBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        capBtn.addActionListener(
                new ActionListener(){  
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        capBtn.setEnabled(false);
                        stopBtn.setEnabled(true);
                        captureAudio();
                    }
                }
        );
        
        stopBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        capBtn.setEnabled(true);
                        stopBtn.setEnabled(false);
                        tdl.stop();
                        tdl.close();
                    }
                }
        );
        
        getContentPane().add(capBtn);
        getContentPane().add(stopBtn);
        
        btnGroup.add(aifcBtn);
        btnGroup.add(aiffBtn);
        btnGroup.add(auBtn);
        btnGroup.add(sndBtn);
        btnGroup.add(waveBtn);
        
        buttonPanel.add(aifcBtn);
        buttonPanel.add(aiffBtn);
        buttonPanel.add(auBtn);
        buttonPanel.add(sndBtn);
        buttonPanel.add(waveBtn);
        
        getContentPane().add(buttonPanel);
        getContentPane().setLayout(new FlowLayout());
        setTitle("Stream2Me - Microphone Capture");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 180);
        setVisible(true);
    }
    
    private void captureAudio(){
        try{
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
            tdl = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
            new CaptureThread().start();
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    private AudioFormat getAudioFormat(){
        float sampleRate = 8000.0F;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
    
class CaptureThread extends Thread
{
    public void run(){
        AudioFileFormat.Type fileType = null;
        File audioFile = null;
        
        if(aifcBtn.isSelected()){
            fileType = AudioFileFormat.Type.AIFC;
            audioFile = new File("audio_recording.aifc");
        }
        else if(aiffBtn.isSelected()){
            fileType = AudioFileFormat.Type.AIFF;
            audioFile = new File("audio_recording.aiff");
        } 
        else if(auBtn.isSelected()){
            fileType = AudioFileFormat.Type.AU;
            audioFile = new File("audio_recording.au");
        }
         else if(sndBtn.isSelected()){
            fileType = AudioFileFormat.Type.SND;
            audioFile = new File("audio_recording.snd");
        }
         else if(waveBtn.isSelected()){
            fileType = AudioFileFormat.Type.WAVE;
            audioFile = new File("audio_recording.wav");
        }
        
        try{
            tdl.open(audioFormat);
            tdl.start();
            AudioSystem.write(new AudioInputStream(tdl), fileType, audioFile);
        } 
        catch (LineUnavailableException ex) {
            Logger.getLogger(AudioRecorder.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(AudioRecorder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    
}




