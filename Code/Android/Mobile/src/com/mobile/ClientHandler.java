package com.mobile;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import messages.Message;
import messages.StringMessage;
import messages.media.communication.StreamNotifyMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import biz.source_code.base64Coder.Base64Coder;

import com.gui.MainWindow;
import com.gui.utils.Contact;

public class ClientHandler {
	private static List<Contact> contacts =new ArrayList<Contact>();
	private static Contact user;
	
	public ClientHandler() throws Exception {
		throw new Exception("Do not create ME!!!!!!");
	}
	
	protected static boolean setUser(Contact loggedUser) {
		if (user == null && loggedUser != null) {
			user =loggedUser;
			for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
				Contact c = it.next();
				if (c.getUserID() == user.getUserID()) {
					contacts.remove(c);
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static Contact getUser() {
		return user;
	}
	
	public static List<Contact> getContacts() {
		return contacts;
	}
	
	public static Contact get(int position) {
		return contacts.get(position);
	}
	
	public static Contact getFromUserID(String userID) {
		for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
			Contact c = it.next();
			if (c.getUserID().equals(userID)) {
				return c;
			}
		}
		return null;
	}
	
	public static void setClientNotifocation(StreamNotifyMessage msg){
		Contact c = getFromUserID(msg.getUserID());
		
		if(c != null){
			if (msg.getAction()) {
				if (msg.getType() == 0) {
					c.setVideoNoticationOn();
					c.setVideoStreamID(msg.getStreamID());
				} else {
					c.setAudioNoticationOn();
					c.setAudioStreamID(msg.getStreamID());
				}
			} else {
				if (msg.getType() == 0) {
					c.setVideoNoticationOff();
					c.setVideoStreamID(null);
				} else {
					c.setAudioNoticationOff();
					c.setAudioStreamID(null);
				}
			}
			MainWindow.updateClientList();
		}else{
			Log.v("ClientHandler","client does not exist.");
		}
		
	}
	
	public static boolean updateContact(Contact client) {
		for (Iterator<Contact> it = contacts.iterator(); it.hasNext();) {
			Contact c = it.next();
			if (c.getUserID() == client.getUserID()) {
				c =client;
				return true;
			}
		}
		return false;
	}
	
	public static boolean handleNewUserMessage(NewUserMessage message) {
		Contact c =new Contact(message);
		if (user != null && c.getUserID() != user.getUserID()) {
			if(!(contacts.contains(c))){ 
				return contacts.add(c);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public static boolean handleLogoutMessage(LogoutMessage message) {
		for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
			Contact c = it.next();
		    if (message.getUserID().equals(c.getUserID())) {
		        it.remove();
		    }
		}
		return false;
	}
	
	public static boolean handleStringMessage(StringMessage message) {
		if (message.getTargetID() == Message.ALL) {
			user.addMessage(message);
			return true;
		} else {
			for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
				Contact c = it.next();
				if (c.getUserID() == message.getUserID()) {
					c.addMessage(message);
					return true;
				}
			}
		}
		return false;
	}
	
	public static String BitMapToString(Bitmap bitmap){
//	     ByteArrayOutputStream baos=new  ByteArrayOutputStream();
//	     bitmap.compress(CompressFormat.PNG,0, baos);
//	     byte [] b=baos.toByteArray();
		ByteBuffer byteBuf =ByteBuffer.allocate(bitmap.getByteCount());
		bitmap.copyPixelsToBuffer(byteBuf);
		byte[] b =byteBuf.array();
		return new String(Base64Coder.encode(b));
	}
	

	 public static Bitmap getImageBitMap(String avatar){
		 byte[] decodedString = Base64Coder.decode(avatar);
		 BitmapFactory.Options options = new BitmapFactory.Options(); 
		 options.inSampleSize = 8; 
		  return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options);
	 }
	 
	 public static Bitmap getImageBitMap(String avatar,int height,int width){
		 byte[] decodedString = Base64Coder.decode(avatar);
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
