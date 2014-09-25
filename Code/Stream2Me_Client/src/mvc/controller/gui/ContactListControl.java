/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import communication.handlers.MessageFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import mvc.controller.videoPlayer.VideoManager;
import mvc.model.Colleague;
import mvc.model.ColleagueList;
import mvc.model.person.Notifier;
import mvc.model.person.Person;
import mvc.model.person.Receiver;
import mvc.view.generalUI.contacts.ContactList;
import mvc.view.generalUI.contacts.ContactPopup;
import mvc.view.generalUI.containers.Button;

/**
 *
 * @author Bernhard
 */
public class ContactListControl implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(ContactListControl.class.getName());
    
    public static ContactListControl INSTANCE =new ContactListControl();
    private static ContactList view;
    private static ColleagueList model =new ColleagueList();
    
    protected static void register(ContactList viewList) {
        view =viewList;
    }

    protected static void clear() {
        model =new ColleagueList();
    }
    
    public static void addColleague(String userID, String name, String surname, String email, String avatar, String title, String aboutMe) {
        if (!userID.equals(UserControl.getUserID())) {
            Colleague person =new Colleague(userID, name, surname, email, avatar, title, aboutMe);
            model.add(person);
            MessageControl.INSTANCE.addMessageHist(userID);
            UpdateControl.INSTANCE.add(userID, UpdateControl.NEWUSER);
        }
    }

    public static void removeColleague(String userID) {
        LOGGER.log(Level.INFO, "User disconnect");
        model.remove(userID);
       
        UpdateControl.INSTANCE.add(userID, UpdateControl.REMOVEUSER);
    }

    public static void updateContact(String userID, String name, String surname, String avatar, String title, String aboutMe) {
        Person person =INSTANCE.getColleague(userID);
        if (person != null) {
            if (name != null) {
                person.setName(name);
            }
            if (surname != null) {
                person.setSurname(surname);
            }
            if (avatar != null) {
                person.setAvatar(avatar);
            }
            if (title != null) {
                person.setTitle(title);
            }
            if (aboutMe != null) {
                person.setAboutMe(aboutMe);
            }
            UpdateControl.INSTANCE.add(userID, UpdateControl.UPDATEDETAILS);
        }
    }

    public static void acceptStream(String streamID, String userID, int type, boolean action) {
        if (type == 0) {
            Receiver person =INSTANCE.getColleague(userID);
            if (person != null) {
                person.setReceivingVideo(action);
                person.setVideoStream(streamID);
                person.setAcceptedVideo(false);
                UpdateControl.INSTANCE.add(userID, UpdateControl.VIDEONOTIFICATION, action);
            }
        } else if (type == 1) {
            Receiver person =INSTANCE.getColleague(userID);
            if (person != null) {
                person.setReceivingAudio(action);
                person.setAudioStream(streamID);
                person.setAcceptedAudio(false);
                UpdateControl.INSTANCE.add(userID, UpdateControl.AUDIONOTIFICATION, action);
            }
        }
    }
    
    protected void addProfile(String userID) {
        Person person =model.get(userID);
        view.addProfile(person.getUserID(), person.getFullname(), person.getAvatar());
    }
    
    protected Colleague getColleague(String userID) {
        return model.get(userID);
    }
    
    protected void removeProfile(String userID) {
        view.removeProfile(userID);
    }

    protected String getColleagueFullname(String userID) {
        return model.get(userID).getFullname();
    }

    protected boolean alertMessage(String userID) {
        Notifier person =getColleague(userID);
        if (person != null) {
            person.setMessage(true);
            view.alert(userID);
            return true;
        }
        return false;
    }

    protected void settleMessageAlert(String userID) {
        Notifier person =getColleague(userID);
        if (person != null) {
            person.setMessage(false);
        }
    }

    protected void update(String userID) {
        Person person =model.get(userID);
        view.updateProfile(person.getUserID(), person.getFullname(), person.getAvatar());
        ProfileControl.INSTANCE.update(person);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command =e.getActionCommand();
        if (command.equals("respondVideo")) {
            String userID =((Button)e.getSource()).getOwnerID();
            Receiver person =model.get(userID);
            if (person != null) {
                ((Button)e.getSource()).togglePressed();
                boolean accept =((Button)e.getSource()).isPressed();
                person.setAcceptedVideo(accept);
                person.setVideo(!accept);
                
                if (accept) {
                    StreamControl.INSTANCE.addVideoFrame(person.getVideoStream());
                }
                
                Control.INSTANCE.writeMessage(MessageFactory
                        .generateStreamResponse(UserControl.getUserID(), 
                                person.getVideoStream(), accept));
            }
            
            LOGGER.log(Level.INFO, "Respond video "+((Button)e.getSource()).isPressed());
        } else if (command.equals("respondAudio")) {
            String userID =((Button)e.getSource()).getOwnerID();
            Receiver person =model.get(userID);
            if (person != null) {
                ((Button)e.getSource()).togglePressed();
                boolean accept =((Button)e.getSource()).isPressed();
                person.setAcceptedAudio(accept);
                person.setAudio(!accept);
                
                Control.INSTANCE.writeMessage(MessageFactory
                        .generateStreamResponse(UserControl.getUserID(), 
                                person.getAudioStream(), accept));
            }
            
            LOGGER.log(Level.INFO, "Respond audio"+((Button)e.getSource()).isPressed());
        } else if (command.equals("inviteVideo")) {
            String userID =((ContactPopup)((JMenuItem) e.getSource()).getParent()).getUserID();
            Receiver person =model.get(userID);
            if (person != null) {
                person.setReceivingVideo(true);
                String videoStreamID =UserControl.INSTANCE.getUser().getVideoStreamID();
                
                Control.INSTANCE.writeMessage(MessageFactory
                        .generateStreamUpdate(UserControl.getUserID(), 
                                "SERVER", videoStreamID, userID, 0, true));
            }
            
            System.out.println("Invite to video");
        } else if (command.equals("inviteAudio")) {
            String userID =((ContactPopup)((JMenuItem) e.getSource()).getParent()).getUserID();
            Receiver person =model.get(userID);
            if (person != null) {
                person.setReceivingAudio(true);
                String audioStreamID =UserControl.INSTANCE.getUser().getAudioStreamID();
                
                Control.INSTANCE.writeMessage(MessageFactory
                        .generateStreamUpdate(UserControl.getUserID(), 
                                "SERVER", audioStreamID, userID, 1, true));
            }
            
            System.out.println("Invite to audio");
        } else if (command.equals("removeVideo")) {
            String userID =((ContactPopup)((JMenuItem) e.getSource()).getParent()).getUserID();
            Receiver person =model.get(userID);
            if (person != null) {
                person.setReceivingVideo(false);
                String videoStreamID =UserControl.INSTANCE.getUser().getVideoStreamID();
                
                Control.INSTANCE.writeMessage(MessageFactory
                        .generateStreamUpdate(UserControl.getUserID(), 
                                "SERVER", videoStreamID, userID, 0, false));
            }
            
            System.out.println("Remove from video");
        } else if (command.equals("removeAudio")) {
            String userID =((ContactPopup)((JMenuItem) e.getSource()).getParent()).getUserID();
            Receiver person =model.get(userID);
            if (person != null) {
                person.setReceivingAudio(false);
                String audioStreamID =UserControl.INSTANCE.getUser().getAudioStreamID();
                
                Control.INSTANCE.writeMessage(MessageFactory
                        .generateStreamUpdate(UserControl.getUserID(), 
                                "SERVER", audioStreamID, userID, 1, false));
            }
            
            System.out.println("Remove from audio");
        }
    }

    protected boolean alertVideo(String userID, boolean action) {
        Notifier person =getColleague(userID);
        if (person != null) {
            person.setVideo(action);
            view.alert(userID);
            return true;
        }
        return false;
    }
    
    protected boolean alertAudio(String userID, boolean action) {
        Notifier person =getColleague(userID);
        if (person != null) {
            person.setAudio(action);
            view.alert(userID);
            return true;
        }
        return false;
    }
}
