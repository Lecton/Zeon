package com.gl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapWithOptions {
	byte[] imageBytes;
	BitmapFactory.Options options;
	
	public BitmapWithOptions(byte[] imageBytes, BitmapFactory.Options options) {
		this.imageBytes =imageBytes;
		this.options =options;
	}
	
	public Bitmap getImage() {
		return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
	}
}
