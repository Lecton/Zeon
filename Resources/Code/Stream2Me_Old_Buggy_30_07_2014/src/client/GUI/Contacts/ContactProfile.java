package client.GUI.Contacts;

import Messages.StringMessage;
import Utils.*;
import client.Colleague;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class ContactProfile extends JPanel {
    private Contacts contactPane;
    private Colleague colleague;
    
    
    private final Color HIGHLIGHT_COLOR = new Color(0, 0, 158);

    private JLabel name;
    private JLabel avatar;
    private JButton acceptAudioStream;
    private JButton acceptVideoStream;
    private GroupLayout layout;
    
    private boolean selected =false;

    public ContactProfile(Contacts contactPane, Colleague colleague) {
        this.contactPane =contactPane;
        this.colleague =colleague;
        
        setOpaque(true);
        setFocusable(true);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        
        addMouseListener(new ContactSelectionListener());
        
        this.setLayout(new BorderLayout());
        name =new JLabel();
        avatar =new JLabel();
        layout = new GroupLayout(this);

        acceptAudioStream = new JButton();
        acceptVideoStream = new JButton();

        name.setText("Name");

        acceptAudioStream.setBackground(new Color(51, 255, 0));
        acceptVideoStream.setBackground(new Color(255, 0, 0));

        acceptAudioStream.setEnabled(false);
        acceptVideoStream.setEnabled(false);
        
        acceptAudioStream.addActionListener(new acceptAudioStreamBtnActionPerformed());

        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(avatar, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(acceptAudioStream, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acceptVideoStream, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 29, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(avatar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(name, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(acceptVideoStream, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(acceptAudioStream, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        
        setup();
    }
    
    private void setup() {
        name.setText(colleague.getUsername());
        setAvatar("");
    }
    
    private class acceptAudioStreamBtnActionPerformed implements ActionListener {                                    

        @Override
        public void actionPerformed(ActionEvent e) {
            contactPane.audioResponse(true, colleague.getStreamID());
        }
    }

    private void setAvatar(String URL) {
        try {
            setAvatar(ImageIO.read(new File(colleague.getCustomURLPath()+URL)));
        } catch (IOException e) {
            try {
                setAvatar(ImageIO.read(new File(colleague.getDefaultURL())));
            } catch (IOException ex) {}
        }
    }

    protected void setAvatar(BufferedImage image) {
        updateAvatar(image);
        
        contactPane.updateAvatar(ImageUtils.encodeToString(image, "png"));
    }
    
    public void updateAvatar(BufferedImage image) {
        saveImage(image, colleague.getCustomURL());
        avatar.setIcon(ImageUtils.resizeConvert(image, 64, 64, BufferedImage.SCALE_FAST));
    }
    
    private boolean saveImage(BufferedImage image, String url) {
        try {
            File f =new File(colleague.getCustomURL());
            ImageIO.write(image, "png", f);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
    public int getID() {
        return colleague.getID();
    }
    
    public Colleague getColleague() {
        return colleague;
    }
    
    public void setUsername(String username) {
        colleague.setUsername(username);
        name.setText(username);
        refresh();
        System.out.println("Hello "+name.getText());
    }
    
    public void unSelect() {
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        selected =false;
        
//        System.out.println("Unselected Person: "+colleague.getUsername());
        contactPane.setChatHistory(new ArrayList<StringMessage>());
    }
    
    public void select() {
        setBackground(HIGHLIGHT_COLOR);
        setForeground(Color.WHITE);
        selected =true;
        
//        System.out.println("Selected Person: "+colleague.getUsername());
        contactPane.setChatHistory(colleague.getMessages());
    }

    public boolean isSelected() {
        return selected;
    }
    
    public void toggleSelected() {
        if (!isSelected()) {
            contactPane.select(this);
        } else {
            contactPane.unselect(this);
        }
        
        refresh();
    }
    
    public void refresh() {
        validate();
        repaint();
    }
    
    public void setIncomingAudio(boolean incomingAudio, String streamID) {
        colleague.setIncomingAudio(incomingAudio, streamID);
        acceptAudioStream.setEnabled(incomingAudio);
        
    }
    
    public void setIncomingVideo(boolean incomingVideo, String streamID) {
        colleague.setIncomingVideo(incomingVideo);
        acceptVideoStream.setEnabled(incomingVideo);
    }
}
