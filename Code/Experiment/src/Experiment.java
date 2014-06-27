
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Experiment extends JFrame {
    
    JPanel [] panels;
    JLabel [] labels;
    public Experiment(){
        
        setTitle("Tabbed Pane");
        panels = new JPanel[5];
        labels = new JLabel[5];
        
        for(int i = 0; i < 5;i++){
            panels[i] = new JPanel();
            labels[i] = new JLabel();
            labels[i].setText("You are in area of Tab" + i);
        }
        JTabbedPane jtp = new JTabbedPane();
        getContentPane().add(jtp);
        
        for(int i = 0; i < 5;i++){
            panels[i].add(labels[i]);
            jtp.addTab("Tab " + i, panels[i]);
        }
    }
    
    public static void main(String[] args) {
        
        Experiment tp = new Experiment();
        tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tp.setVisible(true);
        
    }
}
