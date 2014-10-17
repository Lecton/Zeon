package com.gui.utils;

import java.io.Serializable;
import java.util.ArrayList;

import messages.StringMessage;
import messages.userConnection.GreetingMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;

public class User extends Contact {
	private static final long serialVersionUID = 6796431636059074995L;

	private String vidStreamID = null;
	private String vidStreamName = null;
	
	private String audStreamID = null;
	private String audStreamName = null;

	public User(GreetingMessage message) {
		super(message);
	}
	
	public User(LogoutMessage message) {
		super(message);
	}

	public User(NewUserMessage message) {
		super(message);
	}
	
	public void setVideoStreamingID(String streamID) {
		this.vidStreamID = streamID;
	}	 
 
	public void setVideoStreamingName(String streamName) {
		this.vidStreamName = streamName;
	}	 

	public String getVideoStreamingID(){
		return this.vidStreamID;
	}

	public String getVideoStreamingName(){
		return this.vidStreamName;
	}
 
	public boolean isStreamingVideo(){
		return this.vidStreamID != null;
	}

	
	public void setAudioStreamingID(String streamID) {
		this.audStreamID = streamID;
	}	 
 
	public void setAudioStreamingName(String streamName) {
		this.audStreamName = streamName;
	}	 

	public String getAudioStreamingID(){
		return this.audStreamID;
	}

	public String getAudioStreamingName(){
		return this.audStreamName;
	}
 
	public boolean isStreamingAudio(){
		return this.audStreamID != null;
	}
}