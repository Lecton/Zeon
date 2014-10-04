package com.gltut03;

import com.gl.GlRenderer;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	/** The OpenGL view */
	private GLSurfaceView glSurfaceView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		  
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initiate the Open GL view and
        // create an instance with this activity
        glSurfaceView = new GLSurfaceView(this);
        
        // set our renderer to be the main renderer with
        // the current activity context
        glSurfaceView.setRenderer(new GlRenderer(this));
        setContentView(glSurfaceView);
    }

	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume() {
		super.onResume();
		System.gc();
		glSurfaceView.onResume();
	}

	/**
	 * Also pause the glSurface
	 */
	@Override
	protected void onPause() {
		super.onPause();
		System.gc();
		glSurfaceView.onPause();
	}
}
