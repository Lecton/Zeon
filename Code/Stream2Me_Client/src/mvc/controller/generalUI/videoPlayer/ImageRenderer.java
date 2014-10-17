package mvc.controller.generalUI.videoPlayer;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import utils.ImageUtils;

public class ImageRenderer implements GLEventListener {
    private final static Logger LOGGER = Logger.getLogger(ImageRenderer.class.getName());
    
    private GLU glu = new GLU();
    private TextureData textureData;
    private Texture  texture;
    private BufferedImage image;
    
    private VideoFrame parent;

    public ImageRenderer() {
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {}

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        // Get rid of any previously allocated texture
        if (texture != null) {
            texture.dispose();
            texture = null;
        }

        // Convert the base image
        // Note that for simplicity in handling of the XOR cursor, we
        // always make a copy of the base image
        int imageType = BufferedImage.TYPE_INT_ARGB;
//        convertedImage = new BufferedImage(baseImage.getWidth(),  baseImage.getHeight(), imageType);
//        Graphics2D g = convertedImage.createGraphics();
//        g.setComposite(AlphaComposite.Src);
//        g.drawImage(baseImage, 0, 0, null);
//        g.dispose();
        
        if (image != null) {
            // Create a TextureData and Texture from it
            textureData = TextureIO.newTextureData(image, false);
            texture = TextureIO.newTexture(textureData);

            // Draw one quad with the texture
            texture.enable();
            texture.bind();
            gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_REPLACE);
            TextureCoords coords = texture.getImageTexCoords();
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(coords.left(), coords.bottom());
            gl.glVertex3f(0, 0, 0);
            gl.glTexCoord2f(coords.right(), coords.bottom());
            gl.glVertex3f(1, 0, 0);
            gl.glTexCoord2f(coords.right(), coords.top());
            gl.glVertex3f(1, 1, 0);
            gl.glTexCoord2f(coords.left(), coords.top());
            gl.glVertex3f(0, 1, 0);
            gl.glEnd();
            texture.disable();
        } else {
//            System.out.println("Null Image");
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 1, 0, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    
    void setParent(final VideoFrame parent) {
        this.parent =parent;
        (new Thread(new Runnable() {
            @Override
            public void run() {
                while (parent.isVisible()) {
                    if (parent.hasPicture()) {
//                        System.out.println("Got Image");
                        setImage(parent.getPicture());
                    } 
                    else {
                        try {
                            Thread.sleep(100);
                        } 
                        catch (InterruptedException e) {                            
                        }
                    }
                }
//                System.out.println("Ended");
            }
        })).start();
    }
    
    private void setImage(String image) {
//        lblVideo.setIcon(ImageUtils.resizeConvert(image, lblVideo.getWidth(), 
//                lblVideo.getHeight()));
        try {
            this.image =ImageUtils.decodeToImage(image);
            parent.draw();
        } catch (NullPointerException e) {
            LOGGER.log(Level.WARNING, "Image lost");
        }
//        this.paintImmediately(this.getBounds());
    }
}
