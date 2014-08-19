package com.gui;

import java.util.ArrayList;
import java.util.List;

import com.activitytut.R;
import com.activitytut.R.id;
import com.activitytut.R.layout;
import com.activitytut.R.menu;
import com.gui.utils.ClientAdapter;
import com.gui.utils.Contact;

import Messages.UserConnection.Greeting;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public final class GUIActivity extends ActionBarActivity{
	public static ListView LVContacts;
	public static ClientAdapter adapter;
	public static List<Contact> contacts;
	public static ListView listView = null;
	public static Context baseContext;
	public static Messages.UserConnection.NewUser message = null;
	public static Handler UIHandler;
	public ImageButton DP;
	public boolean microphoneClicked = true;
	public boolean videoClicked = true;
	public boolean streamClicked = true;
	private static ClientAdapter adp;
	final int SELECT_PHOTO = 1;
	public static Greeting greet =null;

	static 
	{
	    UIHandler = new Handler(Looper.getMainLooper());
	}
	public static void runOnUI(Runnable runnable) {
	    UIHandler.post(runnable);
	}
	
	public static void addRow(Messages.UserConnection.NewUser msg) {
		message = msg;
				
				GUIActivity.runOnUI(new Runnable() {
				     @Override
				     public void run() {

							addContact(message);

				    }
				});				
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gui);
		
		contacts = new ArrayList<>();
		baseContext = getBaseContext();
		listView = (ListView) findViewById(R.id.contactList);
		ImageButton im = (ImageButton) findViewById(R.id.profilePicture);
		im.setImageBitmap(Contact.getImageBitMap(GUIActivity.greet.getAvatar(),250,250));
		
	}

	public static void addContact(Messages.UserConnection.NewUser message){
		if(message != null && contacts != null){
			contacts.add(new Contact(message));
			adp = new ClientAdapter(baseContext,contacts);
			listView.setAdapter(adp);
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//			listView.setItemChecked(2, true);
//			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
//
//				@Override
//				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//					Log.v("tag","something");
//					return false;
//				}
//
//				@Override
//				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//					Log.v("tag","something");
//					return false;
//				}
//
//				@Override
//				public boolean onActionItemClicked(ActionMode mode,
//						MenuItem item) {
//					Log.v("tag","something");
//					return false;
//				}
//
//				@Override
//				public void onDestroyActionMode(ActionMode mode) {
//					Log.v("tag","something");
//					
//				}
//
//				@Override
//				public void onItemCheckedStateChanged(ActionMode mode,
//						int position, long id, boolean checked) {
//					// TODO Auto-generated method stub
//					int count = listView.getCheckedItemCount();
//					Log.v("Count",count + "");
//					
//				}
//				 
//	        });
			listView.setOnItemClickListener(new OnItemClickListener() 
			 { 
				public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
				{
					  String name = contacts.get(position).getName();
					  Log.v("tag",name);
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
			View rootView = inflater.inflate(R.layout.fragment_gui, container,
					false);
			return rootView;
		}
	}


	public void ChangeDP(View view)
	{
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);		
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
