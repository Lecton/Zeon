/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messageChannel;

import channel.ClientHandler;
import channel.ClientChannel;
import connection.bootstrap.Handler;
import core.database.StreamHandler;
import core.database.StringMessageHandler;
import core.database.UserHandler;
import core.database.objects.StreamProperty;
import core.database.objects.User;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
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
import utils.Log;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    public static void handle(ChannelHandlerContext ctx, Message msg) {
        if (ctx.channel() instanceof ClientChannel) {
            if (((ClientChannel)ctx.channel()).getUserID() == msg.getUserID()) {
                switch (msg.handle()) {
                    case login:
                        handleLogin((ClientChannel)ctx.channel(), (LoginMessage)msg);
                        break;
                    case logout:
                        handleLogout((ClientChannel)ctx.channel(), (LogoutMessage)msg);
                        break;

                    case string: 
                        handleStringMessage((ClientChannel)ctx.channel(), (StringMessage)msg);
                        break;
                    case updateAvatar:
                        handleUpdateAvatar((ClientChannel)ctx.channel(), (UpdateAvatarMessage)msg);
                        break;
                    case updateName:
                        handleUpdateName((ClientChannel)ctx.channel(), (UpdateNameMessage)msg);
                        break;
                    case updateList:
                        handleListRequest((ClientChannel)ctx.channel(), (UpdateListMessage)msg);
                        break;

                    case streamProperty:
                        handleStreamProperty((ClientChannel)ctx.channel(), (StreamPropertyMessage)msg);
                        break;
                    case streamReply:
                        handleStreamResponse((ClientChannel)ctx.channel(), (StreamResponseMessage)msg);
                        break;
                    case streamUpdate:
                        handleStreamUpdate((ClientChannel)ctx.channel(), (StreamUpdateMessage)msg);
                        break;
                    case auido:
                        handleAudioStream((ClientChannel)ctx.channel(), (AudioStreamMessage)msg);
                        break;
                    case video:
                        handleVideoStream((ClientChannel)ctx.channel(), (VideoStreamMessage)msg);
                        break;
                    default:
                        break;
                }
            } else {
                Log.error(MessageHandler.class, "User attempt impersonation of another user");
            }
        } else {
            Log.error(MessageHandler.class, "Unknown sender of message: "+msg);
        }
    }
    
    private static void handleLogin(ClientChannel ch, LoginMessage msg) {
        GreetingMessage g =UserHandler.userLogin(ch, msg);
        ch.writeAndFlush(g);
    }
    
    private static void handleLogout(ClientChannel ch, LogoutMessage msg) {
        User u =UserHandler.getUser(msg.getUserID());
        if (u == null) {
            Log.error(MessageHandler.class, "Client closed connection, but client could not be found.");
            return;
        }

        if (u.isLoggedIn()) {
            ClientHandler.remove(ch);
            Handler.connections.add(ch.getChannel());
            LogoutMessage message =new LogoutMessage(u.getUserID(), messages.Message.ALL);
            message.setTargetGroupID(u.getGroupID());
            ClientHandler.writeAndFlush(message);
            
            UserHandler.logoff(u.getUserID());
        }
    }
    
    private static void handleStringMessage(ClientChannel ch, StringMessage msg) {
        Message message =StringMessageHandler.handleStringMessage(msg);
        ClientHandler.writeAndFlush(message);
    }

    private static void handleUpdateAvatar(ClientChannel ch, UpdateAvatarMessage msg) {
        Message message =UserHandler.updateAvatar(msg);
        ClientHandler.writeAndFlush(message);
        Log.write(MessageHandler.class, "Update avatar");
    }

    private static void handleUpdateName(ClientChannel ch, UpdateNameMessage msg) {
        Message message =UserHandler.updateName(msg);
        ClientHandler.writeAndFlush(message);
        Log.write(MessageHandler.class, "Update username");
    }
    
   /* private static void handleUpdateTitle(ChannelHandlerContext ctx, UpdateTitle msg){
        
    }*/

    private static void handleListRequest(ClientChannel ch, UpdateListMessage msg) {
        Log.write(MessageHandler.class, "Update list request");
        int groupUsers[] =UserHandler.getGroupUsers(msg.getUserID());
        for (int userID: groupUsers) {
            NewUserMessage message =UserHandler.getNewUserMessage(userID, msg.getUserID());
            if (message != null) {
                ClientHandler.writeAndFlush(message);
            }
        }
    }

    private static void handleStreamProperty(ClientChannel ch, StreamPropertyMessage msg) {
        if (msg.getType() == 1) {
            StreamHandler.createStreamProperty(msg.getUserID(), msg.getStreamName());
            Log.write(MessageHandler.class, "StreamProperty "+
                    msg.getStreamName()+" created.");
        } else {
            StreamProperty sp =StreamHandler.removeStreamProperty(msg.getUserID(), msg.getStreamName());
            if (sp != null) {
                StreamNotifyMessage message =new StreamNotifyMessage(
                        msg.getUserID(), Message.ALL, sp.getStreamID(), 0);
                ClientHandler.writeAndFlush(message, sp.generateMatcher());
                Log.write(MessageHandler.class, "StreamProperty "+
                        msg.getStreamName()+" removed.");
            } else {
                Log.write(MessageHandler.class, "StreamProperty "+msg.getStreamName()
                        +" was not found.");
            }
        }
    }

    private static void handleStreamResponse(ClientChannel ch, StreamResponseMessage msg) {
//        Log.write(MessageHandler.class, msg.getMessage());
        boolean result =StreamHandler.respondStream(msg.getStreamID(), msg.getUserID(), msg.isAccept());
        if (!result) {
            Log.error(MessageHandler.class, "Stream response unsuccessful");
        } else {
            Log.write(MessageHandler.class, "Stream response on "+msg.getStreamID()+" from "+msg.getUserID()+" for "+msg.isAccept());
        }
    }

    private static void handleStreamUpdate(ClientChannel ch, StreamUpdateMessage msg) {
        Log.write(MessageHandler.class, msg.getMessage());
        int streamID =StreamHandler.updateStream(msg.getStreamID(), msg.getUserID(), msg.getAffectedUserID(), msg.getAction());
        
        if (streamID != -1) {
            ClientHandler.writeAndFlush(new StreamNotifyMessage(msg.getUserID(), 
                    msg.getAffectedUserID(), streamID,
                    msg.getAction()));
        }
    }

    private static void handleAudioStream(ClientChannel ch, AudioStreamMessage msg) {
//        Log.write(MessageHandler.class, "Audio stream received on streamID: "
//                +as.getStreamID());
        StreamProperty sp =StreamHandler.getStreamProperty(msg.getUserID(), msg.getStreamName());
        if (sp != null) {
            ClientHandler.writeAndFlush(msg, sp.generateMatcher());
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +as.getStreamID());
        }
    }

    private static void handleVideoStream(ClientChannel ch, VideoStreamMessage msg) {
//        Log.write(MessageHandler.class, "Video stream received on streamID: "
//                +vs.getStreamID());
        StreamProperty sp =StreamHandler.getStreamProperty(msg.getUserID(), msg.getStreamName());
        if (sp != null) {
            ClientHandler.writeAndFlush(msg, sp.generateMatcher());
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +vs.getStreamID());
        }
    }
}