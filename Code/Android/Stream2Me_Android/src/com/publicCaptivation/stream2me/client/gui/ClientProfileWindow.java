package com.publicCaptivation.stream2me.client.gui;

import com.publicCaptivation.stream2me.client.Client;
import com.publicCaptivation.stream2me.client.ClientHandler;
import com.publicCaptivation.stream2me.client.R;
import com.publicCaptivation.stream2me.client.audioPlayer.AudioPlayer;
import com.publicCaptivation.stream2me.client.gui.utils.Contact;
import com.publicCaptivation.stream2me.client.gui.utils.MessageFactory;

import messages.media.AudioStreamMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateProfileMessage;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class ClientProfileWindow extends Activity {
	private static AudioPlayer player;
	private static Contact playerContact;

	private static ImageView image;
	private static TextView name;
	private static TextView surname;
	private static TextView email;
	private static TextView title;
	private static TextView aboutme;
	ImageButton audioAccept;
	ImageButton videoAccept;
	private static Contact contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_profile_window);

		final String uid = getIntent().getStringExtra("ClientID");
		
		if(uid != null){
			if (player != null) {
				playerContact =contact;
			}
			
			contact = ClientHandler.getFromUserID(uid);
			image = (ImageView) findViewById(R.id.clientPicture);
			name = (TextView)findViewById(R.id.name);
			surname = (TextView) findViewById(R.id.surname);
			email = (TextView) findViewById(R.id.email);
			title = (TextView) findViewById(R.id.title);
			aboutme = (TextView) findViewById(R.id.aboutme);
			videoAccept = (ImageButton) findViewById(R.id.videoAccept);
			audioAccept = (ImageButton) findViewById(R.id.audioAccept);


		
			

//			if(contact.getAudioNotification()){
//				audioAccept.setVisibility(View.VISIBLE);
//			}
			
			if(contact.getVideoNotification()){
				videoAccept.setImageResource(R.drawable.unclicked_camera);
				videoAccept.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Client.connection.writeMessage(
										MessageFactory.generateStreamResponse(ClientHandler.getUser().getUserID(), 
												contact.getVideoStreamID(), true));
						getIntent().putExtra("UserProfile", 5);
						getIntent().putExtra("ClientID", contact.getUserID());
						setResult(RESULT_OK, getIntent());		
						finish();
					}
				});
			}
			
			if(contact.getAudioNotification()){
				if (player != null) {
					if (player.getStreamID().equals(contact.getAudioStreamID())) {
						audioAccept.setImageResource(R.drawable.clicked_microphone);
					} else {
						audioAccept.setImageResource(R.drawable.unclicked_microphone);
					}
				} else {
					audioAccept.setImageResource(R.drawable.unclicked_microphone);
				}
				
				audioAccept.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (player != null) {
							playerContact.setAudioNoticationOn();
							Client.connection.writeMessage(
									MessageFactory.generateStreamResponse(ClientHandler.getUser().getUserID(), 
									playerContact.getAudioStreamID(), false));
							stopAudioPlayer();
						}
						
						if (playerContact != null && playerContact.getUserID().equals(uid)) {
							contact.setAudioNoticationOn();
							audioAccept.setImageResource(R.drawable.clicked_microphone);
							Client.connection.writeMessage(
											MessageFactory.generateStreamResponse(ClientHandler.getUser().getUserID(), 
											contact.getAudioStreamID(),false));
							stopAudioPlayer();
						} else {
							// TODO Auto-generated method stub
							contact.setAudioNoticationOff();
							audioAccept.setImageResource(R.drawable.clicked_microphone);
							Client.connection.writeMessage(
											MessageFactory.generateStreamResponse(ClientHandler.getUser().getUserID(), 
											contact.getAudioStreamID(),true));
							playerContact =contact;
//							player =AudioPlayer.start(contact.getAudioStreamID());
						}
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
			title.setText(contact.getTitle());
			aboutme.setText(contact.getAbout());
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
		getIntent().putExtra("UserProfile", 6);
		getIntent().putExtra("ClientID", contact.getUserID());
		setResult(RESULT_OK, getIntent());
		contact =null;
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
	
	public static void handleAudio(AudioStreamMessage msg) {
		if (player != null) {
			player.write(msg.getStreamID(), msg.buffer);
		}
	}
	
	public static void stopAudioPlayer() {
		player.stop();
		player =null;
		playerContact =null;
	}
	
	public void inviteToVideo(View view){
		
//		UUID u;
//		Client.connection.writeMessage(
//				MessageFactory.generateStreamProperty(playerContact.getUserID(), streamID, streamName, turnOn, type)
	}

	public static boolean updateUserProfile(UpdateProfileMessage msg) {
		if (contact == null) {
			return false;
		}
		
		if (contact.getUserID().equals(msg.getUserID())) {
			contact.setName(msg.getName());
			contact.setSurname(msg.getSurname());
			contact.setTitle(msg.getTitle());
			contact.setAbout(msg.getAboutMe());
			contact.setEmail(msg.getEmail());
			
			updateUserDetails();
			
			return true;
		} else {
			return false;
		}
	}

	public static void updateUserDetails(){
		
		MainWindow.runOnUI(new Runnable() {
		     @Override
		     public void run() {
		    	
		    	 if(image != null && name != null && surname != null && email != null && title != null && aboutme != null) {
		 			image.setImageBitmap(ClientHandler.getResizedBitmap(contact.getImage(),300,300));
		 			name.setText(contact.getName());
		 			surname.setText(contact.getSurname());
		 			email.setText(contact.getEmail());
					title.setText(contact.getTitle());
					aboutme.setText(contact.getAbout());
					
					SpannableString sName = new SpannableString(name.getText());
					SpannableString sSurname = new SpannableString(surname.getText());
					SpannableString sEmail = new SpannableString(email.getText());
					SpannableString sTitle = new SpannableString(title.getText());
					SpannableString sAboutMe = new SpannableString(aboutme.getText());
					
					sName.setSpan(new StyleSpan(Typeface.ITALIC), 0, sName.length(), 0);
					sSurname.setSpan(new StyleSpan(Typeface.ITALIC), 0, sSurname.length(), 0);
					sEmail.setSpan(new StyleSpan(Typeface.ITALIC), 0, sEmail.length(), 0);
					sTitle.setSpan(new StyleSpan(Typeface.ITALIC), 0, sTitle.length(), 0);
					sAboutMe.setSpan(new StyleSpan(Typeface.ITALIC), 0, sAboutMe.length(), 0);
					
		 			name.setText(sName, BufferType.SPANNABLE);
		 			surname.setText(sSurname, BufferType.SPANNABLE);
		 			email.setText(sEmail, BufferType.SPANNABLE);
					title.setText(sTitle, BufferType.SPANNABLE);
					aboutme.setText(sAboutMe, BufferType.SPANNABLE);
		 		}
			 }
		});
	}

	public static boolean updateUserAvatar(UpdateAvatarMessage msg) {
		if (contact == null) {
			return false;
		}
		
		if (contact.getUserID().equals(msg.getUserID())) {
			contact.setImage(msg.getAvatar());
			updateUserDetails();
			
			return true;
		} else {
			return false;
		}
	}
}
