package database;

import java.io.*;
import java.util.*;

public class ChatHistory implements Serializable, Comparable<ChatHistory> {

	private static final long serialVersionUID = 1L;
	
	private ChatUser other;
	private List<ChatMessage> messages;
	
	public ChatHistory(ChatUser other, List<ChatMessage> messages) {
		this.other = other;
		this.messages = messages;
	}
	
	public ChatUser getOther() {
		return this.other;
	}
	
	public List<ChatMessage> getMessages() {
		return this.messages;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("... MIT ").append(this.other).append("\n");
		
		for(ChatMessage message : this.messages) {
			buffer.append(message).append("\n");
		}
		
		return buffer.toString();
	}

	@Override
	public int compareTo(ChatHistory other) {
		if(this.messages.isEmpty() || other.messages.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return this.messages.get(this.messages.size() - 1).compareTo(other.messages.get(other.messages.size() - 1));
		}
	}
	
}
