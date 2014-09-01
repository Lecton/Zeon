package ChatClient;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernhard
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<Messages.Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext chc, Messages.Message i) throws Exception {
        System.out.println(i.getMessage());
    }
    
}
