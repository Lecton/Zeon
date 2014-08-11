package Messages;

import User.Client;
import Messages.UserConnection.Login;
import Messages.UserConnection.Logout;
import Messages.Media.AudioStream;
import Messages.Media.StreamNotify;
import Messages.Media.StreamProperty;
import Messages.Media.StreamResponse;
import Messages.Media.StreamUpdate;
import Messages.Media.VideoStream;
import Messages.Update.UpdateAvatar;
import Messages.Update.UpdateUsername;
import Messages.UserConnection.NewUser;
import Utils.Log;
import Messages.Update.UpdateList;
import Server.RelayServer;
import Server.StreamProperties;

public class MessageHandler {
    
    public static void handle(Client cc, RelayServer rs, Message m) {
        switch (m.handle()) {
            case login:
                handleLogin(cc, rs, (Login)m);
                break;
            case logout:
                handleLogout(cc, rs, (Logout)m);
                break;
                
            case string: 
                handleStringMessage(cc, rs, (StringMessage)m);
                break;
            case updateAvatar:
                handleUpdateAvatar(cc, rs, (UpdateAvatar)m);
                break;
            case updateName:
                handleUpdateUsername(cc, rs, (UpdateUsername)m);
                break;
            case updateList:
                handleListRequest(cc, rs, (UpdateList)m);
                break;
                
            case streamProperty:
                handleStreamProperty(cc, rs, (StreamProperty)m);
                break;
            case streamReply:
                handleStreamResponse(cc, rs, (StreamResponse)m);
                break;
            case streamUpdate:
                handleStreamUpdate(cc, rs, (StreamUpdate)m);
                break;
            case auido:
                handleAudioStream(cc, rs, (AudioStream)m);
                break;
            case video:
                handleVideoStream(cc, rs, (VideoStream)m);
                break;
                
            case greeting:
                break;
            default:
                break;
        }
    }
    
    private static void handleLogin(Client cc, RelayServer rs, Login l) {
        NewUser nu =null;
        
        cc.send(rs.userLogin(cc, l.getSender(), l.getPasswordHash()));
    }
    
    private static void handleLogout(Client cc, RelayServer rs, Logout l) {
        Log.write(MessageHandler.class, "User logged out");
        
    }
    
    
    
    private static void handleStringMessage(Client cc, RelayServer rs, 
            StringMessage sm) {
        rs.relayDefault(cc, sm);
    }
    
    private static void handleUpdateUsername(Client cc, RelayServer rs, 
            UpdateUsername uu) {
        rs.getDB().updateUsername(uu.getUserID(), uu.getUsername());
        
//        cc.setName(uu.getUsername());
        rs.relayDefault(cc, uu);
        Log.write(MessageHandler.class, "Update username");
    }
    
    private static void handleUpdateAvatar(Client cc, RelayServer rs, 
            UpdateAvatar ua) {
        rs.getDB().updateAvatar(ua.getUserID(), ua.getAvatar());
        
        rs.relayDefault(cc, ua);
        Log.write(MessageHandler.class, "Update avatar");
    }
    
    private static void handleListRequest(Client cc, RelayServer rs,
            UpdateList ul) {
        if (cc.isLoggedIn()) {
            rs.updateUserConnection(cc);
        }
    }
    
    private static void handleStreamProperty(Client cc, RelayServer rs, 
            StreamProperty sp) {
        if (sp.getType() == 1) {
            rs.createStreamProperty(sp.getUserID(), sp.getStreamID());
            Log.write(MessageHandler.class, "StreamProperty "+
                    sp.getStreamID()+" created.");
        } else {
            rs.removeStreamProperty(sp.getUserID(), sp.getStreamID());
            Log.write(MessageHandler.class, "StreamProperty "+
                    sp.getStreamID()+" removed.");
        }
    }
    
    private static void handleStreamUpdate(Client cc, RelayServer rs, 
            StreamUpdate su) {
        Log.write(MessageHandler.class, su.getMessage());
        
        StreamProperties sp =rs.getStreamProperties(su.getStreamID());
        if (sp != null) {
            if (su.getAction() == 1) {
                sp.add(su.getUserID(), su.getAffectedUserID());
            } else if (su.getAction() == 0) {
                sp.remove(su.getUserID(), su.getAffectedUserID());
            }
            
            rs.relayDefault(cc,new StreamNotify(su.getUserID(), 
                    su.getAffectedUserID(), su.getStreamID(),
                    su.getAction()));
        }
        
    }
    
    private static void handleStreamResponse(Client cc, RelayServer rs, 
            StreamResponse sr) {
        Log.write(MessageHandler.class, sr.getMessage());
        
        StreamProperties sp =rs.getStreamProperties(sr.getStreamID());
        if (sp != null) {
            sp.accept(sr.getUserID());
        }
    }
    
    private static void handleAudioStream(Client cc, RelayServer rs, 
            AudioStream as) {
//        Log.write(MessageHandler.class, "Audio stream received on streamID: "
//                +as.getStreamID());
        StreamProperties sp =rs.getStreamProperties(as.getStreamID());
        if (sp != null) {
            for (int target: sp.getTargets()) {
                as.setTargetID(target);
                rs.relayDefault(cc, as);
            }
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +as.getStreamID());
        }
    }

    private static void handleVideoStream(Client cc, RelayServer rs, VideoStream vs) {
//        Log.write(MessageHandler.class, "Video stream received on streamID: "
//                +vs.getStreamID());
        StreamProperties sp =rs.getStreamProperties(vs.getStreamID());
        if (sp != null) {
            for (int target: sp.getTargets()) {
                vs.setTargetID(target);
                rs.relayDefault(cc, vs);
            }
        } else {
//            Log.write(MessageHandler.class, 
//                    "No Stream Property set for this audio stream. ID: "
//                            +vs.getStreamID());
        }
    }
}
