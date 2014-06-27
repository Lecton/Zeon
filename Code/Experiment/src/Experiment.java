
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Experiment {
    
    JPanel [] panels;
    JLabel [] labels;
    JFrame frame;
    public Experiment(){
        frame = new JFrame();
        frame.setTitle("Tabbed Pane");
        frame.setMinimumSize(new Dimension(1000, 300));
        panels = new JPanel[5];
        labels = new JLabel[5];
        for(int i = 0; i < 5;i++){
            panels[i] = new JPanel();
            labels[i] = new JLabel();
            labels[i].setText("You are in area of Tab" + i); 
        }
        JTabbedPane jtp = new JTabbedPane();
        frame.getContentPane().add(jtp);
        
        for(int i = 0; i < 5;i++){
            panels[i].add(labels[i]);
            jtp.addTab("Tab " + i, panels[i]);
        }
    }
    
    public static void main(String[] args) {
        
        Experiment tp = new Experiment();
        tp.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tp.frame.setVisible(true);
        
    }
}
