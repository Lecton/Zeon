package server;


import server.database.Database;
import Messages.Message;
import Messages.UserConnection.Greeting;
import Messages.UserConnection.Logout;
import Messages.UserConnection.NewUser;
import utils.Log;
import channel.group.ClientChannelGroup;
import client.Client;
import connection.ServerHandler;
import connection.messages.MessageBuilder;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.io.IOException;

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
                if (c.getChannel() == incomming) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    public static void relay(Message msg, Channel sender) {
        Log.write(new Server(), "Relay "+msg.getClass().getSimpleName()+" to: "+msg.getTargetID());
        
        if (msg.getTargetID() == Messages.Message.IGNORE) {
        } else if (msg.getTargetID() == Messages.Message.SERVER) {
            Log.write(new Server(), "Server message received");
        } else if (msg.getTargetID() == Messages.Message.ERROR) {
            Log.error(new Server(), "Error in message relay on message type "+msg.handle());
        } else if (msg.getTargetID() == Messages.Message.ALL) {
            for (Channel ch: clients) {
                Client c =(Client)ch;
                if (!c.getChannel().equals(sender)){
                    c.writeAndFlush(msg);
                }
            }
        } else if (msg.getTargetID() < 0) {
            Log.error(new Server(), "Unknown target type "+msg.getTargetID());
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
            Client c = (Client)ch;
            if (c.getChannel() == channel) {
                return c;
            }
        }
        return null;
    }
    
    public static Database getDatabase() {
        return DB;
    }
    
    public static Greeting userLogin(Channel ch, String username, String passwordHash) {
        int userID = DB.login(username, passwordHash);
        
        Log.write(new Server(), "USERLOGIN - ID: "+userID);
        
        if (userID != ERROR) {
            connections.remove(ch);
            clients.add(new Client(userID, ch));
            
            NewUser newUserMessage = MessageBuilder.generateNewUser(DB.getUserProfile(userID));
            for (Channel channel: clients) {
                Client cl = (Client)channel;
                System.err.println(cl.getUserID() + " --> " + userID);
                channel.writeAndFlush(newUserMessage);
            }
            return MessageBuilder.generateGreeting(DB.getUserProfile(userID), true);
        }
        
        return new Greeting(false);
    }
    
    public static void closeConnection(Channel ch) throws IOException {
        Client user = getClient(ch);
        
        if (user == null) {
            System.err.println("ehhhhhhhhhh");
            return;
        }
        
        if (user.isLoggedIn()) {
            clients.remove(user);
            for (Channel client: clients) {
                Client cl =(Client)client;
                cl.writeAndFlush(new Logout(user.getUserID(), Messages.Message.ALL));
            }

            DB.logoff(user.getUserID());
            user.setLoggedIn(false);

            Log.write(new Server(), "Clients: " + clients.size());
        }
    }
    
    public static void updateUserConnection(Client client) {
        for (Channel ch: clients) {
            Client cl =(Client)ch;
            if (!client.equals(cl)) {
                client.writeAndFlush(DB.getNewUser(cl.getUserID()));
            }
        }
    }

    
    public static void main(String[] args) {
        new ServerHandler(2014).run();
    }
}
