package com.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import messages.Message;
import messages.StringMessage;

import com.gui.utils.ChatAdapter;
import com.gui.utils.ChatMessages;
import com.gui.utils.ClientAdapter;
import com.gui.utils.Contact;
import com.gui.utils.MessageFactory;
import com.mobile.Client;
import com.mobile.ClientHandler;
import com.mobile.R;
import com.mobile.R.id;
import com.mobile.R.layout;
import com.mobile.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.os.Build;

public class MessageWindow extends Activity {

	public static List<ChatMessages> chatHistory;
	public static ListView listChats = null;
	public static Handler UIHandler;
	public static Context baseContext;
	public static ChatAdapter chatAdapter;
	private static int clientID =-1;

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
		setContentView(R.layout.activity_message_window);

//		Serializable c = getIntent().getSerializableExtra("Client");
//		Serializable u = getIntent().getSerializableExtra("User");
		
		int uid = getIntent().getIntExtra("ClientID", -1);
		
//		if(c != null && u != null){
//			user = (Contact)u;
//			client = (Contact)c;
//			setTitle(client.getName());
//			BitmapDrawable icon = new BitmapDrawable(getResources(),client.getImage());
//			listChats = (ListView) findViewById(R.id.chatList);
//			getActionBar().setIcon(icon);
//		}
		if(uid != -1){
			clientID =uid;
			setTitle(ClientHandler.getFromUserID(clientID).getName());
			BitmapDrawable icon = new BitmapDrawable(getResources(),ClientHandler.getFromUserID(clientID).getImage());
			listChats = (ListView) findViewById(R.id.chatList);
			getActionBar().setIcon(icon);
		} else {
			//back or something
			Log.e("MessageWindow", "ClientID not received.");
		}

		baseContext = getBaseContext();
		updateChatList();
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
        case R.id.profile:
			getIntent().putExtra("UserProfile", true);
    		setResult(RESULT_OK, getIntent());		
    		finish();
            break;
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
		if (ClientHandler.getFromUserID(clientID) != null) {
			if (message.getTargetID() != Message.ALL) {
				if (ClientHandler.getFromUserID(clientID).getUserID() == message.getUserID()) {
					ClientHandler.getFromUserID(clientID).addMessage(message);
					updateChatList();
					return true;
				} else {
					return false;
				}
			} else {
				if (ClientHandler.getUser() != null 
						&& ClientHandler.getFromUserID(clientID).getUserID() == ClientHandler.getUser().getUserID()) {
					ClientHandler.getFromUserID(clientID).addMessage(message);
					updateChatList();
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
//		chatHistory.add(new ChatMessages(messages,owner));
	}
	
	@Override
	public void onBackPressed() {
//		getIntent().putExtra("User", user);
//		getIntent().putExtra("Client", client);
		setResult(RESULT_OK, getIntent());		
		finish();
	}	

	public static void updateChatList(){
		
		MainWindow.runOnUI(new Runnable() {
		     @Override
		     public void run() {
		    	
		    	if(listChats != null){
		    		List<ChatMessages> shortList = ClientHandler.getFromUserID(clientID).getMessageHistory();
		    		Log.v("Lengtht","" + shortList.size());
		    		
		    		
//		    		for(ChatMessages ch : chatHistory){
//		    			if((ch.getUserID() == user.getUserID() && ch.getTargetID() == client.getUserID()) ||
//		    			  (ch.getUserID() == client.getUserID() && ch.getTargetID() == user.getUserID())){
//		    				shortList.add(ch); 
//		    			}
//		    		}
		    		
			 		chatAdapter = new ChatAdapter(baseContext,shortList);
			 		listChats.setAdapter(chatAdapter);
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
		StringMessage sm = MessageFactory.generateStringMessage(
                ClientHandler.getUser().getUserID(), ClientHandler.getFromUserID(clientID).getUserID(), 
                ClientHandler.getUser().getEmail(), 
                message + "\n");
		
//		handleStringMessage(sm);
		ClientHandler.getFromUserID(clientID).addMessage(sm);
		updateChatList();
		Client.connection.writeSafe(sm);
	}
}
