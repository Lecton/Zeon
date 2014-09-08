/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package channel;

import channel.group.ClientChannelGroup;
import channel.group.matcher.ClientGroup;
import channel.group.matcher.ClientMatcher;
import io.netty.channel.Channel;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.ConcurrentSet;
import java.util.ArrayList;
import messages.Message;
import messages.update.UpdateAvatarMessage;
import utils.Log;

/**
 *
 * @author Bernhard
 */
public class ClientHandler {
    static ClientChannelGroup serverGroup = new ClientChannelGroup(GlobalEventExecutor.INSTANCE);

    public static boolean add(ClientChannel channel) {
        Log.write(ClientHandler.class, "Clients: " + serverGroup.size());
        return serverGroup.add(channel);
    }
    
    public static boolean remove(Channel channel) {
        Log.write(ClientHandler.class, "Clients: " + serverGroup.size());
        return serverGroup.remove(channel);
    }
    
    public static void removeGroup(int groupID) {
        serverGroup.deregister(new ClientGroup(groupID));
    }
    
    public static void writeAndFlush(Message message) {
        ClientMatcher matcher =ClientMatcher.generateMatcher(message);
        serverGroup.writeAndFlush(message, matcher);
    }
    
    public static void writeAndFlush(Message message, ClientMatcher matcher) {
        serverGroup.writeAndFlush(message, matcher);
    }
}