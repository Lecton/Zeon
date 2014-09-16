/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.bootstrap;

import core.database.Database;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bernhard
 */
public class EndOfPipe extends SimpleChannelInboundHandler<Object> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Logger.getLogger(EndOfPipe.class.getName()).log(Level.SEVERE, 
                "Exception at end of pipe: ", cause);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    }
    
}
