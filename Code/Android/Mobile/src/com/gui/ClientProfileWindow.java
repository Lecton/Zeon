package com.gui;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Build;

public class ClientProfileWindow extends Activity {

	ImageView image;
	TextView name;
	TextView surname;
	TextView email;
	ImageButton audioAccept;
	ImageButton videoAccept;
	private static Contact contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_profile_window);

		final String uid = getIntent().getStringExtra("ClientID");
		
		if(uid != null){
			contact = ClientHandler.getFromUserID(uid);
			image = (ImageView) findViewById(R.id.clientPicture);
			name = (TextView) findViewById(R.id.name);
			surname = (TextView) findViewById(R.id.surname);
			email = (TextView) findViewById(R.id.email);
			videoAccept = (ImageButton) findViewById(R.id.videoInvite);
			videoAccept.setVisibility(View.GONE);
			

//			if(contact.getAudioNotification()){
//				audioAccept.setVisibility(View.VISIBLE);
//			}
			
			if(contact.getVideoNotification()){
				videoAccept.setVisibility(View.VISIBLE);
				videoAccept.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						contact.setVideoNoticationOff();
						Client.connection.writeMessage(
								MessageFactory.generateStreamResponse(ClientHandler.getUser().getUserID(), 
										ClientHandler.getFromUserID(uid).getVideoStreamID(), true));
						getIntent().putExtra("UserProfile", 5);
						getIntent().putExtra("ClientID", contact.getUserID());
						setResult(RESULT_OK, getIntent());		
						finish();
					}
				});
			}
			
			if(!init()){
				Log.v("ClientProfileWindow","init error");				
			}
		}else{
			Log.v("ClientProfileWindow","onCreate");
		}
	}

	public boolean init(){
		if(image != null && name != null && surname != null && email != null){
			image.setImageBitmap(ClientHandler.getResizedBitmap(contact.getImage(),300,300));
			name.setText(contact.getName());
			surname.setText(contact.getSurname());
			email.setText(contact.getEmail());
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client_profile_window, menu);
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

	@Override
	public void onBackPressed() {
		getIntent().putExtra("UserProfile", 4);
		getIntent().putExtra("ClientID", contact.getUserID());
		setResult(RESULT_OK, getIntent());		
		finish();
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
			View rootView = inflater.inflate(
					R.layout.fragment_client_profile_window, container, false);
			return rootView;
		}
	}

}
