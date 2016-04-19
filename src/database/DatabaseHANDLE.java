package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHANDLE extends ADatabaseHANDLE {

	public DatabaseHANDLE(String file) {
		super(file);
	}

	@Override
	public boolean insertChatUser(ChatUser user) {
		Statement statement;
		try {
			statement = this.connection.createStatement();

			if (!existsChatUser(user)) {
				statement.executeUpdate("INSERT INTO users(username) VALUES ('" + user.getUsername() + "')");
				System.out.println("User " + user.getUsername() + " wurde in die Datenbank aufgenommen!");

				return true;
			} else {
				System.out.println("User " + user.getUsername() + " ist schon in der Datenbank!");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean insertChatMessage(ChatMessage message) {
		Statement statement;
		try {
			statement = this.connection.createStatement();

			if(!this.existsChatUser(message.getFrom())){
				this.insertChatUser(message.getFrom());
			}
			if(!this.existsChatUser(message.getTo())){
				this.insertChatUser(message.getTo());
			}
			statement.executeUpdate("INSERT INTO  messages(userfrom, userto, message, time, unread) VALUES ('"
					+ message.getFrom() + "','" + message.getTo() + "','" + message.getMessage() + "','"
					+ message.getTime() + "'," + "1)");
			System.out.println("Message " + message.getTime() + " wurde in die Datenbank aufgenommen!");

			return true;
			/*
			 * }else{ System.out.println("Message " + message.getTime() +
			 * " ist schon in der Datenbank!"); return false; }
			 */

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public boolean updateUnreadChatMessages(ChatUser userto) {
		if (existsChatUser(userto)) {

			try {
				Statement statement = this.connection.createStatement();
				statement.executeUpdate(
						"UPDATE messages SET unread = 0 WHERE (userto = '" + userto.getUsername() + "' AND unread = 1)");

				return true;

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return false;

	}

	public boolean deleteChatUser(ChatUser user) {
		if (this.existsChatUser(user)) {
			try {
				Statement statement = this.connection.createStatement();
				statement.executeUpdate("DELETE * FROM users WHERE username = '" + user.getUsername() + "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;

		} else
			return false;
	}
	
	public boolean deleteUsers() {
		try {
			Statement statement = this.connection.createStatement();
			return statement.executeUpdate("DELETE FROM users") == 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteMessages() {
		try {
			Statement statement = this.connection.createStatement();
			return statement.executeUpdate("DELETE FROM messages") == 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

	@Override
	public boolean existsChatUser(ChatUser user) {
		Statement statement;
		try {
			statement = this.connection.createStatement();
			ResultSet rs = statement
					.executeQuery("SELECT COUNT(*) FROM users WHERE username = '" + user.getUsername() + "'");
			return !rs.getString(1).equals("0");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public List<ChatUser> selectChatUsers() {
		Statement statement;
		List<ChatUser> list = new ArrayList<ChatUser>();
		try {
			statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM users");

			while (rs.next()) {
				list.add(new ChatUser(rs.getString("username")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override

	public List<ChatHistory> selectChatHistory(ChatUser userfrom) {
		List<ChatHistory> historyList = new ArrayList<ChatHistory>();

		for (ChatUser user : this.selectChatUsers()) {
			List<ChatMessage> list = new ArrayList<ChatMessage>();
			try {
				Statement statement = this.connection.createStatement();
				ResultSet rs = statement.executeQuery("SELECT * FROM messages WHERE (userfrom = '" + userfrom.getUsername()
						+ "' AND userto = '" + user.getUsername() + "') OR (userfrom = '" + user.getUsername()
						+ "' AND userto = '" + userfrom.getUsername() + "')");
				while (rs.next()) {
					list.add(new ChatMessage(new ChatUser(rs.getString("userfrom")), new ChatUser(rs.getString("userto")),
							rs.getString("message"), rs.getLong("time")));

				}
				ChatHistory ch = new ChatHistory(user, list);
				if(ch.getMessages().size()>0){
					historyList.add(ch);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}

		return historyList;

	}

	@Override
	public List<ChatHistory> selectUnreadChatHistory(ChatUser userto) {
		List<ChatHistory> historyList = new ArrayList<ChatHistory>();
		List<ChatUser> userList = this.selectChatUsers();
		for (ChatUser otherUser : userList) {
			List<ChatMessage> messages = new ArrayList<ChatMessage>();

			if (this.existsChatUser(userto)) {
				try {
					Statement statement = this.connection.createStatement();
					ResultSet rs = statement.executeQuery(
							"SELECT * FROM messages WHERE userfrom = '" + otherUser.getUsername() + "' AND userto = '" + userto.getUsername() + "' AND unread = 1");
					
					while (rs.next()) {
						ChatMessage cm = new ChatMessage(new ChatUser(rs.getString("userfrom")),
								new ChatUser(rs.getString("userto")), rs.getString("message"),
								System.currentTimeMillis());
						messages.add(cm);
					}if(messages.size()>0){
						historyList.add(new ChatHistory(otherUser, messages));
					}
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return historyList;
	}

}
