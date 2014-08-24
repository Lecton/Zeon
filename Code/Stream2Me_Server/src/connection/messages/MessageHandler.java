/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messages;

import Messages.Media.AudioStream;
import Messages.Media.StreamNotify;
import Messages.Media.StreamProperty;
import Messages.Media.StreamResponse;
import Messages.Media.StreamUpdate;
import Messages.Media.VideoStream;
import Messages.Message;
import Messages.StringMessage;
import Messages.Update.UpdateAvatar;
import Messages.Update.UpdateList;
import Messages.Update.UpdateUsername;
import Messages.UserConnection.Greeting;
import Messages.UserConnection.Login;
import Messages.UserConnection.Logout;
import server.Server;
import server.StreamHandler;
import server.StreamProperties;
import utils.Log;
import client.Client;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    public static void handle(ChannelHandlerContext ctx, Message msg) {
        switch (msg.handle()) {
            case login:
                handleLogin(ctx, (Login)msg);
                break;
            case logout:
                handleLogout(ctx, (Logout)msg);
                break;
                
            case string: 
                handleStringMessage(ctx, (StringMessage)msg);
                break;
            case updateAvatar:
                handleUpdateAvatar(ctx, (UpdateAvatar)msg);
                break;
            case updateName:
                handleUpdateUsername(ctx, (UpdateUsername)msg);
                break;
            case updateList:
                handleListRequest(ctx, (UpdateList)msg);
                break;
                
            case streamProperty:
                handleStreamProperty(ctx, (StreamProperty)msg);
                break;
            case streamReply:
                handleStreamResponse(ctx, (StreamResponse)msg);
                break;
            case streamUpdate:
                handleStreamUpdate(ctx, (StreamUpdate)msg);
                break;
            case auido:
                handleAudioStream(ctx, (AudioStream)msg);
                break;
            case video:
                handleVideoStream(ctx, (VideoStream)msg);
                break;
                
            case greeting:
                break;
            default:
                break;
        }
    }
    
    private static void handleLogin(ChannelHandlerContext ctx, Login msg) {
        Greeting g = Server.userLogin(ctx.channel(), msg.getSender(), msg.getPasswordHash());
        ctx.channel().writeAndFlush(g);
    }
    
    private static void handleLogout(ChannelHandlerContext ctx, Logout msg) {
        Log.error(new MessageHandler(), "Logout not implemented yet");
    }
    
    private static void handleStringMessage(ChannelHandlerContext ctx, StringMessage msg) {
        Server.relay(msg, ctx.channel());
    }

    private static void handleUpdateAvatar(ChannelHandlerContext ctx, UpdateAvatar msg) {
        Server.getDatabase().updateAvatar(msg.getUserID(), msg.getAvatar());
        
        Server.relay(msg, ctx.channel());
        Log.write(new MessageHandler(), "Update avatar");
    }

    private static void handleUpdateUsername(ChannelHandlerContext ctx, UpdateUsername msg) {
        Server.getDatabase().updateUsername(msg.getUserID(), msg.getUsername());
        
        Server.relay(msg, ctx.channel());
        Log.write(new MessageHandler(), "Update username");
    }
    
   /* private static void handleUpdateTitle(ChannelHandlerContext ctx, UpdateTitle msg){
        Server.getDatabase().updateTitle(msg.getUserID(), msg.getUsername());
        Server.relay(msg, ctx.channel());
        * Log.write(new MessageHandler(), "Update username");
    }*/

    private static void handleListRequest(ChannelHandlerContext ctx, UpdateList msg) {
        Log.write(new MessageHandler(), "Update list request");
        try {
            Client client = Server.getClient(ctx.channel());
            if (client.isLoggedIn()) {
                Server.updateUserConnection(client);
            }
        } catch (ClassCastException e) {
            
        }
    }

    private static void handleStreamProperty(ChannelHandlerContext ctx, StreamProperty msg) {
        if (msg.getType() == 1) {
            StreamHandler.createStreamProperty(msg.getUserID(), msg.getStreamID());
            Log.write(new MessageHandler(), "StreamProperty "+
                    msg.getStreamID()+" created.");
        } else {
            StreamHandler.removeStreamProperty(msg.getUserID(), msg.getStreamID());
            Log.write(new MessageHandler(), "StreamProperty "+
                    msg.getStreamID()+" removed.");
        }
    }

    private static void handleStreamResponse(ChannelHandlerContext ctx, StreamResponse msg) {
        Log.write(new MessageHandler(), msg.getMessage());
        StreamHandler.respondStream(msg.getStreamID(), msg.getUserID(), msg.isAccept());
    }

    private static void handleStreamUpdate(ChannelHandlerContext ctx, StreamUpdate msg) {
        Log.write(new MessageHandler(), msg.getMessage());
        StreamHandler.updateStream(msg.getStreamID(), msg.getUserID(), msg.getAffectedUserID(), msg.getAction());
        
        Server.relay(new StreamNotify(msg.getUserID(), 
                msg.getAffectedUserID(), msg.getStreamID(),
                msg.getAction()), ctx.channel());
    }

    private static void handleAudioStream(ChannelHandlerContext ctx, AudioStream msg) {
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

    private static void handleVideoStream(ChannelHandlerContext ctx, VideoStream msg) {
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