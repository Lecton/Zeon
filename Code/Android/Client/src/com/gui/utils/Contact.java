package com.gui.utils;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;

public class Contact {

	 private Bitmap image;
	 private String name;
	 private String email;
	 private int ID;

	 public Contact(Messages.UserConnection.NewUser message) {
		  this.name = message.getSender();
		  this.email = message.getEmail();
		  this.ID = message.getUserID();
		  
		  byte[] decodedString = Base64.decode(message.getAvatar(), Base64.DEFAULT);
		  image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	 }

	 public int getID() {
		 return ID;
	 }
	 
	 public Bitmap getImage(){
		 return image;
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
		 
	 }
	 	 
	}