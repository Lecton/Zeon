package displayclient;

import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class DisplayClient extends Thread implements KeyListener, MouseListener {
    Socket s = null;
    ObjectInputStream ois = null;
    JLabel label = new JLabel();
    JWindow window = new JWindow();
    ImageIcon icon = new ImageIcon();
    JFrame frame = new JFrame();
    String add = null;
    
    public DisplayClient() {
        try {
            add = JOptionPane.showInputDialog(null,"Server Address","127.0.0.1");
            System.out.println("here");
            s = new Socket(add, 2020);
            s.getOutputStream();
            ois = new ObjectInputStream(s.getInputStream());
            frame.setTitle("Displaying "+add+" port 2020");
            frame.addWindowListener(new WindowCloser());
            frame.getContentPane().add(label);
            label.setIcon(icon);
            Dimension d = frame.getToolkit().getScreenSize();
            frame.setSize(300*d.width/d.height,300);
            frame.addKeyListener(this);
            window.addMouseListener(this);
            frame.setVisible(true);
            this.start();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent ke) { }
    
    @Override
    public void keyReleased(KeyEvent ke) {
        int code = ke.getKeyCode();
        if(code == KeyEvent.VK_F) {
            switchDisplay();
        } else if(code == KeyEvent.VK_X) {
            try {
                s.close();
            } catch(Exception ex) {}
        }
        System.exit(0);
    }
    
    @Override
    public void keyTyped(KeyEvent ke) { }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent me) {
        switchDisplay();
    }
    
    @Override
    public void mouseEntered(java.awt.event.MouseEvent me) {}
    @Override
    public void mouseExited(java.awt.event.MouseEvent me) {}
    @Override
    public void mousePressed(java.awt.event.MouseEvent me) {}
    @Override
    public void mouseReleased(java.awt.event.MouseEvent me) {}

    private void switchDisplay() {
        if(frame.isVisible()) {
            frame.setVisible(false);
            frame.getContentPane().removeAll();
            window.getContentPane().removeAll();
            window.getContentPane().add(label);
            window.setSize(window.getToolkit().getScreenSize());
            window.setVisible(true);
            window.requestFocusInWindow();
        } else {
            window.setVisible(false);
            window.getContentPane().removeAll();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(label);
            frame.setVisible(true);
            frame.requestFocus();
        }
    }

    @Override
    public void run() {
        Dimension dimensions = null;
        BufferedImage image = null;
        while(true) {
            try {
                dimensions = frame.getContentPane().getSize();
                icon = (ImageIcon)ois.readObject();
                if(dimensions == null || icon == null) continue;
                if(dimensions.width>0 && dimensions.height>0 && (dimensions.width != icon.getIconWidth() || dimensions.height != icon.getIconHeight())) {
                    icon.setImage(icon.getImage().getScaledInstance(dimensions.width, dimensions.height, image.SCALE_FAST));
                }
                label.setIcon(icon);
                label.validate();
                frame.validate();
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    class WindowCloser extends WindowAdapter {
        
        @Override
        public void windowClosing(WindowEvent we) {
            try { s.close(); } catch(Exception ex) { ex.printStackTrace(); }
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        new DisplayClient();
    }
}