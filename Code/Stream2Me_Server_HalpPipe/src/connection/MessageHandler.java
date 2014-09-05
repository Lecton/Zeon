/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import client.Client;
import io.netty.channel.ChannelHandlerContext;
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
import core.Server;
import core.StreamHandler;
import core.StreamProperties;
import java.io.IOException;
import utils.Log;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    public static void handle(ChannelHandlerContext ctx, Message msg) {
        switch (msg.handle()) {
            case login:
                handleLogin(ctx, (LoginMessage)msg);
                break;
            case logout:
                handleLogout(ctx, (LogoutMessage)msg);
                break;
                
            case string: 
                handleStringMessage(ctx, (StringMessage)msg);
                break;
            case updateAvatar:
                handleUpdateAvatar(ctx, (UpdateAvatarMessage)msg);
                break;
            case updateName:
                handleUpdateName(ctx, (UpdateNameMessage)msg);
                break;
            case updateList:
                handleListRequest(ctx, (UpdateListMessage)msg);
                break;
                
            case streamProperty:
                handleStreamProperty(ctx, (StreamPropertyMessage)msg);
                break;
            case streamReply:
                handleStreamResponse(ctx, (StreamResponseMessage)msg);
                break;
            case streamUpdate:
                handleStreamUpdate(ctx, (StreamUpdateMessage)msg);
                break;
            case auido:
                handleAudioStream(ctx, (AudioStreamMessage)msg);
                break;
            case video:
                handleVideoStream(ctx, (VideoStreamMessage)msg);
                break;
                
            case greeting:
                break;
            default:
                break;
        }
    }
    
    private static void handleLogin(ChannelHandlerContext ctx, LoginMessage msg) {
        GreetingMessage g =Server.userLogin(ctx.channel(), msg.getSender(), msg.getPasswordHash());
        ctx.channel().writeAndFlush(g);
    }
    
    private static void handleLogout(ChannelHandlerContext ctx, LogoutMessage msg) {
        try {
            Server.closeConnection(ctx.channel());
        } catch (IOException e) {
            Log.error(MessageHandler.class, e.getMessage());
        }
//        Log.error(MessageHandler.class, "Logout not implemented yet");
    }
    
    private static void handleStringMessage(ChannelHandlerContext ctx, StringMessage msg) {
        Server.relay(msg, ctx.channel());
    }

    private static void handleUpdateAvatar(ChannelHandlerContext ctx, UpdateAvatarMessage msg) {
        Server.getDatabase().updateAvatar(msg.getUserID(), msg.getAvatar());
        
        Server.relay(msg, ctx.channel());
        Log.write(MessageHandler.class, "Update avatar");
    }

    private static void handleUpdateName(ChannelHandlerContext ctx, UpdateNameMessage msg) {
        Server.getDatabase().updateName(msg.getUserID(), msg.getName(), msg.getSurname());
        
        Server.relay(msg, ctx.channel());
        Log.write(MessageHandler.class, "Update username");
    }
    
   /* private static void handleUpdateTitle(ChannelHandlerContext ctx, UpdateTitle msg){
        
    }*/

    private static void handleListRequest(ChannelHandlerContext ctx, UpdateListMessage msg) {
        Log.write(MessageHandler.class, "Update list request");
        try {
            Client client = Server.getClient(ctx.channel());
            if (client.isLoggedIn()) {
                Server.updateUserConnection(client);
            }
        } catch (ClassCastException e) {
            
        }
    }

    private static void handleStreamProperty(ChannelHandlerContext ctx, StreamPropertyMessage msg) {
        if (msg.getType() == 1) {
            StreamHandler.createStreamProperty(msg.getUserID(), msg.getStreamID());
            Log.write(MessageHandler.class, "StreamProperty "+
                    msg.getStreamID()+" created.");
        } else {
            StreamProperties sp =StreamHandler.removeStreamProperty(msg.getUserID(), msg.getStreamID());
            if (sp != null) {
                for (int target: sp.getTargets()) {
                    Server.relay(new StreamNotifyMessage(msg.getUserID(), target, 
                            msg.getStreamID(), 0), ctx.channel());
                }
                Log.write(MessageHandler.class, "StreamProperty "+
                        msg.getStreamID()+" removed.");
            } else {
                Log.write(MessageHandler.class, "StreamProperty "+msg.getStreamID()
                        +" was not found.");
            }
        }
    }

    private static void handleStreamResponse(ChannelHandlerContext ctx, StreamResponseMessage msg) {
//        Log.write(MessageHandler.class, msg.getMessage());
        boolean result =StreamHandler.respondStream(msg.getStreamID(), msg.getUserID(), msg.isAccept());
        if (!result) {
            Log.error(MessageHandler.class, "Stream response unsuccessful");
        } else {
            Log.write(MessageHandler.class, "Stream response on "+msg.getStreamID()+" from "+msg.getUserID()+" for "+msg.isAccept());
        }
    }

    private static void handleStreamUpdate(ChannelHandlerContext ctx, StreamUpdateMessage msg) {
        Log.write(MessageHandler.class, msg.getMessage());
        StreamHandler.updateStream(msg.getStreamID(), msg.getUserID(), msg.getAffectedUserID(), msg.getAction());
        
        Server.relay(new StreamNotifyMessage(msg.getUserID(), 
                msg.getAffectedUserID(), msg.getStreamID(),
                msg.getAction()), ctx.channel());
    }

    private static void handleAudioStream(ChannelHandlerContext ctx, AudioStreamMessage msg) {
//        Log.write(MessageHandler.class, "Audio stream received on streamID: "
//                +as.getStreamID());
        StreamProperties sp =StreamHandler.getStreamProperties(msg.getStreamID());
        if (sp != null) {
            for (int target: sp.getTargets()) {
                msg.setTargetID(target);
                Server.relay(msg, ctx.channel());
            }
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +as.getStreamID());
        }
    }

    private static void handleVideoStream(ChannelHandlerContext ctx, VideoStreamMessage msg) {
//        Log.write(MessageHandler.class, "Video stream received on streamID: "
//                +vs.getStreamID());
        StreamProperties sp =StreamHandler.getStreamProperties(msg.getStreamID());
        if (sp != null) {
            for (int target: sp.getTargets()) {
                msg.setTargetID(target);
                Server.relay(msg, ctx.channel());
            }
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +vs.getStreamID());
        }
    }
}