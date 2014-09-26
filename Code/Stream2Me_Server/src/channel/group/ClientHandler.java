/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package channel.group;

import channel.ClientChannel;
import channel.group.matcher.ClientGroup;
import channel.group.matcher.ClientMatcher;
import channel.group.matcher.RemoveMatcher;
import core.database.UserHandler;
import io.netty.channel.Channel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;
import messages.userConnection.LogoutMessage;

/**
 *
 * @author Bernhard
 */
public class ClientHandler {
    static final Map<String, ClientChannelGroup> serverGroup = new HashMap<>();
    
    static {
        serverGroup.put("default", new ClientChannelGroup(GlobalEventExecutor.INSTANCE));
    }
    
    public static int add(ClientChannel channel) {
//        Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, 
//                "Clients: " + serverGroup.size());
        ClientChannelGroup ccg =serverGroup.get(channel.getGroupID());
        if (ccg != null) {
            return ccg.add(channel);
        } else {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, 
                    "Group not found.");
            return -1;
        }
    }
    
    public static ClientChannel remove(Channel channel, String groupID) {
        Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, 
                "Groups: " + serverGroup.size());
        ClientChannelGroup ccg =serverGroup.get(groupID);
            ClientChannel cc =(ClientChannel) channel;
        if (ccg != null) {
            ccg.remove(new RemoveMatcher(cc.getUserID()));
            return cc;
        } else {
            serverGroup.get("default").remove(cc);
            return cc;
        }
    }
    
    public static void removeGroup(String groupID) {
        ClientChannelGroup ccg =serverGroup.get(groupID);
        if (ccg != null) {
            ccg.deregister(new ClientGroup("", groupID));
            serverGroup.remove(groupID);
        }
    }
    
    public static void writeAndFlush(String groupID, Message message) {
        ClientMatcher matcher =ClientMatcher.generateMatcher(message);
        ClientChannelGroup ccg =serverGroup.get(groupID);
        
        try {
            if (ccg != null) {
                int count =ccg.writeAndFlush(message, matcher);
            } else {
                 Logger.getLogger(ClientHandler.class.getName()).log(Level.WARNING, 
                         "Group not found");
            }
        } catch (Exception e) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, 
                    "Exception during write and flush of message. "
                            +message.handle(), e);
        }
    }
    
    public static void writeAndFlush(String groupID, Message message, ClientMatcher matcher) {
        ClientChannelGroup ccg =serverGroup.get(groupID);
        if (ccg != null) {
            ccg.writeAndFlush(message, matcher);
        }
    }

    public static void createGroup(String groupID) {
        ClientChannelGroup ccg =new ClientChannelGroup(groupID, GlobalEventExecutor.INSTANCE);
        serverGroup.put(groupID, ccg);
    }

    public static void handlerRemoved(Channel channel) {
        for (ClientChannelGroup ccg: serverGroup.values()) {
            ClientChannel c =ccg.containsAndRemove(channel);
            if (c != null) {
                System.out.println(ccg.name()+" contained the user.");
                UserHandler.logoff(c.getUserID(), c.getConnectionID());
                
                boolean contained =ClientHandler.contains(c, c.getUserID());
                if (!contained) {
                    Message m =new LogoutMessage(c.getUserID());
                    ccg.writeAndFlush(m, ClientMatcher.all());
                }
                return;
            }
        }
        System.out.println("Not a logged in user");
    }

    public static boolean contains(Channel channel, String userID) {
        for (ClientChannelGroup ccg: serverGroup.values()) {
            boolean contained =ccg.containsByID(channel, userID);
            if (contained) {
                return true;
            }
        }
        return false;
    }

    public static String getConnectionID(Channel channel, String groupID) {
        for (ClientChannelGroup ccg: serverGroup.values()) {
            String connectionID =ccg.getConnectionID(channel);
            if (connectionID != null) {
                return connectionID;
            }
        }
        return null;
    }
}