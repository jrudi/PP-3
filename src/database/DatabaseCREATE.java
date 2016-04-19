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
			try {
				Statement statement = this.connection.createStatement();
				statement.executeUpdate("CREATE TABLE users (username TEXT NOT NULL PRIMARY KEY)");
				statement.executeUpdate("CREATE TABLE messages("
						+ "id INTEGER PRIMARY KEY, "
						+ "userfrom TEXT NOT NULL, "
						+ "userto TEXT NOT NULL, "
						+ "message TEXT NOT NULL, "
						+ "time INTEGER NOT NULL, "
						+ "unread INTEGER NOT NULL, "
						+ "FOREIGN KEY(userfrom) REFERENCES users(username), "
						+ "FOREIGN KEY(userto) REFERENCES users(username) "
						+ ")");
			
			
			} catch (SQLException e) {
				e.printStackTrace();
			} 

			
		}
	}
	
	public void fill() {
		System.out.println("hi2");

		if(this.check()) {
			try {
				Statement statement = this.connection.createStatement(); 
				System.out.println("hi");
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
		boolean examples = true;

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
