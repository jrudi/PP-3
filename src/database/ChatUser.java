package database;

import java.io.*;

public class ChatUser implements Serializable, Comparable<ChatUser> {

	private static final long serialVersionUID = 1L;
	
	private String username;

	public ChatUser(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String toString() {
		return username;
	}
	
	@Override 
	public boolean equals(Object object) {
		ChatUser other = (ChatUser) object;
		
		return this.username.equals(other.username);
	}

	@Override
	public int compareTo(ChatUser other) {
		return this.username.compareTo(other.username);
	}
	
}
