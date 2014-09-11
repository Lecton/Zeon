package com.gui;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import messages.update.UpdateAvatarMessage;
import messages.update.UpdateNameMessage;

import com.mobile.Client;
import com.mobile.ClientHandler;
import com.mobile.R;
import com.mobile.R.id;
import com.mobile.R.layout;
import com.mobile.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Build;
import android.provider.MediaStore.MediaColumns;

public class ProfileWindow extends Activity {

	protected ImageButton profileImage = null;
	protected EditText eName = null;
	protected EditText eSurname = null; 
	private final int SELECT_PHOTO = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_window);
		
		profileImage = (ImageButton) findViewById(R.id.profileImg);
		eName = (EditText) findViewById(R.id.userName);
		eSurname = (EditText) findViewById(R.id.userSurname);
		  
		if(setUpdate()){
			Button updateBtn = (Button) findViewById(R.id.updateBtn);
			updateBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ClientHandler.getUser().setName(eName.getText().toString());
					ClientHandler.getUser().setSurname(eSurname.getText().toString());
					
					Client.getConnection().writeSafe(new UpdateNameMessage(ClientHandler.getUser().getUserID(), eName.getText().toString(), eSurname.getText().toString()));
					setResult(RESULT_OK, getIntent());		
					finish();					
				}
			});
			
			profileImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
					photoPickerIntent.setType("image/*");
					startActivityForResult(photoPickerIntent, SELECT_PHOTO);
				}
			});
		}else{
			Log.v("ProfileWindow---onCreate", "Failed to setup fileds.");
		}
		
	}

	public boolean setUpdate(){
		if(profileImage != null && eName != null && eSurname != null){
			profileImage.setImageBitmap(ClientHandler.getUser().getResizedBitmap(ClientHandler.getUser().getImage(),300,300));
			eName.setText(ClientHandler.getUser().getName());
			eSurname.setText(ClientHandler.getUser().getSurname());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_window, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_profile_window,
					container, false);
			return rootView;
		}
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent in) { 
        super.onActivityResult(requestCode, resultCode, in); 
 
        switch(requestCode) { 
        case SELECT_PHOTO:
            if(resultCode == RESULT_OK){
				Uri selectedImageUri = in.getData();
				String tempPath = getPath(selectedImageUri, ProfileWindow.this);
				Bitmap bm;
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
				profileImage.setImageBitmap(bm);
				Client.getConnection().writeSafe(new UpdateAvatarMessage(ClientHandler.getUser().getUserID(),ClientHandler.BitMapToString(bm)));
 
            }
        }
    }
    

    public String getPath(Uri uri, Activity activity) {
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity
				.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}    
	

}
