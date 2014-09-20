package stream2me;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class ImageRenderer implements GLEventListener{
    private GLU glu = new GLU();
    private TextureData textureData;
    private Texture  texture;
    private BufferedImage desktop;

    public ImageRenderer() {
    }
    
    public void init(GLAutoDrawable drawable) {}

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
        
        try {
            desktop =ImageIO.read(new File("test.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(ImageRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create a TextureData and Texture from it
        textureData = TextureIO.newTextureData(desktop, false);
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
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 1, 0, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
    
}
