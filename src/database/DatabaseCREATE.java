package database;

import java.sql.*;

import general.*;

public class DatabaseCREATE extends ADatabase {
	
	public DatabaseCREATE(String file) {
		super(file);
	}
	
	public boolean check() {		
		boolean users = false;
		boolean messages = false;
		
		try {
			Statement statement = this.connection.createStatement();
			
			ResultSet rs = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table'");
			while(rs.next()) {
				if(rs.getString(1).equals("users")) {
					users = true;
				}
				
				if(rs.getString(1).equals("messages")) {
					messages = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (users && messages);
	}
	
	public void create() {
		if(!this.check()) {
			/* YOUR IMPLEMENTATION */
		}
	}
	
	public void fill() {
		if(this.check()) {
			try {
				Statement statement = this.connection.createStatement(); 
				
				// users
				statement.executeUpdate("INSERT INTO users(username) VALUES ('user_one')");
				statement.executeUpdate("INSERT INTO users(username) VALUES ('user_two')");
				statement.executeUpdate("INSERT INTO users(username) VALUES ('user_three')");
				
				// messages
				statement.executeUpdate("INSERT INTO messages(userfrom, userto, message, time, unread) VALUES ('user_one', 'user_two', 'hallo user_two', " + System.currentTimeMillis() + ", 1)");
				statement.executeUpdate("INSERT INTO messages(userfrom, userto, message, time, unread) VALUES ('user_one', 'user_two', 'user_two, sag mal hallo!', " + System.currentTimeMillis() + ", 1)");
				statement.executeUpdate("INSERT INTO messages(userfrom, userto, message, time, unread) VALUES ('user_two', 'user_one', 'hallo user_one', " + System.currentTimeMillis() + ", 1)");
				statement.executeUpdate("INSERT INTO messages(userfrom, userto, message, time, unread) VALUES ('user_one', 'user_three', 'user_three, user_three, user_three!', " + System.currentTimeMillis() + ", 1)");
				statement.executeUpdate("INSERT INTO messages(userfrom, userto, message, time, unread) VALUES ('user_three', 'user_one', 'richtig user_one', " + System.currentTimeMillis() + ", 1)");
				statement.executeUpdate("INSERT INTO messages(userfrom, userto, message, time, unread) VALUES ('user_one', 'user_one', 'ich kann an mich selbst schreiben', " + System.currentTimeMillis() + ", 1)");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		DatabaseCREATE database = new DatabaseCREATE(Parameters.DATABASE);
		boolean examples = false;

		System.out.println("check is " + database.check());
		
		if(!database.check()) {
			database.create();
			System.out.println("create done");
			
			if(examples) {
				database.fill();
				System.out.println("fill done");
			}
		}
	}
	
}
