/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.GUI.Buttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Lecton
 */
public class SubmitButton extends Button{
    public SubmitButton(){
        super("submit.png");
    }

    @Override
    public void createButton(){
        setBorderPainted(false);  
        setContentAreaFilled(false); 
        addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        System.out.println("Submitting");
                    }
                }
            });
        setIcon(new ImageIcon("./assests/" + bname));
    }
}
