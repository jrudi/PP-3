package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import general.Parameters;

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

			// ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM users
			// WHERE time = '" +message.getTime()+ "'" );
			// if(rs.getString(1).equals("0")){
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
	
	public boolean deleteUsers(){
		try {
			Statement statement = this.connection.createStatement();
			return statement.executeUpdate("DELETE FROM users")==0;
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

	public ChatHistory getChatHistoryWithUser(ChatUser userfrom, ChatUser otheruser) {
		try {
			Statement statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM messages WHERE (userfrom = '" + userfrom.getUsername()
					+ "' AND userto = '" + otheruser.getUsername() + "') OR (userfrom = '" + otheruser.getUsername()
					+ "' AND userto = '" + userfrom.getUsername() + "')");
			List<ChatMessage> list = new ArrayList<ChatMessage>();
			while (rs.next()) {
				list.add(new ChatMessage(new ChatUser(rs.getString("userfrom")), new ChatUser(rs.getString("userto")),
						rs.getString("message"), rs.getLong("time")));

			}
			return (new ChatHistory(otheruser, list));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override

	public List<ChatHistory> selectChatHistory(ChatUser userfrom) {
		List<ChatUser> userList = new ArrayList<ChatUser>();
		List<ChatHistory> historyList = new ArrayList<ChatHistory>();

			for (ChatUser user : userList) {
				ChatHistory cH = this.getChatHistoryWithUser(userfrom, user);
				if (cH.getMessages().size() > 0) {
					historyList.add(cH);
				}
			}

			return historyList;
		
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

	@Override
	public List<ChatHistory> selectUnreadChatHistory(ChatUser userto) {
		return null;
	}

	public static void main(String[] args) {
		ChatUser cu1 = new ChatUser("sender");
		ChatUser cu2 = new ChatUser("receiver");
		String message = "HALLO DAS IST EIN TEST";
		long time = System.currentTimeMillis();
		ChatMessage cm = new ChatMessage(cu1, cu2, message, time);
		DatabaseHANDLE dh = new DatabaseHANDLE(Parameters.DATABASE);
		dh.insertChatMessage(cm);
		List<ChatUser> list = dh.selectChatUsers();
		for (ChatUser x : list) {
			System.out.println(x.getUsername());
		}
		
		dh.deleteUsers();
		list = dh.selectChatUsers();

		for (ChatUser y : list) {
			System.out.println(y.getUsername());

		}
	}
}
