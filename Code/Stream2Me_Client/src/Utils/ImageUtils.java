/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
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
        return new ImageIcon(image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH));
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
     * @param image - the encoded image to scale.
     * @param width - the width to which to scale the image.
     * @param height - the height to which to scale the image.
     
     * @return A scaled image icon version of the image
     */
    public static ImageIcon resizeConvert(String image, int width, int height) {
        return resizeConvert(decodeToImage(image), width, height);
    }
}
