package DataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
	private static DB dbCon;
	private Connection con;

    public DB() {
		try {
			Properties properties = new Properties();
			properties.load(DB.class.getClassLoader().getResourceAsStream("database.properties"));

			con = DriverManager.getConnection(properties.getProperty("db_url"),
					properties.getProperty("user"),
					properties.getProperty("pw"));
		} catch(SQLException | IOException e) {
			e.printStackTrace();
		}
	}


	public static Connection connection() {
		if(dbCon == null) dbCon = new DB();
		return dbCon.getConnection();
	}

	private Connection getConnection() {
		return con;
	}
}