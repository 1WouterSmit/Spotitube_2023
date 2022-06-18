package DataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
	private static DB dbCon;
	private Connection con;

    private DB() throws IOException, ClassNotFoundException, SQLException {
		Properties properties = new Properties();
		properties.load(DB.class.getClassLoader().getResourceAsStream("databaseMysql.properties"));

		Class.forName(properties.getProperty("driver"));
		con = DriverManager.getConnection(properties.getProperty("db_url")+"?user="+
				properties.getProperty("user") + "&password=" +
				properties.getProperty("pw"));

	}


	public static Connection connection() throws SQLException {
		try {
			if(dbCon == null) dbCon = new DB();
			return dbCon.getConnection();
		} catch (SQLException | ClassNotFoundException | IOException e) {
			throw new SQLException(e);
		}
	}

	private Connection getConnection() {
		return con;
	}
}