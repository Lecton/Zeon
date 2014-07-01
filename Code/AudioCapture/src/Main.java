
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
public class Main {
    
  private static AudioCapture ac;
  
  public static void main(String args[]){
    final JButton capture = new JButton("Capture");
    final JButton stop = new JButton("Stop");
    capture.setEnabled(true);
    stop.setEnabled(false);
    ac = new AudioCapture();
    JFrame frame = new JFrame("Capture Sound Demo");
    

    ActionListener captureListener = 
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                          capture.setEnabled(false);
                          stop.setEnabled(true);
                            try{
                                
                                ac.captureAudio();
                            }catch (IOException ex) {
                                Logger.getLogger(AudioCapture.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    };
    
    capture.addActionListener(captureListener);
    frame.add(capture, BorderLayout.NORTH);

    ActionListener stopListener = 
                    new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e) {
                          capture.setEnabled(true);
                          stop.setEnabled(false);
                          ac.running = false;
                        }
                };
    
    stop.addActionListener(stopListener);
    frame.add(stop, BorderLayout.CENTER);
    
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);    
    frame.pack();
    frame.show();
  }
  
  
}
