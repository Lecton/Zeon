package com.mobile;

import messages.Message;
import messages.StringMessage;
import messages.media.StreamNotifyMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import android.util.Log;

import com.gui.LoginWindow;
import com.gui.MainWindow;

public class MessageHandler {
	public static boolean handle(Message msg) {
		switch (msg.handle()) {
			case greeting:
				LoginWindow.setGreetingMessage((GreetingMessage)msg);
				return true;
			case newUser:
				MainWindow.handleNewUser((NewUserMessage)msg);
				return true;
			case string:
				MainWindow.handleStringMessage((StringMessage)msg);
				return true;
			case streamNotify:
				ClientHandler.setClientNotifocation((StreamNotifyMessage)msg);
				return true;
			case logout:
				MainWindow.handleLogoutUser((LogoutMessage)msg);
				return true;
			default:
				Log.e("Message Read", "Unhandled message "+msg.handle());
				Log.v("Message Read",msg.getMessage());
				return false;
		}
	}
}
