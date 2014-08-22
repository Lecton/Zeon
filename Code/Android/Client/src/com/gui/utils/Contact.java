package com.gui.utils;

import java.io.File;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.util.Base64;
import android.widget.AdapterView.OnItemClickListener;

public class Contact implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	 private String image;
	 private String name;
	 private String email;
	 private int UserID;
	 
	 public Contact(Messages.UserConnection.Greeting message) {
		  this.name = message.getSender();
		  this.email = message.getEmail();
		  this.UserID = message.getUserID();
		  
		  image = message.getAvatar();
	 }

	 public Contact(Messages.UserConnection.NewUser message) {
		  this.name = message.getSender();
		  this.email = message.getEmail();
		  this.UserID = message.getUserID();
		  
		  image = message.getAvatar();
	 }

	 public int getUserID() {
		 return UserID;
	 }
	 
	 public Bitmap getImage(){
		 return getImageBitMap(image);
	 }

	 public String getName() {
		 return name;
	 }

	 public String getEmail() {
		 return email;
	 }

	 public void setName(String n) {
		 this.name = n;
	 }

	 public void setEmail(String e) {
		 this.email = e;
	 }

	 public void setImage(String si){
		 this.image =si;
	 }
	 
	 public void setUserID(int userID) {
		 UserID = userID;
	 }
	 
	 public static Bitmap getImageBitMap(String message){
		 byte[] decodedString = Base64.decode(message, Base64.DEFAULT);
		  return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	 }
	 
	 public static Bitmap getImageBitMap(String message,int height,int width){
		 byte[] decodedString = Base64.decode(message, Base64.DEFAULT);
		  return getResizedBitmap(
				  	BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length),height,width);
	 }
	 
	  public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	        int width = bm.getWidth();
	        int height = bm.getHeight();
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = ((float) newHeight) / height;
	        // CREATE A MATRIX FOR THE MANIPULATION
	        Matrix matrix = new Matrix();
	        // RESIZE THE BIT MAP
	        matrix.postScale(scaleWidth, scaleHeight);

	        // "RECREATE" THE NEW BITMAP
	        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	        return resizedBitmap;
	  }		 
	 	 
	}