package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class PostgresInventory {
	private static Connection c = null;
	
	/**
	 * Main method establishes connection to database for any calls within it
	 */
	public PostgresInventory(){
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
		
		if (!tableExists()) { // checks for existence of "Inventory" table
			System.out.println("Inventory table creation required");
			createInventoryTable(); // create "Inventory" table if not present
		} else {
			System.out.println("Inventory table present");
		}
	}
	
	/**
	 * 
	 */
	public static boolean tableExists(){
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT to_regclass('Inventory');");
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
	 * Creates Inventory table in database with standard headers
	 * Not needed if this method has been previously called AND the table has not been dropped
	 */
	public static void createInventoryTable() {
		try {
			Statement stmt = null;
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE Inventory (" + 
			"TITLE TEXT PRIMARY KEY NOT NULL," + // name of item
			"PRICE REAL NOT NULL," + // float equivalent, price of item
			"DESCRIPTION TEXT NOT NULL," + // long text description of item
			"STOCK INT NOT NULL)"; // number of items in stock
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Inventory table has been created.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
		
	/**
	 * Adds item to Inventory via a PreparedStatement
	 * 
	 * @param newItem item with title, price, description, stock
	 */
	public static void addItem(Item newItem) {
		try {
			c.setAutoCommit(false);
			String sql = "INSERT INTO Inventory (" +
					"TITLE,PRICE,DESCRIPTION,STOCK) " +
					"VALUES(?,?,?,?);";
			PreparedStatement stmt = c.prepareStatement(sql);
			stmt.setString(1, newItem.getTitle());
			stmt.setFloat(2, newItem.getPrice());
			stmt.setString(3, newItem.getDescription());
			stmt.setInt(4, newItem.getStock());
			
			stmt.executeUpdate();
			stmt.close();
			c.commit();
			System.out.println("New item added to table");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param title item's title as in database
	 * @return an item with that title
	 */
	public static Item getItem(String title) {
		Statement stmt = null;
		Item result = new Item();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Inventory WHERE Title = '" + title + "';");
			
			if (rs.isBeforeFirst()) { // returns true if data present in ResultSet, if not (ie no item with title) returns false
				rs.next();
				System.out.println("Item found.");
				result = new Item(rs.getString("title"), rs.getFloat("price"), rs.getString("description"),
											 rs.getInt("stock"));
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
	
	/**
	 * 
	 * @return The entire inventory
	 */
	public static ArrayList<Item> getInventory() {
		Statement stmt = null;
		ArrayList<Item> result = new ArrayList<Item>();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Inventory;");
			
			while (rs.next()) {
				result.add(new Item(rs.getString("title"), rs.getFloat("price"), rs.getString("description"), rs.getInt("stock")));
			}
			rs.close();
			stmt.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return null;
		}
	}
	
	/**
	 * 
	 * @param searchQuery A String contained within any item title
	 * @return An ArrayList (of variable length) of type Item containing results, will be empty (not null) if no results
	 */
	public static ArrayList<Item> searchInventory (String searchQuery) {
		Statement stmt = null;
		ArrayList<Item> result = new ArrayList<Item>();
		
		try {
			stmt = c.createStatement();
			String sql = "SELECT * FROM Inventory WHERE LOWER(Title) LIKE LOWER('%" + searchQuery + "%');";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					result.add(new Item(rs.getString("title"), rs.getFloat("price"), rs.getString("description"), rs.getInt("stock")));
				}
			}
			
			stmt.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return null;
		}
	}
	
	/**
	 * 
	 * @param oldTitle
	 * @param newTitle
	 */
	public static void setItemTitle(String oldTitle, String newTitle) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Inventory SET Title = '" + newTitle + "' WHERE Title = '" + oldTitle + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Item " + oldTitle + " title changed to " + newTitle);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param title
	 * @param price
	 */
	public static void setItemPrice(String title, int price) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Inventory SET PRICE = " + price + " WHERE Title = '" + title + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Item " + title + " price changed to " + price);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param title
	 * @param description
	 */
	public static void setItemDescription(String title, String description) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Inventory SET Description = '" + description + "' WHERE Title = '" + title + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Item " + title + " description changed to " + description);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param title
	 * @param stock
	 */
	public static void setItemStock(String title, int stock) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Inventory SET Stock = " + stock + " WHERE Title = '" + title + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Item " + title + " stock updated to " + stock);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param title The title of the item to be deleted
	 */
	public static void deleteItem(String title) {
		if (itemExists(title)) {
			Statement stmt = null;
			try {
				c.setAutoCommit(false);
				stmt = c.createStatement();
				String sql = "DELETE FROM Inventory WHERE Title = '" + title + "';";
				stmt.executeUpdate(sql);
				stmt.close();
				c.commit();
				System.out.println("Item deleted");
			
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		} else {
			System.out.println("No such item with that title");
		}
	}
	
	/**
	 * 
	 * @param title The title of the item
	 * @return True if an item with that title exists, false if one does not
	 */
	public static boolean itemExists(String title) {
		if (getItem(title).getTitle() == null) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 
	 * @return the number of items in the database
	 */
	public static int inventoryLength() {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "SELECT count(*) FROM Inventory;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int result = rs.getInt("Count");
			stmt.close();
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			return 0;
		}
	}
	
	/** 
	 * Print list of all items to console
	 *
	 */
	public static void printInventory() {
		System.out.println("\nINVENTORY DATABASE FULL PRINTOUT:\n");
		
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Inventory;");
			
			while (rs.next()) {
				String title = rs.getString("title");
				float price = rs.getFloat("price");
				String description = rs.getString("description");
				int stock = rs.getInt("stock");
				
				System.out.println("Title: " + title);
				System.out.println("Price: " + price);
				System.out.println("Description: " + description);
				System.out.println("Stock: " + stock + "\n");
			}
			System.out.println("Table print-out complete");
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	
	/**
	 * WILL CLEAR ALL DATA FROM "Inventory" TABLE
	 * DO ***NOT*** USE ON ACCIDENT
	 * 
	 * Clears data from "Inventory" table but does not drop it,
	 * allowing further entries to be added without recreating table
	 */
	public static void clearInventory() {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "DELETE FROM Inventory;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			System.out.println("Inventory table has been cleared");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * DROPS "Inventory" TABLE AND CLEARS ALL OF ITS DATA
	 * DO ***NOT*** USE ON ACCIDENT
	 * 
	 * Drops "Inventory" table, including all of its data
	 * The table will have to be recreated before use
	 */
	public static void dropTable() {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "DROP TABLE Inventory;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			System.out.println("Inventory table has been dropped");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}
