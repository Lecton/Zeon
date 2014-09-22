package com.mobile;

import messages.update.UpdateListMessage;

import com.communication.Connection;
import com.gui.ClientProfileWindow;
import com.gui.LoginWindow;
import com.gui.MainWindow;
import com.gui.ContactWindow;
import com.gui.ProfileWindow;
import com.gui.SplashWindow;
import com.gui.utils.Contact;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class Client extends ActionBarActivity { 
	public static Context clientContext;
	
	final int SPLASH_RESULT = 1;
	final int LOGIN_RESULT = 2;
	final int MAIN_RESULT = 3;
	final int MESSAGE_RESULT = 4;
	final int PROFILE_RESULT = 5;

	public static Connection connection;

	public static void getCachedPath() {
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		clientContext =getBaseContext();
		connection = new Connection("10.0.2.2", 2014);
		try {
			connection.makeConnection();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		startActivityForResult(new Intent(getApplicationContext(),SplashWindow.class),SPLASH_RESULT);
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
						Intent i = new Intent(getApplicationContext(),LoginWindow.class);
						startActivityForResult(i,LOGIN_RESULT);
						}else{
							Log.e("SPLASH_RESULT","Splash not complete");
						}
					}
					break;
					
			case LOGIN_RESULT:
					if(resultCode == RESULT_OK){
						Contact loggedUser = (Contact)in.getExtras().getSerializable("LoggedInUser");
						ClientHandler.setUser(loggedUser);
						Intent i = new Intent(getApplicationContext(),MainWindow.class);
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
							finish();
						}
						else if(u_result == 1){
							Intent i = new Intent(getApplicationContext(),ProfileWindow.class);
							startActivityForResult(i, MAIN_RESULT);
								
						}else if(u_result == 2){
							Intent i = new Intent(getApplicationContext(),MainWindow.class);
							startActivityForResult(i, MAIN_RESULT);
							
						}else if(u_result == 3){
							String clientID = in.getExtras().getString("ClientID");
							Intent i = new Intent(getApplicationContext(),ClientProfileWindow.class);
							i.putExtra("ClientID", clientID);
							startActivityForResult(i, MAIN_RESULT);
						}
						else{
							String clientID = in.getExtras().getString("ClientID");
	//						Contact user = (Contact)in.getExtras().getSerializable("User");
							Intent i = new Intent(getApplicationContext(), ContactWindow.class);
							i.putExtra("ClientID", clientID);
	//						i.putExtra("User", user);
							startActivityForResult(i, MESSAGE_RESULT);
						}
					}
					break;
			case MESSAGE_RESULT:
				if(resultCode == RESULT_OK){
					
	//					Contact user = (Contact)in.getExtras().getSerializable("User");
	//					Contact client = (Contact)in.getExtras().getSerializable("Client");
						Intent i = new Intent(getApplicationContext(),MainWindow.class);
	//					i.putExtra("UserDetails", user);
	//					i.putExtra("Client", client);
						startActivityForResult(i, MAIN_RESULT);
					
				}
				break;
			default:
					Log.v("DEFAULT_RESULT","Called");
					break;
		}		
	}	

}
