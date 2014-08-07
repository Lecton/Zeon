package Utils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Bernhard
 */
public class ImageUtils {
    
    
    /**
     * Decodes an image string to be used in the streaming of video data.
     * @param imageString - the image defines as a string which needs decoding.
     * @return 
     */
    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    /**
     * Creates a scaled version of this image. A new Image object is returned 
     * which will render the image at the specified width and height by default. 
     * The new Image object may be loaded asynchronously even if the original 
     * source image has already been loaded completely. If either width or 
     * height is a negative number then a value is substituted to maintain the 
     * aspect ratio of the original image dimensions. If both width and height 
     * are negative, then the original image dimensions are used.
     * 
     * <br><br><b>hints</b> - flags to indicate the type of algorithm to use for image resampling. (Set to smooth)
     * 
     * @param image - the image to scale.
     * @param width - the width to which to scale the image.
     * @param height - the height to which to scale the image.
     
     * @return A scaled image icon version of the image
     */
    public static ImageIcon resizeConvert(BufferedImage image, int width, int height) {
        return resizeConvert(image, new Dimension(width, height));
    }
    
    /**
     * Creates a scaled version of this image. A new Image object is returned 
     * which will render the image at the specified width and height by default. 
     * The new Image object may be loaded asynchronously even if the original 
     * source image has already been loaded completely. If either width or 
     * height is a negative number then a value is substituted to maintain the 
     * aspect ratio of the original image dimensions. If both width and height 
     * are negative, then the original image dimensions are used.
     * 
     * <br><br><b>hints</b> - flags to indicate the type of algorithm to use for image resampling. (Set to smooth)
     * 
     * @param image - the image to scale.
     * @param boundary - the dimensions to which to scale the image.
     
     * @return A scaled image icon version of the image
     */
    public static ImageIcon resizeConvert(String image, Dimension boundary) {
        return resizeConvert(decodeToImage(image), boundary);
    }
    
    public static ImageIcon resizeConvert(BufferedImage image, Dimension boundary) {
        Dimension size =getScaledDimension(new Dimension(image.getWidth(), image.getHeight()), boundary);
//        System.out.println("Resized: "+size+"\n");
        return new ImageIcon(image.getScaledInstance(size.width, size.height, BufferedImage.SCALE_SMOOTH));
    }
    
    public static BufferedImage resize(BufferedImage image, Dimension boundary) {
        Dimension size =getScaledDimension(new Dimension(image.getWidth(), image.getHeight()), boundary);
//        System.out.println("Resized: "+size+"\n");
        return convert(image.getScaledInstance(size.width, size.height, BufferedImage.SCALE_SMOOTH));
    }
    
    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        float ratio =(float)original_width/(float)original_height;
        if (ratio > 1) {
            new_width =bound_width;
            new_height =(int) (bound_height/ratio);
        } else {
            new_height =bound_height;
            new_width =(int) (bound_width*ratio);
        }

        return new Dimension(new_width, new_height);
    }
    
    public static Dimension getFillDimension(Dimension imgSize, Dimension boundary) {
        int new_height, new_width;
        
        if (imgSize.width > imgSize.height) {
            new_height = boundary.height;
            new_width = (int)(imgSize.width * ((float)new_height/(float)imgSize.height));
        } else {
            new_width = boundary.width;
            new_height = (int)(imgSize.height * ((float)new_width/(float)imgSize.width));
        }

        return new Dimension(new_width, new_height);
    }
    
    public static BufferedImage convert(Icon icon) {
        BufferedImage bi =new BufferedImage(icon.getIconWidth(), 
                icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g =bi.getGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        
        return bi;
    }
    
    public static BufferedImage convert(Image icon) {
        BufferedImage bi =new BufferedImage(icon.getWidth(null), 
                icon.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g =bi.getGraphics();
        g.drawImage(icon, 0, 0, null);
        g.dispose();
        
        return bi;
    }
}
