package com.gui;

import messages.media.VideoStreamMessage;

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
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.os.Build;

public class VideoStreamWindow extends Activity {
	public static ImageView vid;
	public static Handler UIHandler;
	static 
	{
	    UIHandler = new Handler(Looper.getMainLooper());
	}
	
	public static void runOnUI(Runnable runnable) {
	    UIHandler.post(runnable);
	}
	
	private static Contact contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_stream_window);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		String uid = getIntent().getStringExtra("ClientID");
		
		if(uid != null){
			vid =(ImageView)findViewById(R.id.vidoeImage);
			BitmapDrawable icon = null;
			contact = ClientHandler.getFromUserID(uid);
			setTitle(contact.getName());
			icon = new BitmapDrawable(getResources(),contact.getImage());
			getActionBar().setIcon(icon);
			
		}else{
			Log.v("VideoStream","onCreate");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_stream_window, menu);
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
			View rootView = inflater.inflate(
					R.layout.fragment_video_stream_window, container, false);
			return rootView;
		}
	}
	

	@Override
	public void onBackPressed() {
		Client.connection.writeMessage(
				MessageFactory.generateStreamResponse(ClientHandler.getUser().getUserID(), 
						ClientHandler.getFromUserID(contact.getUserID()).getVideoStreamID(), false));
		
		getIntent().putExtra("UserProfile", 3);
		getIntent().putExtra("ClientID", contact.getUserID());
		contact =null;
		setResult(RESULT_OK, getIntent());		
		finish();
	}

	public static void handleVideo(final VideoStreamMessage msg) {
		if (contact != null) {
			if (msg.getUserID().equals(contact.getUserID())) {
				if (vid != null) {
					VideoStreamWindow.runOnUI(new Runnable() {
						
						@Override
						public void run() {
							vid.setImageBitmap(ClientHandler.getImageBitMap(msg.getImg(), 150, 150));
							vid.refreshDrawableState();
						}
					});
				}
			}
		}
	}	

}
