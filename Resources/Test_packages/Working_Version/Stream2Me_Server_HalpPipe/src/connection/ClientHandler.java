package connection;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messages.Message;
import core.Server;
import static core.Server.connections;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernhard
 */
class ClientHandler extends SimpleChannelInboundHandler<Message> {
    
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incomming =ctx.channel();
        Server.connections.add(incomming);
    }
    
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incomming =ctx.channel();
        Server.connections.remove(ctx.channel());
        
        
        if (Server.clientContains(incomming)) {
            Server.closeConnection(incomming);
        } else {
//            System.out.println("wasnt logged in");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Channel incomming =ctx.channel();
        MessageHandler.handle(ctx, msg);
    }
}
