package com.gui;

import java.util.ArrayList;
import java.util.List;

import com.activitytut.R;
import com.activitytut.R.id;
import com.activitytut.R.layout;
import com.activitytut.R.menu;
import com.gui.utils.ClientAdapter;
import com.gui.utils.Contact;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.os.Build;

public final class GUIActivity extends ActionBarActivity {
	public static ListView LVContacts;
	public static ClientAdapter adapter;
	public static List<String> contacts;
	public static ListView list = null;
	public static Context baseContext;
	public static Messages.UserConnection.NewUser m = null;
	public static Handler UIHandler;

	static 
	{
	    UIHandler = new Handler(Looper.getMainLooper());
	}
	public static void runOnUI(Runnable runnable) {
	    UIHandler.post(runnable);
	}
	
	public static void addRow(Messages.UserConnection.NewUser msg) {
			m = msg;
				
				GUIActivity.runOnUI(new Runnable() {
				     @Override
				     public void run() {

							addContact(m.getSender());

				    }
				});				
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gui);
		
		contacts = new ArrayList<>();
		baseContext = getBaseContext();
        final Button show=(Button) findViewById(R.id.button1);
        list=(ListView) findViewById(R.id.listView1);
        
        show.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {

				addContact("List " + (contacts.size() + 1));
			}
        });        
		
	}

	public static void addContact(String name){
		Log.v("name", name);
		if(name != null && contacts != null){
			contacts.add(name);
			ArrayAdapter<String> adp=new ArrayAdapter<String>(baseContext,R.layout.list,contacts);
			list.setAdapter(adp);
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


}
