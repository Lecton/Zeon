import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
//import com.borland.jbcl.layout.*;  
  
public class GUIButton extends JFrame {  
  JPanel contentPane;  
  JButton jbtnImage = new JButton();
  ButtonGroup btngrpStateSelect = new ButtonGroup();  
  GridBagLayout gridBagLayout1 = new GridBagLayout();  
  
  /**Construct the frame*/  
  public GUIButton() {  
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);  
    try {  
      jbInit();  
    }  
    catch(Exception e) {  
      e.printStackTrace();  
    }  
  }  
  /**Component initialization*/  
  private void jbInit() throws Exception  {  
    //setIconImage(Toolkit.getDefaultToolkit().createImage(("[Your Icon]")));  
    contentPane = (JPanel) this.getContentPane();  
    contentPane.setLayout(gridBagLayout1);  
    this.setSize(new Dimension(400, 300));  
    this.setTitle("Frame Title");  
    jbtnImage.setBorderPainted(false);  
    jbtnImage.setContentAreaFilled(false);  
    jbtnImage.setDisabledIcon(new ImageIcon(("unclicked-stream.png")));  
    jbtnImage.setFocusPainted(false);  
    jbtnImage.setIcon(new ImageIcon(("unclicked-stream.png")));  
    jbtnImage.setMargin(new Insets(0, 0, 0, 0));  
    jbtnImage.setPressedIcon(new ImageIcon(("unclicked-stream.png")));  
    jbtnImage.setRolloverIcon(new ImageIcon(("unclicked-stream.png")));  
    jbtnImage.addActionListener(new java.awt.event.ActionListener() {  
      public void actionPerformed(ActionEvent e) {  
        jbtnImage_actionPerformed(e);  
      }  
    });  
    contentPane.add(jbtnImage,  new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0  
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(80, 158, 0, 165), -6, -9));
  }  
  /**Overridden so we can exit when window is closed*/  
  protected void processWindowEvent(WindowEvent e) {  
    super.processWindowEvent(e);  
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {  
      System.exit(0);  
    }  
  }  
  
  void jbtnImage_actionPerformed(ActionEvent e) {  
      System.out.println("clicked");
  }  
  
  void jrdbtnEnableBu_actionPerformed(ActionEvent e) {  
  jbtnImage.setEnabled(true);  
  }  
  
  void jrdbtnDisableBu_actionPerformed(ActionEvent e) {  
jbtnImage.setEnabled(false);  
  }  
} 