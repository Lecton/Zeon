/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.bootstrap;

import core.database.Database;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.NotSslRecordException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class EndOfPipe extends SimpleChannelInboundHandler<Object> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("End of Pipe");
        cause.printStackTrace();
        if (cause instanceof NotSslRecordException) {
        } else if (!ctx.channel().isOpen()) {
            System.out.println("Connection not open");
            return;
        } else {
            Logger.getLogger(EndOfPipe.class.getName()).log(Level.SEVERE, 
                    "Exception at end of pipe: ", cause);
        }
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    }
    
}
