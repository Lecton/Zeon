/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller.generalUI;

import communication.handlers.MessageFactory;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import mvc.controller.generalUI.contacts.ContactListControl;
import mvc.controller.Control;
import mvc.model.person.Person;
import mvc.view.generalUI.ProfilePanel;
import utils.ImageUtils;

/**
 *
 * @author Bernhard
 */
public class ProfileControl implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(ProfileControl.class.getName());
    
    public static ProfileControl INSTANCE =new ProfileControl();
    
    private static ProfilePanel view;

    public static void register(ProfilePanel profilePanel) {
        view =profilePanel;
    }

    public static void updateContact(ProfilePanel panel) {
        Person person =ContactListControl.INSTANCE.getColleague(panel.getUserID());
        if (person != null) {
            view.setProfile(person.getName(), person.getSurname(), 
                    person.getEmail(), person.getAvatar(), person.getTitle(), 
                    person.getAboutMe());
//            throw new UnsupportedOperationException("Not supported yet.");
//            protected static void updateContent(ProfilePanel pp) {
//                Person p =ContactListControl.INSTANCE.getColleague(pp.getUserID());
//                pp.setProfile(p.getName(), p.getSurname(), p.getEmail(), p.getAvatar(), p.getTitle(), p.getAboutMe());
//            }
        }
    }
    
    public static void updateUser(ProfilePanel panel) {
        Person user =UserControl.INSTANCE.getUser();
        view.setProfile(user.getName(), user.getSurname(), user.getEmail(), 
                user.getAvatar(), user.getTitle(), user.getAboutMe());
//        throw new UnsupportedOperationException("Not supported yet.");
//        protected static void updateContent(ProfilePanel userPanel) {
//            userPanel.setProfile(model.getName(), model.getSurname(), 
//                    model.getEmail(), model.getAvatar(), model.getTitle(), 
//                    model.getAboutMe());
//        }
    }

    public static void clear() {
        view.clear();
    }

    public void update(Person person) {
        if (person.getUserID().equals(view.getUserID())) {
            view.setProfile(person.getName(), person.getSurname(), 
                    person.getEmail(), person.getAvatar(), 
                    person.getTitle(), person.getAboutMe());
        }
    }
    
    public BufferedImage imgProfilePicMouseClicked(MouseEvent evt, boolean owner, String userID) {
        if (owner) {
            if (evt.getButton() == 3) {
                try {
                    JFileChooser chooser =new JFileChooser();
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    chooser.setMultiSelectionEnabled(false);
                    FileNameExtensionFilter filter =new FileNameExtensionFilter("images", "png", "jpg", "jpeg", "bmp");
                    chooser.setFileFilter(filter);

                    if (chooser.showOpenDialog(null) == 0) {
                        File cf =chooser.getSelectedFile();
                        
                        BufferedImage image =ImageIO.read(cf);
                        image =ImageUtils.resize(image, new Dimension(500, 500));
                        Control.INSTANCE.writeMessage(
                                MessageFactory.generateUpdateAvatar(userID, 
                                        ImageUtils.encodeToString(image, "png")));
                        UserControl.INSTANCE.setAvatar(ImageUtils.encodeToString(image, "png"));
                        UserControl.INSTANCE.update();
                        
                        return image;
                    }
                    return null;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return null;
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if (command != null) {
            if (command.equals("updateUserDetails")) {
                String userID =view.getUserID();
                String name =view.getFirstname();
                String surname =view.getSurname();
                String email =view.getEmail();
                String title =view.getTitle();
                String aboutMe =view.getAboutMe();
                
                Person person =UserControl.INSTANCE.getUser();
                if (person != null) {
                    person.setName(name);
                    person.setSurname(surname);
                    person.setEmail(email);
                    person.setTitle(title);
                    person.setAboutMe(aboutMe);
                    UserControl.INSTANCE.update();
                    Control.INSTANCE.writeMessage(MessageFactory.generateUpdateProfile(userID, name, surname, email, title, aboutMe));
                }
            }
        }
    }
}
