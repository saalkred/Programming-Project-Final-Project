package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Array;
import java.util.ArrayList;

public class PostgresPending {
	private static Connection c = null;
	
	/**
	 * Main method establishes connection to database for any calls within it
	 */
	public PostgresPending(){
		try { // establishes database connection for static Connection "c"
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/usersdb",
					"postgres", "1234");
			System.out.println("Connection to database successful.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		if (!tableExists()) { // checks for existence of "Pending" table
			System.out.println("Pending table creation required");
			createPendingTable(); // create "Pending" table if not present
		} else {
			System.out.println("Pending table present");
		}
	}
	
	/**
	 * 
	 */
	public static boolean tableExists(){
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT to_regclass('Pending');");
			rs.next();
			if (rs.getString("to_regclass") != null) {
				rs.close();
				stmt.close();
				return true;
			} else {
				rs.close();
				stmt.close();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return false;
		}
	}
	
	/**
	 * Creates Pending table in database with standard headers
	 * Not needed if this method has been previously called AND the table has not been dropped
	 */
	public static void createPendingTable() {
		try {
			Statement stmt = null;
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE Pending (" + 
			"USERNAME TEXT PRIMARY KEY NOT NULL," + // username associated with this order
			"STATUS INT NOT NULL," + // status of order; 1 = shopping cart, 2 = pending, 3 = confirmed
			"CONTENTS TEXT[][] NOT NULL)"; // items in order; [Title][Quantity] quantity is integer stored as String
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Pending table has been created.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * Adds a pending order for the given username
	 * @param username
	 * @param status
	 * @param contents Items only need title and stock (quantity in order)
	 */
	public static void addPending(String username, int status, ArrayList<Item> contents) {
		try {
			c.setAutoCommit(false);
			String sql = "INSERT INTO Pending (" +
					"USERNAME,STATUS,CONTENTS) " +
					"VALUES(?,?,?::text[]);";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setInt(2, status);
			String contentsString = "{";
			for (int i = 0; i < contents.size(); i++) {
				contentsString += "{";
				contentsString += "\"" + contents.get(i).getTitle() + "\", " + Integer.toString(contents.get(i).getStock());
				if (i < contents.size()-1) {
					contentsString += "}, ";
				} else {
					contentsString += "}";
				}
				
			}
			contentsString += "}";
			stmt.setString(3, contentsString);
			
			stmt.executeUpdate();
			stmt.close();
			c.commit();
			System.out.println("Pending order added to database");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	public static ArrayList<Item> getOrder(String username) {
		Statement stmt = null;
		ArrayList<Item> result = new ArrayList<Item>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Contents FROM Pending WHERE Username = '" + username + "';");
			rs.next();
			Array a = rs.getArray("Contents");
			String[][] tempCart = (String[][])a.getArray();
			for (int i = 0; i < tempCart.length; i++) {
				result.add(new Item(tempCart[i][0], Integer.parseInt(tempCart[i][1])));
			}
			rs.close();
			stmt.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return result;
		}
	}
	
	public static int getOrderStatus(String username) {
		Statement stmt = null;
		int result = 0;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Status FROM Pending WHERE Username = '" + username + "';");
			
			if (rs.isBeforeFirst()) { // returns true if data present in ResultSet, if not (ie no item with title) returns false
				rs.next();
				result = rs.getInt("Status");
			}
			rs.close();
			stmt.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return result;
		}
	}
	
	public static ArrayList<String> getPendingList() {
		Statement stmt = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Username FROM Pending;");
			
			while (rs.next()) {
				result.add(rs.getString("Username"));
			}
			rs.close();
			stmt.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return result;
		}
	}
	
	public static boolean orderExists(String username) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Pending WHERE Username = '" + username + "';");
			
			if (rs.isBeforeFirst()) { // returns true if data present in ResultSet, if not (ie no account at username) returns false
				rs.close();
				stmt.close();
				return true;
			}
			rs.close();
			stmt.close();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return false;
		}
	}

	
	public static void deleteOrder (String username) {
		if (orderExists(username)) {
			Statement stmt = null;
			try {
				c.setAutoCommit(false);
				stmt = c.createStatement();
				String sql = "DELETE FROM Pending WHERE Username = '" + username + "';";
				stmt.executeUpdate(sql);
				stmt.close();
				c.commit();
				System.out.println("Order deleted");
			
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		} else {
			System.out.println("No such order for that username");
		}
	}
}
