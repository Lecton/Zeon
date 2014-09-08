package com.mobile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import messages.Message;
import messages.StringMessage;
import messages.userConnection.LogoutMessage;
import messages.userConnection.NewUserMessage;
import android.util.Log;

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
	
	public static Contact getFromUserID(int userID) {
		for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
			Contact c = it.next();
			if (c.getUserID() == userID) {
				return c;
			}
		}
		return null;
	}
	
	public static boolean updateContact(Contact client) {
		for (Iterator<Contact> it = contacts.iterator(); it.hasNext(); ) {
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
		    if (message.getUserID() == c.getUserID()) {
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
}
