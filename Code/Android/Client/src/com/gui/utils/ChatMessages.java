package com.gui.utils;

import java.sql.Timestamp;

public class ChatMessages {

	 private String name;
	 private String message;
	 private long timestamp;
	 private int userID;
	 
	 public ChatMessages(int d,String n,String m, long t){
		 this.name = n;
		 this.message = m;
		 this.timestamp = t;
		 this.userID = d;
	 }
	 
	 public void setUserID(int userID) {
		this.userID = userID;
	}
	 public void setName(String name) {
		this.name = name;
	}
	 
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getUserID() {
		return userID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getTimestamp() {
		return (new Timestamp(timestamp) + "").split(" ")[1];
	}
}
