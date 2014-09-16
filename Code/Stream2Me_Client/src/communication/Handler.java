/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package communication;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import messages.Message;
import mvc.controller.Control;

/**
 *
 * @author Bernhard
 */
public class Handler extends SimpleChannelInboundHandler<Message> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        boolean result =Control.handleMessage(msg);
        if (!result) {
            Pool.add(ctx, msg);
        }
    }
}
