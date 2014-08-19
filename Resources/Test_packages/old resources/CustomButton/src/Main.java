
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lecton
 */
public class Main {
       static JButton button = new JButton();
       static boolean pressed = false;
      public static void main(String[] args) {
        JFrame frame = new JFrame();
        
    button.setBorderPainted(false);  
    button.setContentAreaFilled(false); 
    button.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                  if (e.getButton() == MouseEvent.BUTTON1) {
                      if(!pressed){
                          pressed = !pressed;
                          button.setSize(5,5);
                          button.setIcon(new ImageIcon(("submit.png")));
                      }else{
                          pressed = !pressed;
                        button.setIcon(new ImageIcon(("submit.png")));                         
                      }
                      
                  } 

                }
              });
    button.setIcon(new ImageIcon(("submit.png")));
        frame.add(button);
        frame.setSize(new Dimension(500,500));
        frame.setVisible(true);  
      }
}
