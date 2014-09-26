package stream2me;

import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public class Video {
    private GLCanvas mainCanvas;

    private void run() {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);

        // Create top-level window and menus
        JFrame frame = new JFrame("Texture Sub Image Test");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // Now set up the main GLCanvas
        mainCanvas = new GLCanvas();
        mainCanvas.addGLEventListener(new ImageRenderer());
//        mainCanvas.addMouseListener(new MouseAdapter() {
//            public void mouseExited(MouseEvent e) {
//              mainCanvas.repaint();
//            }
//        });

        frame.getContentPane().add(mainCanvas);
        frame.setSize(512, 512);
        frame.setVisible(true);
        
        new Thread(new Runnable() {
            int count = 0;
            public void run() {
                while (true) {
                    mainCanvas.repaint();
                    count++;
                    try {
                        Thread.sleep(1000/60);
                    } catch (InterruptedException e) {

                    }
                    if (count / 10 == (double)count / 10.0) {
                        System.out.println("Draw Count: " + count);
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new Video().run();
    }
}
