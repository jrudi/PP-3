package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import general.Parameters;

public class DatabaseHANDLE extends ADatabaseHANDLE{

	public DatabaseHANDLE(String file) {
		super(file);
	}

	@Override
	public boolean insertChatUser(ChatUser user) {
		Statement statement;
		try {
			statement = this.connection.createStatement();

			//ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM users WHERE username = '" +user.getUsername()+ "'" );
			//rs.getString(1).equals("0")){// getString("username"));
			if(existsChatUser(user)){
				statement.executeUpdate("INSERT INTO users(username) VALUES ('"+ user.getUsername() + "')");
				System.out.println("User " +user.getUsername() +" wurde in die Datenbank aufgenommen!");

				return true;
			}else{
				System.out.println("User " +user.getUsername() +" ist schon in der Datenbank!");
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

			ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM users WHERE time = '" +message.getTime()+ "'" );
			if(rs.getString(1).equals("0")){// getString("username"));
				statement.executeUpdate("INSERT INTO  messages(userfrom, userto, message, time, unread) VALUES ('" 
						+ message.getFrom() 
						+ "','" + message.getTo() 
						+ "','" + message.getMessage() 
						+ "','" +message.getTime() 
						+ "','" 
						+ "'1')");
				System.out.println("Message " + message.getTime() + " wurde in die Datenbank aufgenommen!");

				return true;
			}else{
				System.out.println("Message " + message.getTime() + " ist schon in der Datenbank!");
				return false;
			}
			

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
		try{
			statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM users WHERE username = '" +user.getUsername()+ "'" );
			return rs.getString(1).equals("0");
			
		}catch(SQLException e){
				e.printStackTrace();
		} return false;
			
	}

	@Override
	public List<ChatUser> selectChatUsers() {
		Statement statement;
		List<ChatUser> list = new ArrayList<ChatUser>();
		try {
			statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM users");
			
			while(rs.next()){
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
		//COPY FROM selectChatUsers()
		
		/*Statement statement;
		List<ChatUser> list = new ArrayList<ChatUser>();
		try {
			statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM messages WHERE userfrom = " + userfrom.getUsername());
			
			while(rs.next()){
				String from = rs.getString("userfrom");
				String to = rs.getString("userto");
				String message = rs.getString("message");
				long time = rs.getLong("time");
				String read = rs.getString("unread");
				
				list.add(new ChatUser(rs.getString("username")));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		return null;
	}

	@Override
	public List<ChatHistory> selectUnreadChatHistory(ChatUser userto) {
		return null;
	}

	public static void main(String[] args) {
		ChatUser cu = new ChatUser("jonas1");
		DatabaseHANDLE dh = new DatabaseHANDLE(Parameters.DATABASE);
		dh.insertChatUser(cu);
	}
	
}
