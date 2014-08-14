package com.stream2me.connection;

//import com.stream2me.gui.LoginActivity;

import com.gui.LoginActivity;

import android.util.Log;
import Messages.Message;
import Messages.UserConnection.Greeting;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ConnectionHandler extends SimpleChannelInboundHandler<Message> {
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Log.v(this.getClass().getSimpleName(), "Connection exception");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		switch (msg.handle()) {
			case greeting:
				LoginActivity.setGreetingMessage((Greeting)msg);
				break;
			default:
					Log.e("Message Read", "Unhandled message "+msg.handle());
					Log.v("Message Read",msg.getMessage());
		}
	}
	
}