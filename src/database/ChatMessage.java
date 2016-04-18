package database;

import java.io.*;

public class ChatMessage implements Serializable, Comparable<ChatMessage> {

	private static final long serialVersionUID = 1L;
	
	private ChatUser userfrom;
	private ChatUser userto;
	private String message;
	private long time;
	
	public ChatMessage(ChatUser userfrom, ChatUser userto, String message, long time) {
		this.userfrom = userfrom;
		this.userto = userto;
		this.message = message;
		this.time = time;
	}

	public ChatUser getFrom() {
		return this.userfrom;
	}

	public ChatUser getTo() {
		return this.userto;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public long getTime() {
		return this.time;
	}
	
	@Override
	public String toString() {
		return this.userfrom + " -> " + this.userto + ": " + this.message;
	}

	@Override
	public int compareTo(ChatMessage other) {
		// message with higher time stamp goes to the front
		return (int) (other.time - this.time);
	}
	
}
