package core;

import utils.Log;
import channel.group.ClientChannelGroup;
import client.Client;
import connection.ServerHandler;
import connection.MessageBuilder;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.io.IOException;
import messages.Message;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernhard
 */
public class Server {
    private static final ChannelGroup clients =new ClientChannelGroup(GlobalEventExecutor.INSTANCE);
    public static final ChannelGroup connections =new ClientChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final Database DB =new Database();
    
    public static int SERVER =-10;
    public static int ALL =-11;
    public static int ERROR =-15;
    public static int IGNORE =-1;
    
    public Server() {
    }
    
    public static void addClient(int userID, Channel incomming) {
        clients.add(new Client(userID, incomming));
    }

    public static boolean clientContains(Channel incomming) {
        if (!connections.contains(incomming)) {
            Object[] o = clients.toArray();
            for (int i = 0; i < o.length; i++) {
                Client c =(Client)o[i];
                if (c.ch == incomming) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    public static void relay(Message msg, Channel sender) {
        Log.write(Server.class, "Relay "+msg.getClass().getSimpleName()+" to: "+msg.getTargetID());
        
        if (msg.getTargetID() == messages.Message.IGNORE) {
        } else if (msg.getTargetID() == messages.Message.SERVER) {
            Log.write(Server.class, "Server message received");
        } else if (msg.getTargetID() == messages.Message.ERROR) {
            Log.error(Server.class, "Error in message relay on message type "+msg.handle());
        } else if (msg.getTargetID() == messages.Message.ALL) {
            for (Channel ch: clients) {
                Client c =(Client)ch;
                if (!c.getChannel().equals(sender)){
                    c.writeAndFlush(msg);
                }
            }
        } else if (msg.getTargetID() < 0) {
            Log.error(Server.class, "Unknown target type "+msg.getTargetID());
        } else {
            for (Channel ch: clients) {
                Client c =(Client)ch;
                if (c.getUserID() == msg.getTargetID()) {
                    c.writeAndFlush(msg);
                    break;
                }
            }
        }
    }
    
//    public static Client getClient(int userID) {
//        for (Channel ch: clients) {
//            Client c =(Client)ch;
//            if (c.getUserID() == userID) {
//                return c;
//            }
//        }
//        
//        return null;
//    }
    
    public static Client getClient(Channel channel) {
        for (Channel ch: clients) {
            Client c =(Client)ch;
            if (c.getChannel() == channel) {
                return c;
            }
        }
        return null;
    }
    
    public static Database getDatabase() {
        return DB;
    }
    
    public static GreetingMessage userLogin(Channel ch, String username, String passwordHash) {
        int userID = DB.login(username, passwordHash);
        
        Log.write(Server.class, "USERLOGIN - ID: "+userID);
        
        if (userID != ERROR) {
            connections.remove(ch);
            clients.add(new Client(userID, ch));
            
            NewUserMessage newUserMessage =MessageBuilder.generateNewUser(DB.getUserProfile(userID));
            for (Channel channel: clients) {
                Client cl =(Client)channel;
                channel.writeAndFlush(newUserMessage);
            }
            return MessageBuilder.generateGreeting(DB.getUserProfile(userID), true);
        }
        
        return new GreetingMessage(false);
    }
    
    public static void closeConnection(Channel ch) throws IOException {
        Client user =getClient(ch);
        
        if (user == null) {
            Log.error(Server.class, "Client closed connection, but client could not be found.");
            return;
        }
        
        if (user.isLoggedIn()) {
            clients.remove(user);
            connections.add(ch);
            for (Channel client: clients) {
                Client cl =(Client)client;
                cl.writeAndFlush(new LogoutMessage(user.getUserID(), messages.Message.ALL));
            }

            DB.logoff(user.getUserID());
            user.setLoggedIn(false);

            Log.write(Server.class, "Clients: " + clients.size());
        }
    }
    
    public static void updateUserConnection(Client client) {
        for (Channel ch: clients) {
            Client cl =(Client)ch;
            if (!client.equals(cl)) {
                client.writeAndFlush(DB.getNewUserMessage(cl.getUserID()));
            }
        }
    }

    
    public static void main(String[] args) {
        new ServerHandler(2014).run();
    }
}
