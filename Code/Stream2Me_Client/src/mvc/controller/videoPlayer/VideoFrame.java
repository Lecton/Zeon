package mvc.controller.videoPlayer;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public class VideoFrame extends JFrame {
    private GLCanvas mainCanvas;
    private final String streamID;
    private final List<String> imageBuffer;
    private final ImageRenderer videoRenderer;
    
    private AtomicInteger readCount =new AtomicInteger(0);
    private AtomicInteger writeCount =new AtomicInteger(0);
    
    private Thread fps;

    public VideoFrame(String title, String streamID) throws HeadlessException {
        imageBuffer =new ArrayList<>();
        this.streamID =streamID;
        System.out.println("streamID: "+streamID);
        this.videoRenderer =new ImageRenderer();
        
        setTitle(title+" - "+streamID);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainCanvas = new GLCanvas();
        mainCanvas.addGLEventListener(videoRenderer);
        getContentPane().add(mainCanvas);
        setSize(512, 512);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        videoRenderer.setParent(this);
        
        fps =new Thread(new Runnable(){

            @Override
            public void run() {
                while (VideoFrame.this.isVisible()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    int read =readCount.getAndSet(0);
                    int write =writeCount.getAndSet(0);
                    System.out.println("Read FPS: "+read+" --- Write FPS: "+write);
                }
            }
        }, streamID+" - fps");
        fps.start();
    }

    public String getStreamID() {
        return streamID;
    }
    
    public void write(final String image) {
        imageBuffer.add(image);
        writeCount.incrementAndGet();
//        System.out.print("Write: "+writeCount.incrementAndGet()+" -- ");
//        System.out.println("Read: "+readCount.getAndSet(0));
    }
    
    protected boolean hasPicture() {
        return !imageBuffer.isEmpty();
    }

    protected String getPicture() {
        if (hasPicture()) {
//            System.out.print("Write: "+writeCount.getAndSet(0)+" -- ");
//            System.out.println("Read: "+readCount.incrementAndGet());
            readCount.incrementAndGet();
            return imageBuffer.remove(0);
        } else {
            return "";
        }
    }

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

    protected void draw() {
        mainCanvas.repaint();
    }
}
