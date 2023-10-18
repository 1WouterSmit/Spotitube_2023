package DataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    // Connect to your database.

	private static DB db;
	private static Connection con;

	public static DB getInstance() {
		if( db == null ) db = new DB();
		return db;
	}

	private static void connect() throws IOException, ClassNotFoundException, SQLException {
		Properties properties = new Properties();
		properties.load(DB.class.getClassLoader().getResourceAsStream("databaseMysql.properties"));

		Class.forName(properties.getProperty("driver"));
		con = DriverManager.getConnection(properties.getProperty("db_url")+"?user="+
				properties.getProperty("user") + "&password=" +
				properties.getProperty("pw"));
	}

	public Connection connection() throws SQLException {
		try {
			if(con == null) connect();
			return con;
		} catch (SQLException | ClassNotFoundException | IOException e) {
			throw new SQLException(e);
		}
	}

}