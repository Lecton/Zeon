package com.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class MainWindow extends Activity {


	public static ListView listClients = null;
	public static List<Contact> contacts;
	public static Contact user = null;
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

		if(user == null){
			Serializable userd = getIntent().getSerializableExtra("UserDetails");
			Log.v("user","Main user");
			if(userd != null){
				user = (Contact)userd;
				MessageWindow.chatHistory = new ArrayList<>();
			}else{
				//Logout / Kick user
				Log.v("On GUIActivity create","User got here without valid login path");
			} 
		}
		
		baseContext = getBaseContext();
		contacts = new ArrayList<>();
		listClients = (ListView) findViewById(R.id.contactList);
		Client.connection.writeSafe(new UpdateListMessage(user.getUserID()));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_window, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main_window,
					container, false);
			return rootView;
		}
	}

	public static void handleNewUser(NewUserMessage message){
		
		if(message != null && user != null){
			Contact c = new Contact(message);
			
			if(!(contacts.contains(c))){
				contacts.add(c);
				updateClientList();
			}
		}
	}
	

	public static void handleLogoutUser(LogoutMessage message){
		
		for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
			Contact c = it.next();
		    if (message.getUserID() == c.getUserID()) {
		        it.remove();
		        updateClientList();
		    }
		}
	}
	
	public static void handleStringMessage(StringMessage messages){
		MessageWindow.handleStringMessage(messages,false);
	}
	
	public static void updateClientList(){
		
		MainWindow.runOnUI(new Runnable() {
		     @Override
		     public void run() {		    		
		 		clientAdapter = new ClientAdapter(baseContext,contacts);
		 		listClients.setAdapter(clientAdapter);
		 		listClients.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		 		listClients.setOnItemClickListener(new OnItemClickListener(){

		 			@Override
		 			public void onItemClick(AdapterView<?> parent, View view,
		 					int position, long id) {
		 				
		 				Log.v(contacts.get(position).getName(), "ID: " + contacts.get(position).getUserID());
		 				Contact c = contacts.get(position);	
		 				activity.getIntent().putExtra("Client", c);
		 				activity.getIntent().putExtra("User", user);
		 				activity.setResult(RESULT_OK, activity.getIntent());		
		 				activity.finish();
		 				
		 			}
		 		});
			 }
		});
	}
}
