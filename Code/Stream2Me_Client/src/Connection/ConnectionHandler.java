/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Connection;

import Client.Colleague;
import Interface.ClientGUI.Contacts.ContactProfile;
import Interface.ClientGUI.GUI;
import Interface.ClientLogin.Login;
import Messages.Message;
import Messages.UserConnection.Greeting;
import Utils.Log;
import Utils.MessageFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * @author Bernhard
 */
class ConnectionHandler extends SimpleChannelInboundHandler<Messages.Message> {
    private GUI userInterface;
    private boolean pass =false;
    private Login owner;

    public void setOwner(Login owner) {
        this.owner = owner;
    }

    public void setUserInterface(GUI userInterface) {
        this.userInterface = userInterface;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if (pass) {
            switch (msg.handle()) {
                case newUser:
                    handleNewUser((Messages.UserConnection.NewUser)msg);
                    break;
                case updateAvatar:
                    handleUpdateAvatar((Messages.Update.UpdateAvatar)msg);
                    break;
                case updateName:
                    handleUpdateUsername(
                            (Messages.Update.UpdateUsername)msg);
                    break;
                case logout:
                    handleRemoveUser((Messages.UserConnection.Logout)msg);
                    break;
                case notify:
                    break;

                case string:
                    handleStringMessage((Messages.StringMessage)msg);
                    break;
                case streamNotify:
                    handleStreamNotification(
                            (Messages.Media.StreamNotify)msg);
                    break;
                case auido:
                    handleAudioStream((Messages.Media.AudioStream)msg);
                    break;
                case video:
                    handleVideoStream((Messages.Media.VideoStream)msg);
                    break;
                default:
                    Log.error(this, "Unknown type: "+msg.handle());
                    break;
            }
        } else {
            switch (msg.handle()) {
                case greeting:
                    Greeting greet =(Greeting)msg;
                    pass =greet.isSuccessful();
                    if (greet.isSuccessful()) {
                        owner.setResponse("Success");
                        owner.setMyAccount(new Colleague(greet.getUserID(), greet.getUsername(), greet.getEmail(), greet.getAvatar()));
                        owner.successful();
                    } else {
                        owner.setResponse("Invalid username or password");
                    }
                    break;
                default:
                    Log.write(this, "Received unhandled message "+msg.handle());
                    break;
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
