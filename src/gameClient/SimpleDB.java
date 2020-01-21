package gameClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Server.Game_Server;
import Server.game_service;

/**
 * This class represents a simple example of using MySQL Data-Base. Use this
 * example for writing solution.
 * 
 * @author boaz.benmoshe
 *
 */
public class SimpleDB {
	public static final String jdbcUrl = "jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
	public static final String jdbcUser = "student";
	public static final String jdbcUserPassword = "OOP2020student";

	/**
	 * Simple main for demonstrating the use of the Data-base
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// int scenario_num = 0; // current "stage is 9, can play[0,9], can NOT 10 or
		// above
		// int id = 999;
		Game_Server.login(206087702);
		game_service game = Game_Server.getServer(0); // you have [0,23] games
		
		System.out.println(game.toString());
		// int id1 = 999; // "dummy existing ID
		// int level = 0;
		// allUsers(); //return the num of users
		printLog();
		String kml = getKML(206087702, 0);
		System.out.println("***** KML file example: ******");
		game.sendKML(kml);
		System.out.println("" + kml);
	}

	/**
	 * simply prints all the games as played by the users (in the database).
	 * 
	 */
	//public static void getInfo();
	public static void printLog() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			while (resultSet.next()) {
				System.out.println("Id: " + resultSet.getInt("UserID") + "," + resultSet.getInt("levelID") + ","
						+ resultSet.getInt("moves") + "," + resultSet.getDate("time"));
			}
			resultSet.close();
			statement.close();
			connection.close();
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * this function returns the KML string as stored in the database (userID,
	 * level);
	 * 
	 * @param id
	 * @param level
	 * @return
	 */
	public static String getKML(int id, int level) {

		String ans = null;
		String allCustomersQuery = "SELECT * FROM Users where userID=" + id + ";";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);
			if (resultSet != null && resultSet.next()) {
				ans = resultSet.getString("kml_" + level);
			}
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ans;
	}

	public static int allUsers() {
		int ans = 0;
		String allCustomersQuery = "SELECT * FROM Users;";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword); // enter to the
																										// database
			Statement statement = connection.createStatement(); // create connection
			ResultSet resultSet = statement.executeQuery(allCustomersQuery); // read an id by sending UserID
			while (resultSet.next()) { // read all user and print them
				System.out.println("Id: " + resultSet.getInt("UserID"));
				ans++; // numOfUsers
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ans;
	}
}
