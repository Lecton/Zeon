    package client.GUI.Buttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton{
    protected boolean pressed = false;
    protected String bname = null;

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
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if(!pressed) {
                            pressed = !pressed;
                            setIcon(new ImageIcon("./assests/" + bname + ""));
                        } else {
                            pressed = !pressed;
                            setIcon(new ImageIcon("./assests/un" + bname));
                        }
                    }
                }
            });
        setIcon(new ImageIcon("./assests/un" + bname));
    }

    public boolean isPressed() {
        return pressed;
    }
}
