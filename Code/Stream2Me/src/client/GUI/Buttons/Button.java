    package client.GUI.Buttons;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton{
//       JButton button = new JButton();
       public boolean pressed = false;
       public String bname = null;
       public Button() {
           createButton();
       }
       
       public Button(String _name) {
           this.bname = _name;
           createButton();
       }
       
       public void createButton(){
            setBorderPainted(false);  
            setContentAreaFilled(false); 
            addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                          if (e.getButton() == MouseEvent.BUTTON1) {
                              if(!pressed){
                                  pressed = !pressed;
                                  setIcon(new ImageIcon("./assests/" + bname + ""));
                              }else{
                                  pressed = !pressed;
                                  setIcon(new ImageIcon("./assests/un" + bname));
                              }
                          }
                        }
                      });
            setIcon(new ImageIcon("./assests/un" + bname));
       }
       
}
