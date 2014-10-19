package com.publicCaptivation.stream2me.client.gui.utils;

import com.publicCaptivation.stream2me.client.ClientHandler;

import messages.StringMessage;

public class ChatMessages {

	 private String userID;
	 private String targetID;
	 private String message;
	 private String timestamp;
	 private boolean isMine;
	 
	 public ChatMessages(StringMessage message, boolean owner) {
		 int i =message.getTimestamp().lastIndexOf(".");
		 if (i != -1) {
			 this.timestamp = message.getTimestamp().substring(0, i);
		 } else {
			 this.timestamp = message.getTimestamp();
		 }
		 this.message = message.getMessage();
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
