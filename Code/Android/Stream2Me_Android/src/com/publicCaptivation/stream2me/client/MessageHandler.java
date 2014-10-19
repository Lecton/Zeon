package com.publicCaptivation.stream2me.client;

import com.publicCaptivation.stream2me.client.gui.ClientProfileWindow;
import com.publicCaptivation.stream2me.client.gui.LoginWindow;
import com.publicCaptivation.stream2me.client.gui.MainWindow;
import com.publicCaptivation.stream2me.client.gui.VideoStreamWindow;

import messages.Message;
import messages.StringMessage;
import messages.media.AudioStreamMessage;
import messages.media.VideoStreamMessage;
import messages.media.communication.StreamNotifyMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateProfileMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import android.util.Log;

public class MessageHandler {
	public static boolean handle(Message msg) {
		Log.v("MessageHandler", "Got: "+msg.handle());
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
			case console:
				return true;
			case video:
				VideoStreamWindow.handleVideo((VideoStreamMessage)msg);
				return true;
			case auido:
				ClientProfileWindow.handleAudio((AudioStreamMessage)msg);
				return true;
			case updateAvatar:
				MainWindow.updateUserAvatar((UpdateAvatarMessage)msg);
				return true;
			case updateProfile:
				MainWindow.updateUserProfile((UpdateProfileMessage)msg);
				return true;
			case streamProperty:
				Log.v("MessageHandler","streamProperty");
				return true;
			case streamPropertyRequest:
				Log.v("MessageHandler","streamPropertyRequest");
				return true;
			case streamReply:
				Log.v("MessageHandler","streamReply");
				return true;
			default:
				Log.e("Message Read", "Unhandled message "+msg.handle());
				Log.v("Message Read",msg.getMessage());
				return false;
		}
	}
}
