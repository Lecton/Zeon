/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package channel.group;

import channel.ClientChannel;
import channel.group.ClientChannelGroup;
import channel.group.matcher.ClientGroup;
import channel.group.matcher.ClientMatcher;
import channel.group.matcher.RemoveMatcher;
import io.netty.channel.Channel;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;

/**
 *
 * @author Bernhard
 */
public class ClientHandler {
    static final Map<String, ClientChannelGroup> serverGroup = new HashMap<>();
    
    static {
        serverGroup.put("default", new ClientChannelGroup(GlobalEventExecutor.INSTANCE));
    }
    
    public static boolean add(ClientChannel channel) {
//        Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, 
//                "Clients: " + serverGroup.size());
        ClientChannelGroup ccg =serverGroup.get(channel.getGroupID());
        if (ccg != null) {
            return ccg.add(channel);
        } else {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, 
                    "Group not found.");
            return false;
        }
    }
    
    public static boolean remove(Channel channel, String userID, String groupID) {
        Logger.getLogger(ClientHandler.class.getName()).log(Level.INFO, 
                "Groups: " + serverGroup.size());
        ClientChannelGroup ccg =serverGroup.get(groupID);
        if (ccg != null) {
            return ccg.remove(new RemoveMatcher(userID));
        } else {
            return serverGroup.get("default").remove(channel);
        }
    }
    
    public static void removeGroup(String groupID) {
        ClientChannelGroup ccg =serverGroup.get(groupID);
        if (ccg != null) {
            ccg.deregister(new ClientGroup(groupID));
            serverGroup.remove(groupID);
        }
    }
    
    public static void writeAndFlush(String groupID, Message message) {
        ClientMatcher matcher =ClientMatcher.generateMatcher(message);
        ClientChannelGroup ccg =serverGroup.get(groupID);
        
        try {
            if (ccg != null) {
                ccg.writeAndFlush(message, matcher);
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
}