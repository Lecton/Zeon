package com.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import messages.StringMessage;

import com.gui.utils.ChatAdapter;
import com.gui.utils.ChatMessages;
import com.gui.utils.ClientAdapter;
import com.gui.utils.Contact;
import com.mobile.Client;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MessageWindow extends Activity {

	public static List<ChatMessages> chatHistory;
	public static ListView listChats = null;
	public static Handler UIHandler;
	public static Context baseContext;
	public static ChatAdapter chatAdapter;
	private static Contact user;
	private static Contact client;

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

		Serializable c = getIntent().getSerializableExtra("Client");
		Serializable u = getIntent().getSerializableExtra("User");
		
		if(c != null && u != null){
			user = (Contact)u;
			client = (Contact)c;
			setTitle(client.getName());
			BitmapDrawable icon = new BitmapDrawable(getResources(),client.getImage());
			listChats = (ListView) findViewById(R.id.chatList);
			getActionBar().setIcon(icon);
		}

		baseContext = getBaseContext();
		updateChatList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message_window, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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


	public static void handleStringMessage(StringMessage messages,boolean owner){
		chatHistory.add(new ChatMessages(messages,owner));
		updateChatList();
	}
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_OK, getIntent());		
		finish();
	}	

	public static void updateChatList(){
		
		MainWindow.runOnUI(new Runnable() {
		     @Override
		     public void run() {
		    	
		    	if(listChats != null){
		    		Log.v("Lengtht","" + chatHistory.size());
		    		List<ChatMessages> shortList = new ArrayList<>();
		    		
		    		for(ChatMessages ch : chatHistory){
		    			if((ch.getUserID() == user.getUserID() && ch.getTargetID() == client.getUserID()) ||
		    			  (ch.getUserID() == client.getUserID() && ch.getTargetID() == user.getUserID())){
		    				shortList.add(ch); 
		    			}
		    		}
		    		
			 		chatAdapter = new ChatAdapter(baseContext,shortList);
			 		listChats.setAdapter(chatAdapter);
		    	}
			 }
		});
	}

	public void sendMessage(View view){
		EditText text = (EditText) findViewById(R.id.textMessage);
		String message = text.getText().toString();
		text.setText("");
		StringMessage stringMessage = new StringMessage(user.getUserID(),client.getUserID(),user.getEmail(),message);
		handleStringMessage(stringMessage,true);
		Client.connection.writeSafe(stringMessage);
	}
}
