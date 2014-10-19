package com.publicCaptivation.stream2me.client.gui;

import messages.media.VideoStreamMessage;

import com.publicCaptivation.stream2me.client.Client;
import com.publicCaptivation.stream2me.client.ClientHandler;
import com.publicCaptivation.stream2me.client.gl.GlRenderer;
import com.publicCaptivation.stream2me.client.gui.utils.Contact;
import com.publicCaptivation.stream2me.client.gui.utils.MessageFactory;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

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
//        Point p =new Point();
//        display.getSize(p);
//        width =p.x;
//        height =p.y;
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
			if (contact != null) {
				if (contact.getVideoStreamID().equals(streamID)) {
					renderer.addImage(streamID, image);
				}
			}
		} else {
//			throw new IllegalStateException("Video window not open.");
		}
	}
	
	@Override
	public void onBackPressed() {
		synchronized(this){
			Client.connection.writeMessage(
					MessageFactory.generateStreamResponse(ClientHandler.getUser().getUserID(), 
							contact.getVideoStreamID(), false));
			
			renderer.close();
			renderer =null;
			
			String contactID = contact.getUserID();
			contact =null;
			
			getIntent().putExtra("UserProfile", 6);
			getIntent().putExtra("ClientID", contactID);
			setResult(RESULT_OK, getIntent());
			finish();
		}
	}
}
