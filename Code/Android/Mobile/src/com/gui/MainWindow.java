package com.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import messages.Message;
import messages.StringMessage;
import messages.update.UpdateListMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;

import com.connection.Connection;
import com.gui.utils.ChatMessages;
import com.gui.utils.ClientAdapter;
import com.gui.utils.Contact;
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
import android.content.Intent;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class MainWindow extends Activity {

	public static ListView listClients = null;
	public static Context baseContext;
	public static ClientAdapter clientAdapter;
	public static Handler UIHandler;
	public static Activity activity;
	
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
		setContentView(R.layout.activity_main_window);
		activity = this;

		if(ClientHandler.getUser() != null){
//			Serializable userd = getIntent().getSerializableExtra("UserDetails");
			Log.v("user","Main user");
		} else {
			//Logout / Kick user
			Log.v("On GUIActivity create","User got here without valid login path");
		}
		
//		Serializable clientS = getIntent().getSerializableExtra("Client");
//		if (clientS != null) {
//			for (Contact c: contacts) {
//				if (c.getUserID() == ((Contact)clientS).getUserID()) {
//					c =(Contact)clientS;
//					break;
//				}
//			}
//		}
		
		baseContext = getBaseContext();
		listClients = (ListView) findViewById(R.id.contactList);
		updateClientList();
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
			View rootView = inflater.inflate(R.layout.fragment_main_window,
					container, false);
			return rootView;
		}
	}

	public static void handleNewUser(NewUserMessage message){
		ClientHandler.handleNewUserMessage(message);
		updateClientList();
//		if(message != null && user != null){
//			Contact c = new Contact(message);
//			
//			if(!(contacts.contains(c))){
//				contacts.add(c);
//				updateClientList();
//			}
//		}
	}
	

	public static void handleLogoutUser(LogoutMessage message){
		ClientHandler.handleLogoutMessage(message);
		updateClientList();
//		for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
//			Contact c = it.next();
//		    if (message.getUserID() == c.getUserID()) {
//		        it.remove();
//		        updateClientList();
//		    }
//		}
	}
	
	public static void handleStringMessage(StringMessage message){
		boolean result =MessageWindow.handleStringMessage(message);
		if (!result) {
			ClientHandler.handleStringMessage(message);
		}
	}
	
	public static void updateClientList(){
		
		MainWindow.runOnUI(new Runnable() {
		     @Override
		     public void run() {	
		    	if (listClients == null) {
		    		return;
		    	}
		    	 
		 		clientAdapter = new ClientAdapter(baseContext,ClientHandler.getContacts());
		 		listClients.setAdapter(clientAdapter);
		 		listClients.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		 		listClients.setOnItemClickListener(new OnItemClickListener(){

		 			@Override
		 			public void onItemClick(AdapterView<?> parent, View view,
		 					int position, long id) {
		 				
		 				Log.v(ClientHandler.get(position).getName(), "ID: " + ClientHandler.get(position).getUserID());
		 				Contact c = ClientHandler.get(position);	
		 				activity.getIntent().putExtra("ClientID", c.getUserID());
		 				activity.setResult(RESULT_OK, activity.getIntent());		
		 				activity.finish();
		 				
		 			}
		 		});
			 }
		});
	}
}
