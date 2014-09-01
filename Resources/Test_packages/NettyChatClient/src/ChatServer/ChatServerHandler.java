/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ChatServer;

import Messages.UserConnection.Login;
import Messages.UserConnection.Logout;
import Messages.UserConnection.NewUser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 *
 * @author Bernhard
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<Messages.Message> {

    private static final ChannelGroup channels =new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incomming =ctx.channel();
        for (Channel channel: channels) {
            channel.writeAndFlush(new NewUser(1, incomming.remoteAddress().toString(), null, null));
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incomming =ctx.channel();
        for (Channel channel: channels) {
            channel.writeAndFlush(new Logout(1));
        }
        channels.remove(ctx.channel());
    }
    
    
    @Override
    protected void channelRead0(ChannelHandlerContext chc, Messages.Message i) throws Exception {
        Channel incomming =chc.channel();
        
        for (Channel channel: channels) {
            if (channel != incomming) {
                channel.writeAndFlush(i);
            }
        }
    }
}
