/**
 * 
 */

package com.publicCaptivation.stream2me.client.gl;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * @author Lecton
 *
 */
public class Square {
	private FloatBuffer vertexBuffer;	// buffer holding the vertices
	private float vertices[] = {
			-3.0f, -2.0f,  0.0f,		// V1 - bottom left
			-3.0f,  2.0f,  0.0f,		// V2 - top left
			 3.0f, -2.0f,  0.0f,		// V3 - bottom right
			 3.0f,  2.0f,  0.0f			// V4 - top right 
	};

	private FloatBuffer textureBuffer;	// buffer holding the texture coordinates
	private float texture[] = {    		
			// Mapping coordinates for the vertices
			0.0f, 1.0f,		// top left		(V2)
			0.0f, 0.0f,		// bottom left	(V1)
			1.0f, 1.0f,		// top right	(V4)
			1.0f, 0.0f		// bottom right	(V3)
	};
	
	/** The texture pointer */
	private int[] textures = new int[1];
	private Bitmap bitmap;
	private GL10 gl;
	private GlRenderer renderer;
	private boolean open =false;
	
	public Square(GlRenderer renderer) {
		this.renderer =renderer;
		
		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		// allocates the memory from the byte buffer
		vertexBuffer = byteBuffer.asFloatBuffer();
		
		// fill the vertexBuffer with the vertices
		vertexBuffer.put(vertices);
		
		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);
		
		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
		open =true;
	}

//	public Bitmap StringToBitMap(String encodedString){
//	     try{
//	       byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
//	       return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//	     }catch(Exception e){
//	       e.getMessage();
//	       return null;
//     }
//	}	
	
	public void close(){
		open =false;
		gl.glDeleteTextures(1, textures, 0);
		recycle();
	}
	
	public boolean isOpen() {
		return open;
	}
	
	protected void reloadTexture() {
		gl.glDeleteTextures(1, textures, 0);
		
		// generate one texture pointer
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
	}
	
	/**
	 * Load the texture for the square
	 * @param gl
	 * @param context
	 */
	public void loadGLTexture(GL10 gl, Context context) {
		this.gl =gl;
		
		// generate one texture pointer
		gl.glGenTextures(1, textures, 0);
		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		
		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		
		bitmap = renderer.getImage();
		
		if(bitmap != null){
			
			// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			// Clean up
			recycle();
			
		}else{
			Log.e("Square - loadGLTexture", "bitmap is null.");
		}
		Log.v("GLRenderer", "new Image");
	}
	
	public void updateImage(GL10 gl){
		bitmap = renderer.getImage();
		
		if(bitmap != null){
//			ByteBuffer byteBuf = ByteBuffer.allocate(bitmap.getByteCount());
//			bitmap.copyPixelsToBuffer(byteBuf);
//			gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, 0, 255, 255, 0, 0, 0, byteBuf);
			
			
			// Use Android GLUtils to specify a two-dimensional texture image from our bitmap 
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			
			// Clean up
			recycle();
		}
	}
	
	
	/** The draw method for the square with the GL context */
	public void draw(GL10 gl) {
		// bind the previously generated texture
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		
		// Point to our buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		// Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		
		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		// Draw the vertices as triangle strip
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);

		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}
	
	public void recycle(){
		if(bitmap != null){
			bitmap.recycle();
		}
		bitmap = null;
		System.gc();
	}
	
//	public Bitmap getImage(){
//
//		String image = renderer.getImage();
//		if (image != null) {
//			BitmapWithOptions src = ClientHandler.getImageBitMapWithOptions(image);
//			
//	// First decode with inJustDecodeBounds=true to check dimensions
//			src.options.inJustDecodeBounds = true;
////			BitmapFactory.decodeResource(context.getResources(),
////					src, options);
//			// Calculate inSampleSize
//			src.options.inSampleSize = calculateInSampleSize(src.options, VideoStreamWindow.width, VideoStreamWindow.height);
//			// Decode bitmap with inSampleSize set
//			src.options.inJustDecodeBounds = false;
//			return src.getImage();
//		} else {
//			return null;
//		}
//	}
}
