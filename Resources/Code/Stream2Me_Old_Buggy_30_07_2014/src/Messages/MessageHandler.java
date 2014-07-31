package Messages;

import Messages.UserConnection.*;
import Messages.Media.*;
import client.GUI.GUI;
import java.io.IOException;

public class MessageHandler {
    public static void handle(GUI userInterface, Message m) {
        switch (m.handle()) {
            case login:
                break;
            case greeting: 
                handleGreeting(userInterface, (Greeting)m);
                break;
            case newUser:
                handleNewUser(userInterface, (NewUser)m);
                break;
            case logout:
                handleLogout(userInterface, (Logout)m);
                break;
            case disconnect:
                break;
            case streamReply:
                break;
            case streamProperty:
                handleStreamProperty(userInterface, (StreamProperty)m);
                break;
            case auido:
                handleAudioStream(userInterface, (AudioStream)m);
                break;
            case string:
                handleStringMessage(userInterface, (StringMessage)m);
                break;
            case updateAvatar:
                break;
            case updateName:
                break;
            default:
                break;
        }
    }
    
    private static void handleGreeting(GUI userInterface, Greeting g) {
        System.out.println(g.getMessage());
        
        userInterface.setID(g.getUserID());
        userInterface.setupMyContactData();
    }
    
    private static void handleNewUser(GUI userInterface, NewUser nu) {
        if (nu.getUserID() != userInterface.getID()) {
            userInterface.getContactPane().addContact(nu.getUserID(), nu.getUsername());
        } else {
            System.out.println("I am new user");
        }
    }
    
    private static void handleLogout(GUI userInterface, Logout l) {
        userInterface.getContactPane().removeContact(l.getUserID());
        System.out.println("Removed User");
    }
    
    private static void handleStreamProperty(GUI userInterface, StreamProperty sp) {
        System.out.println("Audio Stream Property parsed");
        int myID =userInterface.getID();
        if (sp.contained(myID)) {
            userInterface.getContactPane().getContactProfile(myID).setIncomingAudio(sp.getType() == 1, sp.getStreamID());
        } else {
//            System.out.println("I am not on the list");
        }
    }
    
    private static void handleAudioStream(GUI userInterface, AudioStream as) {
        System.out.println("Audio message receivd");
        try {
            userInterface.getAudio().write(as.getBuffer());
        } catch (IOException ex) {
            System.err.println("AUDIOMESSAGE - ERORR writing");
        }
    }

    private static void handleStringMessage(GUI userInterface, StringMessage sm) {
        userInterface.getContactPane().acceptMessage(sm);
    }
}