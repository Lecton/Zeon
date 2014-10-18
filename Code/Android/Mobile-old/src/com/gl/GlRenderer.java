/**
 * 
 */
package com.gl;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import biz.source_code.base64Coder.Base64Coder;

import com.gui.VideoStreamWindow;
import com.mobile.ClientHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Debug;
import android.util.Log;

/**
 * @author Lecton
 *
 */
public final class GlRenderer implements Renderer {
	private AtomicInteger counter =new AtomicInteger(0);
	
	private Square 		square;		// the square
	private Context 	context;
	private ConcurrentLinkedQueue<String> images =new ConcurrentLinkedQueue<>();
	/** Constructor to set the handed over context */
	public GlRenderer(Context context) {
		this.context = context;
		
		// initialise the square
		this.square = new Square(this);
	}


	@Override
	public void onDrawFrame(GL10 gl) {
		// clear Screen and Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Reset the Modelview Matrix
		gl.glLoadIdentity();
		
		// Drawing
		gl.glTranslatef(0.0f, 0.0f, -5.0f);		// move 5 units INTO the screen
												// is the same as moving the camera 5 units away
//		gl.glScalef(0.5f, 0.5f, 0.5f);			// scale the square to 50% 
		if(square != null && gl != null) {
			try {
				square.updateImage(gl);			
				square.draw(gl);						// Draw the square
			} catch (NullPointerException e) {}
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Load the texture for the square
		square.loadGLTexture(gl, this.context);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);			//Enable Texture Mapping ( NEW )
		gl.glShadeModel(GL10.GL_SMOOTH); 			//Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	//Black Background
		gl.glClearDepthf(1.0f); 					//Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); 			//Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); 			//The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
		
		
	}
	
	protected Bitmap getImage() {
		String image =images.poll();
		if (image != null) {
			Log.v("Image counter", "Count: "+counter.incrementAndGet());
//			BitmapWithOptions src = ClientHandler.getImageBitMapWithOptions(image);
//			src.options.inJustDecodeBounds = true;
//			// First decode with inJustDecodeBounds=true to check dimensions
//			// Calculate inSampleSize
//			src.options.inSampleSize = calculateInSampleSize(src.options, VideoStreamWindow.width, VideoStreamWindow.height);
//			// Decode bitmap with inSampleSize set
//			src.options.inJustDecodeBounds = false;
//			return src.getImage();
			
			byte[] decodedString = Base64Coder.decode(image);
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options);			
			// Calculate inSampleSize
			options.inSampleSize = calculateInSampleSize(options, VideoStreamWindow.width, VideoStreamWindow.height);
			// Decode bitmap with inSampleSize set
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            int[] pids = { android.os.Process.myPid() };
            
            Double free = new Double(Debug.getNativeHeapFreeSize())/1024.0;
            Bitmap src =BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options);
            double size =src.getWidth()*src.getHeight()*4;
            if (free < size*1.5) {
//				counter.set(0);
            	if(square != null){
					square.reloadTexture();
					Log.v("GLRenderer MemClear", "Pausing for memory clearing.");
					try {
						int contained =images.size();
						images.clear();
						Thread.sleep(100);
					} catch (InterruptedException e) {
						Log.e("MemClear sleep error", e.getMessage());
					}
            	}
			}
//			
            
            return src;		
		} else {
			return null;
		}
	}

	 
	//Given the bitmap size and View size calculate a subsampling size (powers of 2) 
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
	
	public void addImage(String streamID, String image) {
		if (square != null && square.isOpen()) {
			images.add(image);
		}
	}
	
	public void close(){
		square.close();
		square = null;
		images.clear();
	}
}
