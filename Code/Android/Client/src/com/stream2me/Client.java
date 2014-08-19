package com.stream2me;

import com.activitytut.R;
import com.gui.GUIActivity;
import com.gui.LoginActivity;
import com.stream2me.connection.Connection;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
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
import android.os.Build;
import android.preference.PreferenceManager.OnActivityResultListener;

public class Client extends ActionBarActivity {

	final int LOGIN_RESULT = 1;
	final int GUI_RESULT = 2;
	private static Connection connection = new Connection("10.0.2.2", 2014);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		connection.makeConnection();

		startActivityForResult(new Intent(getApplicationContext(),LoginActivity.class),LOGIN_RESULT);
//		startActivity(new Intent(getApplicationContext(),LoginActivity.class));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		
		switch(requestCode){
			case LOGIN_RESULT:
					if(resultCode == RESULT_OK){
						startActivityForResult(new Intent(getApplicationContext(),GUIActivity.class),GUI_RESULT);
					}
					break;
		
			case GUI_RESULT:
					if(resultCode == RESULT_OK){
						Log.v("GUI_RESULT","Close");
					}
					break;
					
			default:
					Log.v("DEFAULT_RESULT","Called");
					break;
		}
		
	}
}
