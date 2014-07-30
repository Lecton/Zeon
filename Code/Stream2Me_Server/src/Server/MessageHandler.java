package Server;

import Messages.UserConnection.Login;
import Messages.UserConnection.Logout;
import Messages.Media.AudioStream;
import Messages.Media.StreamProperty;
import Messages.Media.StreamResponse;
import Messages.Message;
import Messages.StringMessage;
import Messages.UpdateAvatar;
import Messages.UpdateUsername;
import Messages.UserConnection.Greeting;
import Messages.UserConnection.NewUser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageHandler {
    
    public static void handle(Client cc, RelayServer rs, Message m) {
        switch (m.handle()) {
            case login:
                handleLogin(cc, rs, (Login)m);
                break;
            case logout:
                handleLogout(cc, rs, (Logout)m);
                break;
            case disconnect:
//                handle(cc, rs, (Disconnect)m);
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
                
                
            case streamProperty:
                handleStreamProperty(cc, rs, (StreamProperty)m);
                break;
            case streamReply:
                handleStreamResponse(cc, rs, (StreamResponse)m);
                break;
            case auido:
                handleAudioStream(cc, rs, (AudioStream)m);
                break;
                
            case greeting:
                break;
            default:
                break;
        }
    }
    
    private static void handleLogin(Client cc, RelayServer rs, Login m) {
        NewUser nu =null;
        if ((nu =rs.userLogin(cc, m.getSender(), m.getPasswordHash())) != null) {
            rs.relayDefault(cc, nu);
            cc.send(new Greeting(cc.getID()));
            rs.updateUserConnection(cc);
        } else {
            
        }
//        cc.send(new NewUse);
    }
    
    
    private static void handleStringMessage(Client cc, RelayServer rs, StringMessage m) {
        rs.relayDefault(cc, m);
    }
    
    private static void handleUpdateUsername(Client cc, RelayServer rs, UpdateUsername m) {
        cc.setName(m.getUsername());
        rs.relayDefault(cc, m);
        System.out.println("Update username");
    }
    
    private static void handleUpdateAvatar(Client cc, RelayServer rs, UpdateAvatar m) {
        rs.relayDefault(cc, m);
        System.out.println("Update avatar");
    }
    
    private static void handleLogout(Client cc, RelayServer rs, Logout m) {
        System.out.println("Disconnect user");
//        rs.logout();
    }
    
    private static void handleStreamProperty(Client cc, RelayServer rs, StreamProperty sp) {
        if (sp.getType() == 1) {
            cc.addStreamProperty(sp.getStreamID(), sp.getAllowed());
            System.out.println("StreamProperty added "+sp.getStreamID());
        }
        
        rs.relayDefault(cc, sp);
    }
    
    private static void handleStreamResponse(Client cc, RelayServer rs, StreamResponse sr) {
        System.out.println(sr.getMessage());
        cc.getStreamProperties(sr.getStreamID()).accept(sr.getUserID());
        
        rs.relayDefault(cc, sr);
    }
    
    private static void handleAudioStream(Client cc, RelayServer rs, AudioStream as) {
        StreamProperties sp =cc.getStreamProperties(as.getStreamID());
        if (sp != null) {
            for (int target: sp.getTargets()) {
                as.setTargetID(target);
                rs.relayDefault(cc, as);
            }
        } else {
            System.out.println("No Stream Property set for this audio stream. ID: "+as.getStreamID());
        }
    }
}
