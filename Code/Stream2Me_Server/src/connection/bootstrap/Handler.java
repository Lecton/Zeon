/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.bootstrap;

import channel.group.ClientChannelGroup;
import channel.group.ClientHandler;
import connection.messageChannel.MessageHandler;
import core.database.Database;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.Message;

/**
 *
 * @author Bernhard
 */
public class Handler extends SimpleChannelInboundHandler<Message> {
    public static final ChannelGroup connections =new ClientChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incomming =ctx.channel();
        final SslHandler sslHandler =ctx.pipeline().get(SslHandler.class);
        System.out.println("Attempting handshake");
        sslHandler.handshakeFuture().addListener(new Authentication(sslHandler));
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incomming =ctx.channel();
        connections.remove(incomming);
        incomming.close();
        
        ClientHandler.handlerRemoved(incomming);
        
        System.out.println("Disconnected");
//        if (Server.clientContains(incomming)) {
//            Server.closeConnection(incomming);
//        } else {
//            System.out.println("wasnt logged in");
//        }
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Logger.getLogger(Handler.class.getName()).log(Level.INFO, 
                "Received message: "+msg.handle());
        Channel incomming =ctx.channel();
        
        try {
            MessageHandler.handle(ctx, msg);
        } catch (UnsupportedOperationException e) {
//            Logger.getLogger(Handler.class.getName()).log(Level.WARNING, "Operation not yet implemented", e);
        }
    }
    
}
