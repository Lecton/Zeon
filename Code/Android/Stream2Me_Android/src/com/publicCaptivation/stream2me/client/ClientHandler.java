package com.publicCaptivation.stream2me.client;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.publicCaptivation.stream2me.client.gui.MainWindow;
import com.publicCaptivation.stream2me.client.gui.utils.Contact;
import com.publicCaptivation.stream2me.client.gui.utils.User;

import messages.Message;
import messages.StringMessage;
import messages.media.communication.StreamNotifyMessage;
import messages.update.UpdateAvatarMessage;
import messages.update.UpdateProfileMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import biz.source_code.base64Coder.Base64Coder;

public class ClientHandler {
	private static Bitmap BLANK;
	
	static {
		BLANK =getImageBitMap("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");
		if (BLANK == null) {
			BLANK =Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
		}
	}
	
	private static List<Contact> contacts =new ArrayList<Contact>();
	private static User user;
	
	public ClientHandler() throws Exception {
		throw new Exception("Do not create ME!!!!!!");
	}
	
	protected static boolean setUser(User loggedUser) {
		if (user == null && loggedUser != null) {
			user =loggedUser;
			for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
				Contact c = it.next();
				if (c.getUserID().equals(user.getUserID())) {
					contacts.remove(c);
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static User getUser() {
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
			if (c.getUserID().equals(client.getUserID())) {
				c = client;
				return true;
			}
		}
		return false;
	}
	
	public static boolean updateUserAvatar(UpdateAvatarMessage msg){
		for(Iterator<Contact> it = contacts.iterator();it.hasNext();){
			Contact c = it.next();
			if(c.getUserID().equals(msg.getUserID())){
				c.setImage(msg.getAvatar());
				Log.v("ClientHandler","Client found to update.");
				return true;
			}
		}
		return false;
	}
		
	public static boolean handleNewUserMessage(NewUserMessage message) {
		Contact c =new Contact(message);
		if (user != null && !(c.getUserID().equals(user.getUserID()))) {
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
		if (message.getTargetID().equals(Message.ALL)) {
			user.addMessage(message);
			return true;
		} else {
			Log.v("ClientHandler","Client size: " + contacts.size());
			for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
				Contact c = it.next();
				if (c.getUserID().equals(message.getUserID())) {
					c.addMessage(message);
					c.setStringNoticationOn();
					Log.v("ClientHandler","Name: " + c.getName());
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
		 ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		 bitmap.compress(Bitmap.CompressFormat.JPEG,10, baos);
		 byte [] b=baos.toByteArray();
		 String temp=Base64.encodeToString(b, Base64.DEFAULT);
		 return temp;
	}
	
	public static Bitmap getImageBitMap(String avatar){
		byte[] decodedString = Base64Coder.decode(avatar);
		BitmapFactory.Options options = new BitmapFactory.Options(); 
		options.inSampleSize = 0;
		return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length, options);
	 }
	
	 
//	 public static Bitmap getImageBitMap(String avatar,int height,int width){
//		 byte[] decodedString = Base64Coder.decode(avatar);
//		  return getResizedBitmap(
//				  	BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length),height,width);
//	 }
	 
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

	public static Bitmap getBLANK() {
		return BLANK;
	}

	public static boolean updateUserProfile(UpdateProfileMessage msg) {
		for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
			Contact c = it.next();
			if (c.getUserID().equals(msg.getUserID())) {
				c.setName(msg.getName());
				c.setSurname(msg.getSurname());
				c.setTitle(msg.getTitle());
				c.setAbout(msg.getAboutMe());
				c.setEmail(msg.getEmail());
				return true;
			}
		}
		return false;
	}
}
