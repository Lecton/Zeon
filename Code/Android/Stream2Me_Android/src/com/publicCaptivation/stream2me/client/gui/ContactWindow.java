package com.publicCaptivation.stream2me.client.gui;

import java.util.List;

import com.publicCaptivation.stream2me.client.Client;
import com.publicCaptivation.stream2me.client.ClientHandler;
import com.publicCaptivation.stream2me.client.R;
import com.publicCaptivation.stream2me.client.gui.utils.ChatAdapter;
import com.publicCaptivation.stream2me.client.gui.utils.ChatMessages;
import com.publicCaptivation.stream2me.client.gui.utils.MessageFactory;

import messages.Message;
import messages.StringMessage;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class ContactWindow extends Activity {
	private static String DEFAULTID ="-11";
	
	public static List<ChatMessages> chatHistory;
	public static ListView listChats = null;
	public static Handler UIHandler;
	public static Context baseContext;
	public static ChatAdapter chatAdapter;
	private static String clientID =DEFAULTID;

	static 
	{
	    UIHandler = new Handler(Looper.getMainLooper());
	}
	
	public static void runOnUI(Runnable runnable) {
	    UIHandler.post(runnable);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		setContentView(R.layout.activity_message_window);

//		Serializable c = getIntent().getSerializableExtra("Client");
//		Serializable u = getIntent().getSerializableExtra("User");
		
		String uid = getIntent().getStringExtra("ClientID");
		
//		if(c != null && u != null){
//			user = (Contact)u;
//			client = (Contact)c;
//			setTitle(client.getName());
//			BitmapDrawable icon = new BitmapDrawable(getResources(),client.getImage());
//			listChats = (ListView) findViewById(R.id.chatList);
//			getActionBar().setIcon(icon);
//		}
		if(uid != null){
			clientID = uid;
			
			BitmapDrawable icon =null;
			if (ClientHandler.getUser().getUserID().equals(clientID)) {
				setTitle("Group Messages");
				icon = new BitmapDrawable(getResources(),ClientHandler.getUser().getImage());
			} else {
				ClientHandler.getFromUserID(clientID).setStringNoticationOff();
				setTitle(ClientHandler.getFromUserID(clientID).getName());
				icon = new BitmapDrawable(getResources(),ClientHandler.getFromUserID(clientID).getImage());
			}
			listChats = (ListView) findViewById(R.id.chatList);
			getActionBar().setIcon(icon);
			getActionBar().setDisplayHomeAsUpEnabled(true);

			baseContext = getBaseContext();
			updateChatList();
		} else {
			//back or something
			Log.e("MessageWindow", "ClientID not received.");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		
        switch(item.getItemId()){
        case android.R.id.home:
			getIntent().putExtra("ClientID", clientID);
			getIntent().putExtra("UserProfile", 3);
    		setResult(RESULT_OK, getIntent());		
    		finish();
        }		
		return true;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_message_window,
					container, false);
			return rootView;
		}
	}


	public static boolean handleStringMessage(StringMessage message){
		if (clientID.equals(DEFAULTID)) {
			return false;
		}
		
		if (!(message.getTargetID().equals(Message.ALL))) {
			if (ClientHandler.getFromUserID(clientID) != null)  {
				if (ClientHandler.getFromUserID(clientID).getUserID().equals(message.getUserID())) {
					ClientHandler.getFromUserID(clientID).addMessage(message);
					updateChatList();
//					Log.v("ContactWindow.handleStringMessage ", "Name  "+ClientHandler.getFromUserID(clientID).getName() + "  Message=" + ClientHandler.getFromUserID(clientID).getStringNotification());
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			if (ClientHandler.getUser() != null 
					&& ClientHandler.getUser().getUserID().equals(clientID)) {
				ClientHandler.getUser().addMessage(message);
				updateChatList();
				return true;
			} else {
				return false;
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		clientID =DEFAULTID;
//		getIntent().putExtra("User", user);
//		getIntent().putExtra("Client", client);
		getIntent().putExtra("UserProfile", 2);
		setResult(RESULT_OK, getIntent());		
		finish();
	}

	public static void updateChatList(){
		
		MainWindow.runOnUI(new Runnable() {
		     @Override
		     public void run() {
		    	
		    	if(listChats != null){
		    		try {
			    		List<ChatMessages> shortList =null;
			    		if (ClientHandler.getUser().getUserID().equals(clientID)) {
			    			shortList = ClientHandler.getUser().getMessageHistory();
			    		} else {
			    			shortList = ClientHandler.getFromUserID(clientID).getMessageHistory();
			    		}
			    		
				 		chatAdapter = new ChatAdapter(baseContext,shortList);
				 		listChats.setAdapter(chatAdapter);
		    		} catch (Exception e) {
		    			
		    		}
		    	} else {
		    		Log.e("MessageWindow - updateChatList()", "listChat is null");
		    	}
			 }
		});
	}

	public void sendMessage(View view){
		EditText text = (EditText) findViewById(R.id.textMessage);
		String message = text.getText().toString();
		text.setText("");
		
		if (ClientHandler.getUser().getUserID().equals(clientID)) {
			StringMessage sm = MessageFactory.generateStringMessage(
					clientID, Message.ALL, 
					message + "\n");
		
			ClientHandler.getUser().addMessage(sm);
			Client.connection.writeMessage(sm);
		} else {
			StringMessage sm = MessageFactory.generateStringMessage(
					ClientHandler.getUser().getUserID(), clientID, 
					message + "\n");

//		Log.d("StringMessage userID", sm.getUserID());
//		Log.d("ClientHandler userID", ClientHandler.getUser().getUserID());
//		Log.d("StringMessage targetID", sm.getTargetID());
//		Log.d("ClientHandler targetID", clientID);
		
			ClientHandler.getFromUserID(clientID).addMessage(sm);
			Client.connection.writeMessage(sm);
		}
		updateChatList();
	}
}
