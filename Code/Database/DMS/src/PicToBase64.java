
import biz.source_code.base64Coder.Base64Coder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernhard
 */
public class PicToBase64 {
    public static void main(String[] arg) throws IOException {
        File f =new File("default_profile.png");
        
        BufferedImage image =ImageIO.read(f);
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "jpg", bos);
            byte[] imageBytes = bos.toByteArray();

            imageString = new String(Base64Coder.encode(imageBytes));

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(imageString);
    }
}
