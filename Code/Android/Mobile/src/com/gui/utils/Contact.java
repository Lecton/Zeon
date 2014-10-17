package com.gui.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mobile.ClientHandler;

import biz.source_code.base64Coder.Base64Coder;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import messages.StringMessage;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.Image;
import android.util.Base64;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;

public class Contact implements Serializable {
	
	 private static final long serialVersionUID = 1L;
	 private String image;
	 private String name;
	 private String surname;
	 private String email;
	 private String about;
	 private boolean [] notification = {false, // video notificatioon[0]
			 							false, // audio notificatioon[1]
			 							false}; //string notificatioon [2]
	 
	 private String videoStreamID;
	 private String audioStreamID;
	 
	 private String UserID;
	 
	 private ArrayList<StringMessage> messageHistory;
	 
	 public Contact(GreetingMessage message) {
		  this.name = message.getName();
		  this.surname = message.getSurname();
		  this.email = message.getEmail();
		  this.UserID = message.getUserID();
		  this.about = message.getAboutMe();		  
		  image = message.getAvatar();
		  messageHistory =new ArrayList<StringMessage>();
	 }

	 public Contact(NewUserMessage message) {
		  this.name = message.getName();
		  this.surname = message.getSurname();
		  this.email = message.getEmail();
		  this.UserID = message.getUserID();
		  this.about = message.getAboutMe();		  
		  image = message.getAvatar();
		  messageHistory =new ArrayList<StringMessage>();
	 }

	 public Contact(LogoutMessage message) {
		  this.UserID = message.getUserID();
	 }	 
	 
	 public String getAbout(){
		 return about;
	 }
	 
	 public void setAbout(String abt){
		this.about = abt;
	 }
	 
	 public String getUserID() {
		 return UserID;
	 }
	 
	 public Bitmap getImage(){
		 return ClientHandler.getImageBitMap(image);
	 }

	 public String getName() {
		 return name;
	 }

	 public String getSurname() {
		 return surname;
	 }
	 
	 public String getEmail() {
		 return email;
	 }

	 public boolean setVideoNoticationOn(){
		 
		 return (notification[0] = true);
	 }

	 public boolean setAudioNoticationOn(){
		 
		 return (notification[1] = true);
	 }
	 
	 public boolean setStringNoticationOn(){
		 
		 return (notification[2] = true);
	 }

	 public boolean setVideoNoticationOff(){
		 
		 return !(notification[0] = false);
	 }

	 public boolean setAudioNoticationOff(){
		 
		 return !(notification[1] = false);
	 }
	 
	 public boolean setStringNoticationOff(){
		 
		 return !(notification[2] = false);
	 }
	 
	 public boolean getVideoNotification(){
		 return notification[0];
	 }	 
	 
	 public boolean getAudioNotification(){
		 return notification[1];
	 }	 
	 
	 public boolean getStringNotification(){
		 return notification[2];
	 }
	 
	 public void setName(String n) {
		 this.name = n;
	 }

	 public void setSurname(String n) {
		 this.surname = n;
	 }

	 public void setEmail(String e) {
		 this.email = e;
	 }

	 public void setImage(String si){
		 this.image =si;
	 }
	 
	 public void setUserID(String userID) {
		 UserID = userID;
	 }
	 
	 public void addMessage(StringMessage message) {
		 messageHistory.add(message);
	 }
	 
	 public String getVideoStreamID() {
		return videoStreamID;
	 }
	 
	 public void setVideoStreamID(String videoStreamID) {
		this.videoStreamID = videoStreamID;
	 }
	 
	 public String getAudioStreamID() {
		return audioStreamID;
	 }
	 
	 public void setAudioStreamID(String audioStreamID) {
		this.audioStreamID = audioStreamID;
	 }
	 
	 public List<ChatMessages> getMessageHistory() {
		 List<ChatMessages> messageList = new ArrayList<>();
//		 Log.v("User name: " + this.name,"" + messageHistory.size());
		 for (StringMessage message: messageHistory) {
//			 Logger.getLogger(Contact.class.getName()).log(Level.INFO, message.getMessage()+" --> "+message.getUserID()+" --> "+getUserID());
			 messageList.add(new ChatMessages(message, (message.getUserID().equals(ClientHandler.getUser().getUserID()))));
		 }
		 return messageList;
	 }
	 
//	 public List<ChatMessages> getMessageHistory(boolean value) {
//		 List<ChatMessages> messageList = new ArrayList<>();
//		 Log.v("User name: " + this.name,"" + messageHistory.size());
//		 for (StringMessage message: messageHistory) {
////			 Logger.getLogger(Contact.class.getName()).log(Level.INFO, message.getMessage()+" --> "+message.getUserID()+" --> "+getUserID());
//			 messageList.add(new ChatMessages(message, value));
//		 }
//		 return messageList;
//	 }
	 		
	}