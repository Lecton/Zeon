package mvc.controller.generalUI.videoPlayer;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.media.opengl.GLCanvas;
import javax.swing.JFrame;

public class VideoFrame extends JFrame {
    private GLCanvas mainCanvas;
    private final String streamID;
    private final String userID;
    
    private final List<String> imageBuffer;
    private final ImageRenderer videoRenderer;
    
    private AtomicInteger readCount =new AtomicInteger(0);
    private AtomicInteger writeCount =new AtomicInteger(0);
    
    private Thread fps;

    public VideoFrame(String title, String streamID, String userID, String streamName) throws HeadlessException {
        imageBuffer =new ArrayList<>();
        this.streamID =streamID;
        this.userID =userID;
//        System.out.println("streamID: "+streamID);
        this.videoRenderer =new ImageRenderer();
        
        setTitle(title+" - "+streamName);
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
//                    System.out.println("Read FPS: "+read+" --- Write FPS: "+write);
                }
            }
        }, streamID+" - fps");
        fps.start();
    }

    public String getStreamID() {
        return streamID;
    }

    public String getUserID() {
        return userID;
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

    protected void draw() {
        mainCanvas.repaint();
    }
}
