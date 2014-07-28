/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server;

import Messages.UserConnection.Logon;
import Messages.UserConnection.Logout;
import Messages.*;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    
    public static void handle(Client cc, RelayServer rs, Message m) {
        switch (m.handle()) {
            case logon:
                
        }
    }
    
    private static void handle(Client cc, RelayServer rs, Logon m) {
        System.out.println("Client init");
    }
    
    private static void handle(Client cc, RelayServer rs, UpdateUsername m) {
        System.out.println("Update username");
    }
    
    private static void handle(Client cc, RelayServer rs, UpdateAvatar m) {
        System.out.println("Update avatar");
    }
    
    private static void handle(Client cc, RelayServer rs, Logout m) {
        System.out.println("Disconnect user");
    }
}
