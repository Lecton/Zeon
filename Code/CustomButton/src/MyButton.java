
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;


public class MyButton {  
  boolean packFrame = false;  
  
  /**Construct the application*/  
  public MyButton() {  
    GUIButton frame = new GUIButton();  
    //Validate frames that have preset sizes  
    //Pack frames that have useful preferred size info, e.g. from their layout  
    if (packFrame) {  
      frame.pack();  
    }  
    else {  
      frame.validate();  
    }  
    //Center the window  
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
    Dimension frameSize = frame.getSize();  
    if (frameSize.height > screenSize.height) {  
      frameSize.height = screenSize.height;  
    }  
    if (frameSize.width > screenSize.width) {  
      frameSize.width = screenSize.width;  
    }  
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);  
    frame.setVisible(true);  
  }  
  /**Main method*/  
  public static void main(String[] args) {  
    try {  
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");  
    }  
    catch(Exception e) {  
      e.printStackTrace();  
    }  
    new MyButton();  
  }  
}
