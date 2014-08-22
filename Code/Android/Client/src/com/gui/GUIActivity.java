package com.gui;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.activitytut.R;
import com.activitytut.R.id;
import com.activitytut.R.layout;
import com.activitytut.R.menu;
import com.gui.utils.ChatAdapter;
import com.gui.utils.ChatMessages;
import com.gui.utils.ClientAdapter;
import com.gui.utils.Contact;
import com.gui.utils.MessageFactory;
import com.stream2me.Client;

import Messages.Message;
import Messages.Message.MessageType;
import Messages.UserConnection.Greeting;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.transition.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public final class GUIActivity extends ActionBarActivity{
	public static ListView LVContacts;	
	public static TextView streamNotification;
	public static TextView audioNotification;
	public static ClientAdapter clientAdapter;
	public static ChatAdapter chatAdapter;
	public static List<Contact> contacts;
	public static List<Contact> selected;
	private static List<ChatMessages> chatHistory;
	public static ListView listClients = null;
	public static ListView listChats = null;
	public static Context baseContext;
	public static Messages.UserConnection.NewUser message = null;
	public static Handler UIHandler;
	public ImageButton DP;
	public boolean microphoneClicked = true;
	public boolean videoClicked = true;
	public boolean streamClicked = true;
	final int SELECT_PHOTO = 1;
	
	private Contact user;

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
		setContentView(R.layout.activity_gui);
		
		Serializable a = getIntent().getSerializableExtra("LoggedInUser");
		if (a != null) {
			user =(Contact)a;			
			contacts = new ArrayList<>();
			baseContext = getBaseContext();
			listClients = (ListView) findViewById(R.id.contactList);
			listChats =  (ListView) findViewById(R.id.chatList);
			ImageButton im = (ImageButton) findViewById(R.id.profilePicture);
			im.setImageBitmap(Contact.getResizedBitmap(user.getImage(), 250, 250));
			selected = new ArrayList<>();
			chatHistory = new ArrayList<>();
			

			audioNotification =  (TextView) findViewById(R.id.leftBtn);
			streamNotification =  (TextView) findViewById(R.id.rightBtn);
		} else {
			//Logout / Kick user
			Log.e("On GUIActivity create","User got her without valid login path");
		}
		
	}

	public static void addContact(Messages.UserConnection.NewUser message){
		if(message != null && contacts != null){
			contacts.add(new Contact(message));
			clientAdapter = new ClientAdapter(baseContext,contacts);
			listClients.setAdapter(clientAdapter);
			
			listClients.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			listClients.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
														int position, long id){
					

					Log.v(contacts.get(position).getName(), "position: " + position );
					if(!(selected.contains(contacts.get(position)))){
						selected.add(contacts.get(position));
						List<ChatMessages> chat_m = new ArrayList<>();
						
						for(ChatMessages cm:chatHistory){
//							
							if(cm.getUserID() == contacts.get(position).getUserID()){

								Log.v(contacts.get(position).getName() + " " + contacts.get(position).getUserID(), "position: " + position + "  ");
								Log.v(cm.getName(),cm.getMessage() + " " + cm.getTimestamp());
								chat_m.add(cm);
							}							
						}
						
						chatAdapter = new ChatAdapter(baseContext, chat_m);
						listChats.setAdapter(chatAdapter);
						
						
					}else{
						if(!selected.remove(contacts.get(position))){
							Log.e("GUIActivity","Contact could not be diselected");
						}
					}
					
				}
			});

		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gui, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_gui, container,false);
			return rootView;
		}
	}


	public void ChangeDP(View view){
		
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);		
	}
	
	public void sendMessage(View view){
		EditText edt = (EditText) findViewById(R.id.createArea);
		Log.v("Text message",edt.getText().toString());
		String text = edt.getText().toString();
		edt.setText("");
		for(Contact c:selected){
			if(text.length() > 0){
				Messages.StringMessage sm = MessageFactory.generateStringMessage
													(this.user.getUserID(), c.getUserID(), 
													this.user.getName(), text);
				chatHistory.add(new ChatMessages(this.user.getUserID(), this.user.getName(),text,(System.currentTimeMillis())));
				Client.connection.writeSafe(sm);
			}
		}
	}
	
	public static void handleNewUser(Messages.UserConnection.NewUser msg) {
		message = msg;
				
				GUIActivity.runOnUI(new Runnable() {
				     @Override
				     public void run() {
						addContact(message);
				    }
				});				
	}
	
	public static void handleStringMessage(Messages.StringMessage m){
		chatHistory.add(new ChatMessages(m.getUserID(),m.getSender(),m.getMessage(),(System.currentTimeMillis())));		
	}	
	
	public static void handleStreamNotification(Messages.Media.StreamNotify msg){
		
		if(msg != null && streamNotification != null){
			streamNotification.setVisibility(View.VISIBLE);
		}else if(streamNotification == null){
			Log.v("GUIActivity","streamNotification is null");
		}else if(msg == null){
			Log.v("GUIActivity","message is null");
			
		}
	}
	
	public void microBtn(View view){
		ImageButton button = (ImageButton) findViewById(R.id.microBtn);
		if(microphoneClicked){
			button.setImageResource(R.drawable.clicked_microphone);
			
		}else{
			Log.v("tag","Unclicked");
			button.setImageResource(R.drawable.unclicked_microphone);
		}
		microphoneClicked = !microphoneClicked;
	}
	
	public void videoBtn(View view){
		ImageButton button = (ImageButton) findViewById(R.id.videoBtn);
		if(videoClicked){
			button.setImageResource(R.drawable.clicked_camera);
			
		}else{
			button.setImageResource(R.drawable.unclicked_camera);
		}
		videoClicked = !videoClicked;
	}
	
	public void streamBtn(View view){
		ImageButton button = (ImageButton) findViewById(R.id.streamBtn);
		if(streamClicked){
			button.setImageResource(R.drawable.clicked_stream);
			
		}else{
			button.setImageResource(R.drawable.unclicked_stream);
		}
		streamClicked = !streamClicked;
	}	
	
	public void SendStream(View view){
		  Toast.makeText(getApplicationContext(), "Green",Toast.LENGTH_SHORT).show();
	}
	
	public void RecieveStream(View view){
		  Toast.makeText(getApplicationContext(), "Red",Toast.LENGTH_SHORT).show();
	}

}
