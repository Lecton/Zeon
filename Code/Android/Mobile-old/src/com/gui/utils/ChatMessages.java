package com.gui.utils;

import android.util.Log;

import com.mobile.ClientHandler;

import messages.StringMessage;

public class ChatMessages {

	 private String userID;
	 private String targetID;
	 private String name;
	 private String message;
	 private String timestamp;
	 private boolean isMine;
	 
	 public ChatMessages(StringMessage message, boolean owner){
		 
		 if (owner) {
			 this.name = ClientHandler.getUser().getName();
		 } else {
			 this.name = ClientHandler.getFromUserID(message.getUserID()).getName();
		 }
		 this.message = message.getMessage();
		 this.timestamp = message.getTimestamp();
		 this.userID = message.getUserID();
		 this.targetID = message.getTargetID();
		 isMine = owner;
	 }
	 
	public void setIsMine(boolean m){
		isMine = m;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
		
	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}
	
	 public void setName(String name) {
		this.name = name;
	}
	 
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isMine(){
		return isMine;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public String getTargetID() {
		return targetID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
//	public String getTimestamp() {
//		return (new Timestamp(timestamp) + "").split(" ")[1];
//	}
}
