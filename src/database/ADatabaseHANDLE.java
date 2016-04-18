package database;

import java.util.List;

public abstract class ADatabaseHANDLE extends ADatabase {
	
	public ADatabaseHANDLE(String file) {
		super(file);
	}

	public abstract boolean insertChatUser(ChatUser user);
	
	public abstract boolean insertChatMessage(ChatMessage message);
	
	public abstract boolean updateUnreadChatMessages(ChatUser userto);
	
	public abstract boolean existsChatUser(ChatUser user);
	
	public abstract List<ChatUser> selectChatUsers();
	
	public abstract List<ChatHistory> selectChatHistory(ChatUser userfrom);
	
	public abstract List<ChatHistory> selectUnreadChatHistory(ChatUser userto);
	
}
