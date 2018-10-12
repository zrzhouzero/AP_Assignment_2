package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDatabase {

	public static void main(String[] args) {

		final String DB_NAME = "FlexiRentSys";

		//use try-with-resources Statement
		try (Connection con = getConnection()) {
			System.out.println("Connection to database " + DB_NAME + " created successfully");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	static Connection getConnection()
			throws SQLException, ClassNotFoundException {
		//Registering the HSQLDB JDBC driver
		Class.forName("org.hsqldb.jdbc.JDBCDriver");

		/* Database files will be created in the "database"
		 * folder in the project. If no username or password is
		 * specified, the default SA user and an empty password are used */

		return DriverManager.getConnection("jdbc:hsqldb:file:database/" + "FlexiRentSys", "SA", "");

	}

}
