/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messageChannel;

import channel.ClientChannel;
import channel.group.ClientHandler;
import channel.group.matcher.ClientMatcher;
import channel.group.matcher.ConnectionMatcher;
import channel.group.matcher.StringMatcher;
import connection.bootstrap.Handler;
import core.database.DatabaseHandler;
import core.database.objects.BaseUser;
import core.database.objects.Settings;
import core.database.objects.StreamProperty;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.StringMessage;
import messages.media.AudioStreamMessage;
import messages.media.communication.StreamNotifyMessage;
import messages.media.creation.StreamPropertyRequestMessage;
import messages.media.communication.StreamResponseMessage;
import messages.media.communication.StreamUpdateMessage;
import messages.media.VideoStreamMessage;
import messages.media.creation.StreamPropertyMessage;
import messages.media.creation.StreamTerminateMessage;
import messages.settings.group.GroupJoinMessage;
import messages.settings.group.GroupJoinRequestMessage;
import messages.settings.group.GroupListMessage;
import messages.settings.group.GroupListRequestMessage;
import messages.settings.SettingsMessage;
import messages.settings.SettingsRequestMessage;
import messages.settings.group.GroupCreateMessage;
import messages.settings.group.GroupCreateRequestMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateListMessage;
import messages.update.UpdateProfileMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LoginMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import messages.userConnection.registration.CheckEmailMessage;
import messages.userConnection.registration.CheckUsernameMessage;
import messages.userConnection.registration.RegisterMessage;

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
            case updateProfile:
                handleUpdateProfile(ctx.channel(), (UpdateProfileMessage)msg);
                break;
            case updateList:
                handleListRequest(ctx.channel(), (UpdateListMessage)msg);
                break;

            case streamPropertyRequest:
                handleStreamPropertyRequest(ctx.channel(), (StreamPropertyRequestMessage)msg);
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
                
            case checkUsername:
                handleCheckUsername(ctx.channel(), (CheckUsernameMessage)msg);
                break;
            case checkEmail:
                handleCheckEmail(ctx.channel(), (CheckEmailMessage)msg);
                break;
            case register:
                handleRegister(ctx.channel(), (RegisterMessage)msg);
                break;
                
            case settingsRequest:
                handleSettingsRequest(ctx.channel(), (SettingsRequestMessage)msg);
                break;
            case settingsGroupListRequest:
                handleSettingsGroupListRequest(ctx.channel(), (GroupListRequestMessage)msg);
                break;
            case settingsGroupJoinRequest:
                handleSettingsGroupJoinRequest(ctx.channel(), (GroupJoinRequestMessage)msg);
                break;
            case settingsGroupCreateRequest:
                handleSettingsGroupCreateRequest(ctx.channel(), (GroupCreateRequestMessage)msg);
            default:
                break;
        }
    }
    
    private static void handleLogin(Channel ch, LoginMessage msg) {
        GreetingMessage g =DatabaseHandler.userHandler.userLogin(ch, msg);
        if (g != null) {
            ch.writeAndFlush(g);
        } else {
//            System.out.println("Login error");
        }
    }
    
    private static void handleLogout(Channel ch, LogoutMessage msg) {
        BaseUser u =DatabaseHandler.userHandler.getUser(msg.getUserID());
        if (u == null) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.parse("ERROR"), 
                    "Client closed connection, but client could not be found.");
            return;
        }

        ClientChannel channel =ClientHandler.remove(ch, u.getGroupID());
        boolean contained =ClientHandler.contains(channel, channel.getUserID());
        Handler.connections.add(ch);
        System.out.println("Second life: "+contained);
        if (!contained) {
            LogoutMessage message =new LogoutMessage(u.getUserID(), messages.Message.ALL);
            message.setTargetGroupID(u.getGroupID());
            ClientHandler.writeAndFlush(DatabaseHandler.userHandler.getGroupID(msg.getUserID()), message);
        }
        DatabaseHandler.userHandler.logoff(u.getUserID(), channel.getConnectionID());
    }
    
    private static void handleStringMessage(Channel ch, StringMessage msg) {
        Message message =DatabaseHandler.stringMessageHandler.handleStringMessage(msg);
        String groupID =DatabaseHandler.userHandler.getGroupID(msg.getUserID());
        String connectionID =ClientHandler.getConnectionID(ch, groupID);
        ClientHandler.writeAndFlush(groupID, message, new StringMatcher(msg.getTargetID(), connectionID, msg.getUserID(), groupID));
    }

    private static void handleUpdateAvatar(Channel ch, UpdateAvatarMessage msg) {
        try {
        Message message =DatabaseHandler.userHandler.updateAvatar(msg);
        ClientHandler.writeAndFlush(DatabaseHandler.userHandler.getGroupID(msg.getUserID()), message);
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                "Update avatar.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleUpdateProfile(Channel ch, UpdateProfileMessage msg) {
        Message message =DatabaseHandler.userHandler.updateProfile(msg);
        ClientHandler.writeAndFlush(DatabaseHandler.userHandler.getGroupID(msg.getUserID()), message);
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                    "Update profile.");
    }

    private static void handleListRequest(Channel ch, UpdateListMessage msg) {
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                "Update list request.");
        String groupUsers[] =DatabaseHandler.userHandler.getGroupUsers(msg.getUserID());
        for (String userID: groupUsers) {
            NewUserMessage message =DatabaseHandler.userHandler.getNewUserMessage(userID, msg.getUserID());
            String groupID =DatabaseHandler.userHandler.getGroupID(msg.getUserID());
            String connectionID =ClientHandler.getConnectionID(ch, groupID);
            if (message != null) {
                ClientHandler.writeAndFlush(groupID, message, new ConnectionMatcher(connectionID));
            }
        }
    }

    private static void handleStreamPropertyRequest(Channel ch, StreamPropertyRequestMessage msg) {
        try {
        if (msg.getAction()) {
            String connectionID =ClientHandler.getConnectionID(ch, msg.getUserID());
            
            StreamPropertyMessage message =DatabaseHandler.streamHandler.createStreamProperty(msg.getUserID(), connectionID, 
                    msg.getStreamName(), msg.getType());
            
            if (message.isSuccessful()) {
                Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                        "StreamProperty "+msg.getStreamName()+" created.");
            }
            String groupID =DatabaseHandler.userHandler.getGroupID(msg.getUserID());
            ClientHandler.writeAndFlush(groupID, message, new ClientMatcher(msg.getUserID()));
        } else {
            try {
                StreamProperty sp =DatabaseHandler.streamHandler.removeStreamProperty(msg.getUserID(), msg.getStreamID());
                if (sp != null) {
                    StreamTerminateMessage terminate =MessageBuilder.generateStreamTerminate(msg.getUserID(), msg.getStreamID(), sp.getType());
                    String groupID =sp.getGroupID();
                    ClientHandler.writeAndFlush(groupID, terminate, new ClientMatcher(msg.getUserID()));
                    StreamNotifyMessage message =new StreamNotifyMessage(
                            msg.getUserID(), Message.ALL, sp.getStreamID(), sp.getType(), false);
                    ClientHandler.writeAndFlush(groupID, message, sp.generateMatcher());
                    Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                            "StreamProperty "+msg.getStreamName()+" removed.");
                } else {
                    Logger.getLogger(MessageHandler.class.getName()).log(Level.WARNING, 
                        "StreamProperty "+msg.getStreamName()+" was not found.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleStreamResponse(Channel ch, StreamResponseMessage msg) {
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                    msg.getMessage()+".");
        String connectionID =ClientHandler.getConnectionID(ch, DatabaseHandler.userHandler.getGroupID(msg.getUserID()));
        boolean result =DatabaseHandler.streamHandler.respondStream(msg.getStreamID(), msg.getUserID(), connectionID, msg.isAccept());
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
        String affectedConnectionID =DatabaseHandler.userHandler.getConnectionID(msg.getAffectedUserID());
        boolean success =DatabaseHandler.streamHandler.updateStream(msg.getStreamID(), msg.getUserID(), msg.getAffectedUserID(), affectedConnectionID, msg.getAction());
        if (success) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                        "Stream Update successful.");
            String groupID =DatabaseHandler.userHandler.getGroupID(msg.getUserID());
            ClientHandler.writeAndFlush(groupID, new StreamNotifyMessage(msg.getUserID(), 
                    msg.getAffectedUserID(), msg.getStreamID(), msg.getType(),
                    msg.getAction()));
        } else {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.WARNING, 
                        "Could not update the connection.");
        }
    }

    private static void handleAudioStream(Channel ch, AudioStreamMessage msg) {
//        Log.write(MessageHandler.class, "Audio stream received on streamID: "
//                +as.getStreamID());
        StreamProperty sp =DatabaseHandler.streamHandler.getStreamProperty(msg.getUserID(), msg.getStreamID(), true);
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
        StreamProperty sp =DatabaseHandler.streamHandler.getStreamProperty(msg.getUserID(), msg.getStreamID(), true);
        
        if (sp != null) {
            ClientHandler.writeAndFlush(sp.getGroupID(), msg, sp.generateMatcher());
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +vs.getStreamID());
        }
    }

    private static void handleCheckUsername(Channel ch, CheckUsernameMessage msg) {
        boolean result =DatabaseHandler.registrationHandler.checkUsername(msg);
        msg.setValid(result);
        ch.writeAndFlush(msg);
    }

    private static void handleCheckEmail(Channel ch, CheckEmailMessage msg) {
        boolean result =DatabaseHandler.registrationHandler.checkEmail(msg);
        msg.setValid(result);
        ch.writeAndFlush(msg);
    }

    private static void handleRegister(Channel ch, RegisterMessage msg) {
        Message message =DatabaseHandler.registrationHandler.register(msg);
        ch.writeAndFlush(message);
    }

    private static void handleSettingsRequest(Channel ch, SettingsRequestMessage msg) {
        Settings settings =DatabaseHandler.settingHandler.getSettings(msg.getUserID());
        if (settings != null) {
            SettingsMessage sm =new SettingsMessage(settings.getGroupID(), settings.getGroupName(), settings.getOwnerName(), settings.isOwner(msg.getUserID()));
            ch.writeAndFlush(sm);
        } else {
            ch.writeAndFlush(new SettingsMessage());
        }
    }

    private static void handleSettingsGroupListRequest(Channel ch, GroupListRequestMessage msg) {
        Map<String, String> groups =DatabaseHandler.settingHandler.getGroups();
        GroupListMessage sm =new GroupListMessage(groups);
        ch.writeAndFlush(sm);
    }
    
    private static void handleSettingsGroupJoinRequest(Channel ch, GroupJoinRequestMessage msg) {
        BaseUser u =DatabaseHandler.userHandler.getUser(msg.getUserID());
        
        GroupJoinMessage gjm =DatabaseHandler.settingHandler.groupJoin(msg.getUserID(), msg.getGroupID(), msg.getPassword());
        
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                "Join was "+(gjm.isSuccessful()? "successful" : "unsuccessful")+".");
        if (gjm.isSuccessful()) {
            if (u == null) {
                Logger.getLogger(MessageHandler.class.getName()).log(Level.parse("ERROR"), 
                        "Client closed connection, but client could not be found.");
                return;
            }

            ClientChannel channel =ClientHandler.remove(ch, u.getGroupID());
//            System.out.println(channel == null);
            String uid =channel.getUserID();
            boolean contained =ClientHandler.contains(channel, uid);
            if (!contained) {
                LogoutMessage message =new LogoutMessage(u.getUserID(), messages.Message.ALL);
                message.setTargetGroupID(u.getGroupID());
                ClientHandler.writeAndFlush(u.getGroupID(), message);
            }
            DatabaseHandler.userHandler.changeUserGroup(channel, msg.getUserID(), msg.getGroupID());
        }
        
        ch.writeAndFlush(gjm);
    }
    
    private static void handleSettingsGroupCreateRequest(Channel ch, GroupCreateRequestMessage msg) {
        BaseUser u =DatabaseHandler.userHandler.getUser(msg.getUserID());
        
        GroupCreateMessage gcm =DatabaseHandler.settingHandler.groupCreate(msg.getUserID(), msg.getGroupName(), msg.getPassword());
        
        Logger.getLogger(MessageHandler.class.getName()).log(Level.INFO, 
                "Create was "+(gcm.isSuccessful()? "successful" : "unsuccessful")+".");
        if (gcm.isSuccessful()) {
            if (u == null) {
                Logger.getLogger(MessageHandler.class.getName()).log(Level.parse("ERROR"), 
                        "Client closed connection, but client could not be found.");
                return;
            }

            ClientChannel channel =ClientHandler.remove(ch, u.getGroupID());
            String uID =channel.getUserID();
            boolean contained =ClientHandler.contains(channel, channel.getUserID());
            if (!contained) {
                LogoutMessage message =new LogoutMessage(u.getUserID(), messages.Message.ALL);
                message.setTargetGroupID(u.getGroupID());
                ClientHandler.writeAndFlush(u.getGroupID(), message);
            }
            DatabaseHandler.userHandler.changeUserGroup(channel, msg.getUserID(), gcm.getGroupID());
        }
        
        ch.writeAndFlush(gcm);
    }
}