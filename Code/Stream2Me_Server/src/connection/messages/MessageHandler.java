/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection.messages;

import Messages.Message;
import Messages.StringMessage;
import Messages.UserConnection.Greeting;
import Messages.UserConnection.Login;
import Utils.Log;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * @author Bernhard
 */
public class MessageHandler {
    public static void handle(ChannelHandlerContext ctx, Message msg) {
        switch (msg.handle()) {
            case greeting:
            case login:
                handleLogin(ctx, (Login)msg);
            case logout:
            case newUser:
            case notify:
            case updateAvatar:
            case updateName:
            case updateList:
                break;
            case string:
                handleStringMessage(ctx, (StringMessage)msg);
                
        }
    }
    
    private static void handleLogin(ChannelHandlerContext ctx, Login msg) {
        Greeting g =Server.Server.userLogin(ctx.channel(), msg.getSender(), msg.getPasswordHash());
        ctx.channel().writeAndFlush(g);
    }
    
    private static void handleStringMessage(ChannelHandlerContext ctx, StringMessage msg) {
        Server.Server.relay(msg, ctx.channel());
    }
}
