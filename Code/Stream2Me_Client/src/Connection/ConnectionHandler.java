/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import client.Colleague;
import connection.messageLog.ConnectionObserver;
import userInterface.generalUI.GUI;
import userInterface.authentication.Login;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messages.Message;
import messages.StringMessage;
import messages.media.AudioStreamMessage;
import messages.media.StreamNotifyMessage;
import messages.media.VideoStreamMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateNameMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import userInterface.generalUI.contacts.ContactProfile;
import utils.Log;
import utils.MessageFactory;

/**
 *
 * @author Bernhard
 */
class ConnectionHandler extends SimpleChannelInboundHandler<Message> {
    private GUI userInterface;
    private boolean pass =false;
    private Login owner;
    private ConnectionPool pool;
    
    public void setOwner(Login owner) {
        this.owner = owner;
        pass =false;
        pool =new ConnectionPool(this);
        (new Thread(pool)).start();
    }

    public void setUserInterface(GUI userInterface) {
        this.userInterface = userInterface;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public boolean isPass() {
        if (userInterface == null) {
            return false;
        } else {
            return pass && userInterface.isShowing();
        }
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if (pass) {
            ConnectionObserver.write(msg);
            switch (msg.handle()) {
                case newUser:
                    handleNewUser((NewUserMessage)msg);
                    break;
                case updateAvatar:
                    handleUpdateAvatar((UpdateAvatarMessage)msg);
                    break;
                case updateName:
                    handleUpdateName((UpdateNameMessage)msg);
                    break;
                case logout:
                    handleRemoveUser((LogoutMessage)msg);
                    break;
                case notify:
                    break;

                case string:
                    handleStringMessage((StringMessage)msg);
                    break;
                case streamNotify:
                    handleStreamNotification((StreamNotifyMessage)msg);
                    break;
                case auido:
                    handleAudioStream((AudioStreamMessage)msg);
                    break;
                case video:
                    handleVideoStream((VideoStreamMessage)msg);
                    break;
                default:
                    Log.error(this.getClass(), "Unknown type: "+msg.handle());
                    break;
            }
        } else {
            switch (msg.handle()) {
                case greeting:
                    GreetingMessage greet =(GreetingMessage)msg;
                    pass =greet.isSuccessful();
                    if (greet.isSuccessful()) {
                        owner.setResponse("Success");
                        owner.setMyAccount(new Colleague(greet.getUserID(), greet.getName(), greet.getSurname(), greet.getEmail(), greet.getAvatar()));
                        owner.setMessage(greet);
                    } else {
                        owner.setResponse("Invalid username or password");
                    }
                    
                    ConnectionObserver.write(msg);
                    break;
                default:
                    Log.write(this.getClass(), "Received unhandled message "+msg.handle()+" added to pool");
                    pool.add(ctx, msg);
                    break;
            }
        }
    }
        
    private void handleNewUser(NewUserMessage nu) {
        Log.write(this.getClass(), "New user");
        if (nu.getUserID() != userInterface.getUserID()) {
            userInterface.addProfile(
                    MessageFactory.generateColleague(nu));
        } else {
            Log.write(this.getClass(), "Me, myself and I");
        }
    }
    
    private void handleRemoveUser(LogoutMessage l) {
        Colleague c =userInterface.getContactProfile(
                        l.getUserID());
        if (c != null) {
            Log.write(this.getClass(), "User "
                    +c.getEmail()
                    +" logged out");
        } else {
            Log.error(this.getClass(), "User to be removed, "
                    + "but cannot be found.");
        }
        userInterface.removeProfile(l.getUserID());
    }
    
    private void handleUpdateName(UpdateNameMessage uu) {
        Colleague c =userInterface.getContactList().getProfile(
                        uu.getUserID());
        
        if (c != null) {
            Log.write(this.getClass(), "User "
                    +c.getFullName()
                    +" updated username to "+uu.getName()+ " " + uu.getSurname());
            c.setName(uu.getName());
            c.setSurname(uu.getSurname());
        } else {
            Log.error(this.getClass(), "User to be updated, "
                    + "but cannot be found.");
        }
    }
    
    private void handleUpdateAvatar(UpdateAvatarMessage ua) {
        Log.write(this.getClass(), "Avatar updated");
        userInterface.getContactList().getContactProfile(
                ua.getUserID()).updateAvatar(ua.getAvatar());
    }
    
    private void handleStringMessage(StringMessage sm) {
        Log.write(this.getClass(), "String message from: "+sm.getUserID()+" to:"+sm.getTargetID());
        if(sm.getTargetID() == Message.ALL) {
            userInterface.getUser().addMessage(sm);
        } else {
            userInterface.getContactList().getProfile(
                    sm.getUserID()).addMessage(sm);
        }
        
        userInterface.updateChatArea(sm.getTargetID(), sm.getUserID());
    }

    private void handleStreamNotification(StreamNotifyMessage sn) {
        Log.write(this.getClass(), sn.getMessage());
        ContactProfile cp =userInterface.getContactList().getContactProfile(
                sn.getUserID());
        if (cp != null) {
            //allow accepting
            cp.setIncoming(sn.getType() == 1, sn.getStreamID());
        }
    }

    private void handleVideoStream(VideoStreamMessage vs) {
        userInterface.writetoVideoBuffer(vs.getStreamID(), vs.getImg());
    }

    private void handleAudioStream(AudioStreamMessage as) {
        userInterface.writetoAudioBuffer(as.buffer);
    }
}
