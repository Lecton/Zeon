/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connection;

import Interface.ClientGUI.Contacts.ContactProfile;
import Interface.ClientGUI.GUI;
import Utils.Log;
import Utils.MessageFactory;
import java.io.IOException;

/**
 *
 * @author Bernhard
 */
public class streamListener implements Runnable {
    private final Connection con;
    private final GUI userInterface;
    
    private boolean running =false;

    public streamListener(Connection con, GUI userInterface) {
        this.con = con;
        this.userInterface = userInterface;
    }
    
    public static streamListener start(Connection con, GUI userInterface) {
        streamListener listener =new streamListener(con, userInterface);
        (new Thread(listener)).start();
        return listener;
    }
    
    public void stop() {
        running =false;
    }
    
    @Override
    public void run() {
        Log.write(this, "Listener started");
        running =true;
        while (running) {
            try {
                Messages.Message m =con.read();
                
                switch (m.handle()) {
                    case newUser:
                        handleNewUser((Messages.UserConnection.NewUser)m);
                        break;
                    case updateAvatar:
                        handleUpdateAvatar((Messages.Update.UpdateAvatar)m);
                        break;
                    case updateName:
                        handleUpdateUsername(
                                (Messages.Update.UpdateUsername)m);
                        break;
                    case logout:
                        handleRemoveUser((Messages.UserConnection.Logout)m);
                        break;
                    case notify:
                        break;
                        
                        
                    case string:
                        handleStringMessage((Messages.StringMessage)m);
                        break;
                    case streamNotify:
                        handleStreamNotification(
                                (Messages.Media.StreamNotify)m);
                        break;
                    case auido:
                        handleAudioStream((Messages.Media.AudioStream)m);
                        break;
                    case video:
                        handleVideoStream((Messages.Media.VideoStream)m);
                        break;
                    default:
                        Log.error(this, "Unknown type: "+m.handle());
                        break;
                }
            } catch (NullPointerException npe) {
                Log.error(this, "Null pointer exception");
            } catch (IOException ex) {
                Log.error(this, "IOException");
            } catch (ClassNotFoundException ex) {
                Log.error(this, "Class casting exception");
            }
        }
    }
    
    private void handleNewUser(Messages.UserConnection.NewUser nu) {
        Log.write(this, "New user");
        if (nu.getUserID() != userInterface.getUserID()) {
            userInterface.getContactList().addProfile(
                    MessageFactory.generateColleague(nu));
        } else {
            Log.write(this, "Me, myself and I");
        }
    }
    
    private void handleRemoveUser(Messages.UserConnection.Logout l) {
        Log.write(this, "User "
                +userInterface.getContactList().getContactProfile(
                        l.getUserID()).getUsername()
                +" logged out");
        
        userInterface.getContactList().removeProfile(l.getUserID());
    }
    
    private void handleUpdateUsername(Messages.Update.UpdateUsername uu) {
        Log.write(this, "User "
                +userInterface.getContactList().getContactProfile(
                        uu.getUserID()).getUsername()
                +" updated username to "+uu.getUsername());
        userInterface.getContactList().getContactProfile(
                uu.getUserID()).setUsername(uu.getUsername());
    }
    
    private void handleUpdateAvatar(Messages.Update.UpdateAvatar ua) {
        Log.write(this, "Avatar updated");
        userInterface.getContactList().getContactProfile(
                ua.getUserID()).updateAvatar(ua.getAvatar());
    }
    
    private void handleStringMessage(Messages.StringMessage sm) {
        Log.write(this, "String message");
        if(sm.getTargetID() == Messages.Message.ALL) {
            userInterface.getUserProfile().addMessage(sm);
        } else {
            userInterface.getContactList().getContactProfile(
                    sm.getUserID()).addMessage(sm);
        }
        
        userInterface.getChatArea().update();
    }

    private void handleStreamNotification(Messages.Media.StreamNotify sn) {
        Log.write(this, sn.getMessage());
        ContactProfile cp =userInterface.getContactList().getContactProfile(
                sn.getUserID());
        if (cp != null) {
            //allow accepting
            cp.setIncoming(sn.getType() == 1, sn.getStreamID());
        }
    }

    private void handleVideoStream(Messages.Media.VideoStream vs) {
        userInterface.getVideoArea().setImage(vs.getImg());
    }

    private void handleAudioStream(Messages.Media.AudioStream as) {
        userInterface.getAudioPlayer().write(as.buffer);
    }
}