package com.connection;
import messages.Message;
import messages.StringMessage;
import messages.media.StreamNotifyMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;

import com.gui.LoginWindow;
import com.gui.MainWindow;
import com.gui.ContactWindow;
import com.mobile.ClientHandler;

import android.util.Log;
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
				LoginWindow.setGreetingMessage((GreetingMessage)msg);
				break;
			case newUser:
				MainWindow.handleNewUser((NewUserMessage)msg);
				break;
			case string:
				MainWindow.handleStringMessage((StringMessage)msg);
				break;
			case streamNotify:
				ClientHandler.setClientNotifocation((StreamNotifyMessage)msg);
				break;
			case logout:
				MainWindow.handleLogoutUser((LogoutMessage)msg);
				break;
			default:
					Log.e("Message Read", "Unhandled message "+msg.handle());
					Log.v("Message Read",msg.getMessage());
		}
	}	
}