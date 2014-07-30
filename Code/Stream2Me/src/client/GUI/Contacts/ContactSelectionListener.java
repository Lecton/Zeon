package client.GUI.Contacts;

import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

public class ContactSelectionListener implements MouseInputListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        ContactProfile cp =(ContactProfile)e.getComponent();
        cp.toggleSelected();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}