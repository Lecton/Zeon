package com.gui;

import messages.media.VideoStreamMessage;

import com.gl.GlRenderer;
import com.gl.Square;
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
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.os.Build;

public class VideoStreamWindow extends Activity {

	/** The OpenGL view */
	private GLSurfaceView glSurfaceView;
	private static GlRenderer renderer;
	public static int width,height;
	private static Contact contact;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		final String uid = getIntent().getStringExtra("ClientID");
		if(uid != null){
		contact = ClientHandler.getFromUserID(uid);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display = getWindowManager().getDefaultDisplay(); 
        width = display.getWidth();
        height = display.getHeight();

        // Initiate the Open GL view and
        // create an instance with this activity
        glSurfaceView = new GLSurfaceView(this);
        
        // set our renderer to be the main renderer with
        // the current activity context
        renderer =new GlRenderer(this);
        glSurfaceView.setRenderer(renderer);
        setContentView(glSurfaceView);
		}else{
			Log.v("VideoStreamWindow - onCreate","No userID");
		}
    }

	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume() {
		super.onResume();
		glSurfaceView.onResume();
	}

	/**
	 * Also pause the glSurface
	 */
	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();
	}
	
	public static void handleVideo(VideoStreamMessage msg){
		if (renderer != null) {
			String image =msg.getImg();
			String streamID =msg.getStreamID();
			
			renderer.addImage(streamID, image);
		} else {
			throw new IllegalStateException("Video window not open.");
		}
	}
	
	@Override
	public void onBackPressed() {
		getIntent().putExtra("UserProfile", 6);
		getIntent().putExtra("ClientID", contact.getUserID());
		setResult(RESULT_OK, getIntent());		
		finish();
	}
}
