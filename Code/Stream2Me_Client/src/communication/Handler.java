/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import messages.Message;
import mvc.controller.Control;

/**
 *
 * @author Bernhard
 */
public class Handler extends SimpleChannelInboundHandler<Message> {
    private final static Logger LOGGER = Logger.getLogger(Handler.class.getName()); 

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Control.exceptionCaught(cause);
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        LOGGER.log(Level.INFO, "Writing message "+msg.handle()
                +" from "+msg.getUserID()+" to "+msg.getTargetID());
        
        boolean result =Control.handleMessage(msg);
        if (!result) {
            Pool.add(ctx, msg);
        }
    }
}
