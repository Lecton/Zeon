package com.stream2me.connection;
import com.gui.GUIActivity;
import com.gui.LoginActivity;

import android.util.Log;
import Messages.Message;
import Messages.UserConnection.Greeting;
import Messages.UserConnection.NewUser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ConnectionHandler extends SimpleChannelInboundHandler<Message> {
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Log.v(this.getClass().getSimpleName(), "Connection exception");
		cause.printStackTrace();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg)
			throws Exception {
		switch (msg.handle()) {
			case greeting:
				LoginActivity.setGreetingMessage((Greeting)msg);
				break;
			case newUser:
				GUIActivity.handleNewUser((NewUser)msg);
				break;
			case string:
				GUIActivity.handleStringMessage((Messages.StringMessage)msg);
				break;
			case streamNotify:
				GUIActivity.handleStreamNotification((Messages.Media.StreamNotify)msg);
				break;
			default:
					Log.e("Message Read", "Unhandled message "+msg.handle());
					Log.v("Message Read",msg.getMessage());
		}
	}	
}