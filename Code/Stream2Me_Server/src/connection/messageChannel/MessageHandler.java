/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messageChannel;

import channel.group.ClientHandler;
import connection.bootstrap.Handler;
import core.database.StreamHandler;
import core.database.StringMessageHandler;
import core.database.UserHandler;
import core.database.objects.BaseUser;
import core.database.objects.StreamProperty;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.StringMessage;
import messages.media.AudioStreamMessage;
import messages.media.StreamNotifyMessage;
import messages.media.StreamPropertyMessage;
import messages.media.StreamResponseMessage;
import messages.media.StreamUpdateMessage;
import messages.media.VideoStreamMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateListMessage;
import messages.update.UpdateNameMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LoginMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    public static void handle(ChannelHandlerContext ctx, Message msg) {
        switch (msg.handle()) {
            case login:
                handleLogin(ctx.channel(), (LoginMessage)msg);
                break;
            case logout:
                handleLogout(ctx.channel(), (LogoutMessage)msg);
                break;

            case string: 
                handleStringMessage(ctx.channel(), (StringMessage)msg);
                break;
            case updateAvatar:
                handleUpdateAvatar(ctx.channel(), (UpdateAvatarMessage)msg);
                break;
            case updateName:
                handleUpdateName(ctx.channel(), (UpdateNameMessage)msg);
                break;
            case updateList:
                handleListRequest(ctx.channel(), (UpdateListMessage)msg);
                break;

            case streamProperty:
                handleStreamProperty(ctx.channel(), (StreamPropertyMessage)msg);
                break;
            case streamReply:
                handleStreamResponse(ctx.channel(), (StreamResponseMessage)msg);
                break;
            case streamUpdate:
                handleStreamUpdate(ctx.channel(), (StreamUpdateMessage)msg);
                break;
            case auido:
                handleAudioStream(ctx.channel(), (AudioStreamMessage)msg);
                break;
            case video:
                handleVideoStream(ctx.channel(), (VideoStreamMessage)msg);
                break;
            default:
                break;
        }
    }
    
    private static void handleLogin(Channel ch, LoginMessage msg) {
        GreetingMessage g =UserHandler.userLogin(ch, msg);
//        System.out.println("User login attempt: "+g.isSuccessful());
        if (g != null) {
            ch.writeAndFlush(g);
        } else {
//            System.out.println("Login error");
        }
    }
    
    private static void handleLogout(Channel ch, LogoutMessage msg) {
        BaseUser u =UserHandler.getUser(msg.getUserID());
        System.out.println("handle Logout");
        if (u == null) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                    "Client closed connection, but client could not be found.");
            return;
        }

        if (u.isLoggedIn()) {
            ClientHandler.remove(ch, u.getUserID(), u.getGroupID());
            Handler.connections.add(ch);
            LogoutMessage message =new LogoutMessage(u.getUserID(), messages.Message.ALL);
            message.setTargetGroupID(u.getGroupID());
            ClientHandler.writeAndFlush(UserHandler.getGroupID(msg.getUserID()), message);
            
            UserHandler.logoff(u.getUserID());
        } else {
            System.out.println("User not logged in");
        }
    }
    
    private static void handleStringMessage(Channel ch, StringMessage msg) {
        Message message =StringMessageHandler.handleStringMessage(msg);
        ClientHandler.writeAndFlush(UserHandler.getGroupID(msg.getUserID()), message);
    }

    private static void handleUpdateAvatar(Channel ch, UpdateAvatarMessage msg) {
        Message message =UserHandler.updateAvatar(msg);
        ClientHandler.writeAndFlush(UserHandler.getGroupID(msg.getUserID()), message);
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                "Update avatar.");
    }

    private static void handleUpdateName(Channel ch, UpdateNameMessage msg) {
        Message message =UserHandler.updateName(msg);
        ClientHandler.writeAndFlush(UserHandler.getGroupID(msg.getUserID()), message);
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                    "Update username.");
        
    }
    
   /* private static void handleUpdateTitle(ChannelHandlerContext ctx, UpdateTitle msg){
        
    }*/

    private static void handleListRequest(Channel ch, UpdateListMessage msg) {
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                "Update list request.");
        String groupUsers[] =UserHandler.getGroupUsers(msg.getUserID());
        for (String userID: groupUsers) {
            NewUserMessage message =UserHandler.getNewUserMessage(userID, msg.getUserID());
            if (message != null) {
                ClientHandler.writeAndFlush(UserHandler.getGroupID(
                        msg.getUserID()), message);
            }
        }
    }

    private static void handleStreamProperty(Channel ch, StreamPropertyMessage msg) {
        if (msg.getType() == 1) {
            StreamHandler.createStreamProperty(msg.getUserID(), msg.getStreamName());
            Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                    "StreamProperty "+msg.getStreamName()+" created.");
        } else {
            StreamProperty sp =StreamHandler.removeStreamProperty(msg.getUserID(), msg.getStreamName());
            if (sp != null) {
                StreamNotifyMessage message =new StreamNotifyMessage(
                        msg.getUserID(), Message.ALL, sp.getStreamID(), 0);
                ClientHandler.writeAndFlush(
                        UserHandler.getGroupID(msg.getUserID()), 
                        message, sp.generateMatcher());
                Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                        "StreamProperty "+msg.getStreamName()+" removed.");
            } else {
                Logger.getLogger(MessageHandler.class.getName()).log(Level.WARNING, 
                    "StreamProperty "+msg.getStreamName()+" was not found.");
            }
        }
    }

    private static void handleStreamResponse(Channel ch, StreamResponseMessage msg) {
//        Log.write(MessageHandler.class, msg.getMessage());
        boolean result =StreamHandler.respondStream(msg.getStreamID(), msg.getUserID(), msg.isAccept());
        if (!result) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.WARNING, 
                    "Stream response unsuccessful.");
        } else {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                    "Stream response on "+msg.getStreamID()+" from "
                            +msg.getUserID()+" for "+msg.isAccept()+".");
        }
    }

    private static void handleStreamUpdate(Channel ch, StreamUpdateMessage msg) {
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                    msg.getMessage()+".");
        String streamID =StreamHandler.updateStream(msg.getStreamID(), msg.getUserID(), msg.getAffectedUserID(), msg.getAction());
        String groupID =UserHandler.getGroupID(msg.getUserID());
        
        if (streamID != null) {
            ClientHandler.writeAndFlush(groupID, new StreamNotifyMessage(msg.getUserID(), 
                    msg.getAffectedUserID(), streamID,
                    msg.getAction()));
        }
    }

    private static void handleAudioStream(Channel ch, AudioStreamMessage msg) {
//        Log.write(MessageHandler.class, "Audio stream received on streamID: "
//                +as.getStreamID());
        StreamProperty sp =StreamHandler.getStreamProperty(msg.getUserID(), msg.getStreamName());
        if (sp != null) {
            ClientHandler.writeAndFlush(sp.getGroupID(), msg, sp.generateMatcher());
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +as.getStreamID());
        }
    }

    private static void handleVideoStream(Channel ch, VideoStreamMessage msg) {
//        Log.write(MessageHandler.class, "Video stream received on streamID: "
//                +vs.getStreamID());
        StreamProperty sp =StreamHandler.getStreamProperty(msg.getUserID(), msg.getStreamName());
        if (sp != null) {
            ClientHandler.writeAndFlush(sp.getGroupID(), msg, sp.generateMatcher());
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +vs.getStreamID());
        }
    }
}