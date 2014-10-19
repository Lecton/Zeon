package com.publicCaptivation.stream2me.client;

import com.publicCaptivation.stream2me.client.communication.Connection;
import com.publicCaptivation.stream2me.client.gui.ClientProfileWindow;
import com.publicCaptivation.stream2me.client.gui.ContactWindow;
import com.publicCaptivation.stream2me.client.gui.LoginWindow;
import com.publicCaptivation.stream2me.client.gui.MainWindow;
import com.publicCaptivation.stream2me.client.gui.ProfileWindow;
import com.publicCaptivation.stream2me.client.gui.SplashWindow;
import com.publicCaptivation.stream2me.client.gui.VideoStreamWindow;
import com.publicCaptivation.stream2me.client.gui.utils.User;

import messages.update.UpdateListMessage;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Client extends ActionBarActivity { 
	public static Context clientContext;
	
	final int SPLASH_RESULT = 1;
	final int LOGIN_RESULT = 2;
	final int MAIN_RESULT = 3;
	final int MESSAGE_RESULT = 4;
	final int PROFILE_RESULT = 5;
	final String HOST = "192.168.1.37";//"bwmuller.duckdns.org";//"10.0.2.2";
	public static Connection connection;

	public static void getCachedPath() {}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);
		clientContext = getBaseContext();
		
			connection = new Connection(HOST, 2014);
			try {
				connection.makeConnection();
				startActivityForResult(new Intent(getApplicationContext(),SplashWindow.class),SPLASH_RESULT);
			} catch (Exception e) {
				e.printStackTrace();
				final Dialog dialog = new Dialog(this);

				//tell the Dialog to use the dialog.xml as it's layout description
				dialog.setContentView(R.layout.dialog);
				dialog.setTitle("Android Custom Dialog Box");

				TextView txt = (TextView) dialog.findViewById(R.id.txt);

				txt.setText("Could not connect to the server, sorry :(");
				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton);

				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						finish();
					}
				});

				dialog.show();
//				e.printStackTrace();
			}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_client,
					container, false);
			return rootView;
		}
	}
	
	
	@Override
	protected void onDestroy() {
		connection.close();
		super.onDestroy();
	}

	public static Connection getConnection() {
		return connection;
	}
	
	@Override
	public void finishFromChild(Activity child) {
		Log.v("Client",child.getClass().getSimpleName()+" finished");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent in) {
		
		switch(requestCode){
			case SPLASH_RESULT:
					if(resultCode == RESULT_OK){
						
						boolean s_result = in.getExtras().getBoolean("SplashComplete");
						if(s_result){
						Intent i = new Intent(getApplicationContext(), LoginWindow.class);
						startActivityForResult(i,LOGIN_RESULT);
						}else{
							Log.e("SPLASH_RESULT","Splash not complete");
						}
					}
					break;
					
			case LOGIN_RESULT:
					if(resultCode == RESULT_OK){
						User loggedUser = (User)in.getExtras().getSerializable("LoggedInUser");
						ClientHandler.setUser(loggedUser);
						Intent i = new Intent(getApplicationContext(), MainWindow.class);
//						i.putExtra("UserDetails", loggedUser);
						Client.connection.writeMessage(new UpdateListMessage(loggedUser.getUserID()));
						startActivityForResult(i, MAIN_RESULT);
					}
					break;
					
			case MAIN_RESULT:
					if(resultCode == RESULT_OK){
						int u_result = 0;
						u_result = in.getExtras().getInt("UserProfile");

						if(u_result == -1){
							Log.v("Closing application","Close");
							finish();
						}
						else if(u_result == 1){
							Log.v("Activity","Profile");
							Intent i = new Intent(getApplicationContext(), ProfileWindow.class);
							startActivityForResult(i, MAIN_RESULT);
						}else if(u_result == 2){
							Intent i = new Intent(getApplicationContext(),MainWindow.class);
							startActivityForResult(i, MAIN_RESULT);
						}else if(u_result == 3){
							String clientID = in.getExtras().getString("ClientID");
							Intent i = new Intent(getApplicationContext(), ClientProfileWindow.class);
							i.putExtra("ClientID", clientID);
							startActivityForResult(i, MAIN_RESULT);
						}
						else if(u_result == 4){
							String clientID = ClientHandler.getUser().getUserID();
							Intent i = new Intent(getApplicationContext(), ContactWindow.class);
							i.putExtra("ClientID", clientID);
							startActivityForResult(i, MAIN_RESULT);
						}
						else if(u_result == 5){
							String clientID = in.getExtras().getString("ClientID");
							Intent i = new Intent(getApplicationContext(), VideoStreamWindow.class);
							i.putExtra("ClientID", clientID);
							startActivityForResult(i, MAIN_RESULT);
						}else if(u_result == 6){
							String clientID = in.getExtras().getString("ClientID");
							Intent i = new Intent(getApplicationContext(), ContactWindow.class);
							i.putExtra("ClientID", clientID);
							startActivityForResult(i, MAIN_RESULT);
						}
					}
					break;
//			case MESSAGE_RESULT:
//				if(resultCode == RESULT_OK){
//					
//	//					Contact user = (Contact)in.getExtras().getSerializable("User");
//	//					Contact client = (Contact)in.getExtras().getSerializable("Client");
//						String ClientID
//						Intent i = new Intent(getApplicationContext(),MainWindow.class);
//	//					i.putExtra("UserDetails", user);
//	//					i.putExtra("Client", client);
//						startActivityForResult(i, MAIN_RESULT);
//					
//				}
//				break;
			default:
					Log.v("DEFAULT_RESULT","Called");
					break;
		}		
	}	

}
