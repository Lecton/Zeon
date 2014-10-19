package com.publicCaptivation.stream2me.client.gui;

import java.io.FileNotFoundException;
import java.io.InputStream;

import com.publicCaptivation.stream2me.client.Client;
import com.publicCaptivation.stream2me.client.ClientHandler;
import com.publicCaptivation.stream2me.client.R;
import com.publicCaptivation.stream2me.client.gui.utils.MessageFactory;

import messages.Message;
import messages.update.UpdateAvatarMessage;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import android.provider.MediaStore.MediaColumns;

public class ProfileWindow extends Activity {

	protected ImageButton profileImage = null;
	protected EditText eName = null;
	protected EditText eSurname = null;
	protected EditText eAbout = null; 
	private final int SELECT_PHOTO = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_window);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		profileImage = (ImageButton) findViewById(R.id.profileImg);
		eName = (EditText) findViewById(R.id.userName);
		eSurname = (EditText) findViewById(R.id.userSurname);
		eAbout = (EditText) findViewById(R.id.userAbout);
		  
		if(init()){
			Button updateBtn = (Button) findViewById(R.id.updateBtn);
			updateBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ClientHandler.getUser().setName(eName.getText().toString());
					ClientHandler.getUser().setSurname(eSurname.getText().toString());
					ClientHandler.getUser().setAbout(eAbout.getText().toString());
					
					String userID =ClientHandler.getUser().getUserID();
					String name =eName.getText().toString();
					String surname =eSurname.getText().toString();
					String email =ClientHandler.getUser().getEmail();
					String title ="";
					String aboutMe =eAbout.getText().toString();
					
					Client.getConnection().writeMessage(MessageFactory.generateUpdateProfile(userID, name, surname, email, title, aboutMe));
					getIntent().putExtra("UserProfile", 2);
					setResult(RESULT_OK, getIntent());		
					finish();					
				}
			});
			
//			profileImage.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//					photoPickerIntent.setType("image/*");
//					startActivityForResult(photoPickerIntent, SELECT_PHOTO);
//				}
//			});
		}else{
			Log.v("ProfileWindow---onCreate", "Failed to setup fileds.");
		}
		
	}

	public boolean init(){
		if(profileImage != null && eName != null && eSurname != null){
			profileImage.setImageBitmap(ClientHandler.getResizedBitmap(ClientHandler.getUser().getImage(),500,500));
			eName.setText(ClientHandler.getUser().getName());
			eSurname.setText(ClientHandler.getUser().getSurname());
			eAbout.setText(ClientHandler.getUser().getAbout());
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
	public void onBackPressed() {
		getIntent().putExtra("UserProfile", 2);
		setResult(RESULT_OK, getIntent());		
		finish();
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
				Client.getConnection().writeMessage(new UpdateAvatarMessage(ClientHandler.getUser().getUserID(),ClientHandler.BitMapToString(bm)));
//            	Uri selectedImageUri = in.getData();
//				try {
//					BitmapFactory.Options options = new BitmapFactory.Options();
//					options.inJustDecodeBounds = true;
//					BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri), null, options);
//					
//					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
//					btmapOptions.inSampleSize = calculateInSampleSize(options, 200, 200);
//	                Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri), null, btmapOptions);
//	                String avatar =ClientHandler.BitMapToString(bm);
//					ClientHandler.getUser().setImage(avatar);
//					profileImage.setImageBitmap(bm);
//					Client.getConnection().writeMessage(new UpdateAvatarMessage(ClientHandler.getUser().getUserID(), avatar));
//				} catch (FileNotFoundException e) {
//				}
//            	
//				Uri selectedImageUri = in.getData();
//				String tempPath = getPath(selectedImageUri, ProfileWindow.this);
//				Bitmap bm;
//				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
//				bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
//				bm =ClientHandler.getResizedBitmap(bm, 200, 200);
//				String avatar =ClientHandler.BitMapToString(bm);
//				ClientHandler.getUser().setImage(avatar);
//				profileImage.setImageBitmap(ClientHandler.getUser().getImage());
//				Client.getConnection().writeMessage(new UpdateAvatarMessage(ClientHandler.getUser().getUserID(), avatar));
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
//		String[] projection = { MediaColumns.DATA };
//		Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);;
////		Cursor cursor = activity
////				.managedQuery(uri, projection, null, null, null);
//		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
//		cursor.moveToFirst();
//		return cursor.getString(column_index);
	}    
	
    public void createVideoStream(View view) {
    	if(ClientHandler.getUser().isStreamingVideo()){
    		Message msg = (MessageFactory.generateStreamProperty(
							ClientHandler.getUser().getUserID(), ClientHandler.getUser().getVideoStreamingID(), 
							ClientHandler.getUser().getVideoStreamingName(), false, 0));
    		Client.connection.writeMessage(msg);
    		ClientHandler.getUser().setVideoStreamingName(null);
    	} else {
    		String name = ClientHandler.getUser().getVideoStreamingName() == null ? 
                    "videoStream" : ClientHandler.getUser().getVideoStreamingName();
    		
            Message msg = MessageFactory.generateStreamProperty(
            		ClientHandler.getUser().getUserID(), null, name, true, 0);
            Client.connection.writeMessage(msg);
            ClientHandler.getUser().setVideoStreamingName(name);
    	}
    }
    
    static int calculateInSampleSize( BitmapFactory.Options options, int reqWidth, int reqHeight) {
	    int inSampleSize = 1;	//Default subsampling size
		// See if image raw height and width is bigger than that of required view
		if (options.outHeight > reqHeight || options.outWidth > reqWidth) {
			//bigger
			final int halfHeight = options.outHeight / 2;
			final int halfWidth = options.outWidth / 2;
			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
		    while ((halfHeight / inSampleSize) > reqHeight 
		    		&& (halfWidth / inSampleSize) > reqWidth) {
		    	inSampleSize *= 2;
		    }
		}
		return inSampleSize;
    }
}
