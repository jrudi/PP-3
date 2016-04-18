package database;

import java.sql.*;

public abstract class ADatabase {

	protected Connection connection;
	
	public ADatabase(String file) {
		this.connect(file);
	}
	
	private void connect(String file) {
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
