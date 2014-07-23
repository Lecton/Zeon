/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.GUI;

import Messages.MessageUtils;
import client.Colleague;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Bernhard
 */
public class ContactProfiler extends JPanel implements ListCellRenderer<Colleague> {
    private final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

    private JLabel name;
    private JLabel avatar;
    private JButton acceptAudioStream;
    private JButton acceptVideoStream;
    private GroupLayout layout;
    private GUI userInterface;

    /**
    * Renders the cells in the list whenever a change is made to the list of
    * contacts.
    */
    public ContactProfiler(GUI userInterface) {
        this.userInterface =userInterface;
        
        setOpaque(true);
        this.setLayout(new BorderLayout());
        name =new JLabel("User");
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
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Colleague> list, Colleague value,
        int index, boolean isSelected, boolean cellHasFocus) {
        name.setText(value.getUsername());
        acceptAudioStream.setEnabled(value.getIncomingAudio());
        acceptVideoStream.setEnabled(value.getIncomingVideo());
        setAvatar("");
    //                name.setText(value.getTitle());
    //                name.setIcon(value.getImage());
        if (isSelected) {
            setBackground(HIGHLIGHT_COLOR);
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }
    
    private class acceptAudioStreamBtnActionPerformed implements ActionListener {                                    

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }

    public void setAvatar(String URL) {
        try {
            setAvatar(ImageIO.read(new File(".\\assests\\"+URL)));
        } catch (IOException e) {
            try {
                setAvatar(ImageIO.read(new File(".\\assests\\default_profile.png")));
            } catch (IOException ex) {}
        }
    }

    public void setAvatar(BufferedImage image) {
        avatar.setIcon(new ImageIcon(image.getScaledInstance(64, 64, BufferedImage.SCALE_FAST)));
    }
}
