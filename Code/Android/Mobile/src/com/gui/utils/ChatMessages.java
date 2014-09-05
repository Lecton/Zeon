package com.gui.utils;

import java.sql.Timestamp;

import messages.StringMessage;

public class ChatMessages {

	 private int userID;
	 private int targetID;
	 private String name;
	 private String message;
	 private String timestamp;
	 private boolean isMine;
	 
	 public ChatMessages(StringMessage message, boolean owner){
		 this.name = message.getSender();
		 this.message = message.getMessage();
		 this.timestamp = message.getTimestamp();
		 this.userID = message.getUserID();
		 this.targetID = message.getTargetID();
		 isMine = owner;
	 }
	 
	public void setIsMine(boolean m){
		isMine = m;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
		
	public void setTargetID(int targetID) {
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
	
	public int getUserID() {
		return userID;
	}
	
	public int getTargetID() {
		return targetID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return message;
	}
	
//	public String getTimestamp() {
//		return (new Timestamp(timestamp) + "").split(" ")[1];
//	}
}
