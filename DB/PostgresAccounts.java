package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


/*
 * This class contains NO data, but allows access to "Accounts" table in postgres
 */

public class PostgresAccounts {
	private static Connection c = null;
	
	public PostgresAccounts(){
		try { // establishes database connection for Connection "c"
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
		
		if (!tableExists()) { // checks for existence of "Accounts" table
			System.out.println("Accounts table creation required");
			createAccountsTable(); // create "Accounts" table if not present
		} else {
			System.out.println("Accounts table present");
		}
	}
	
	/**
	 * @return True if the table "Accounts" exists in the database, false if it does not
	 */
	public static boolean tableExists(){
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT to_regclass('Accounts');");
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
	 * Creates Accounts table in database with standard headers
	 * Not needed if this method has been previously called AND the table has not been dropped
	 */
	public static void createAccountsTable() {
		try {
			Statement stmt = null;
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE ACCOUNTS (" +
			"TYPE INT NOT NULL," + // account type: 1 = customer, 2 = clerk, 3 = manager
			"NAME TEXT NOT NULL," + // first and last name
			"USERNAME TEXT PRIMARY KEY NOT NULL," + // username for login
			"PASSWORD TEXT NOT NULL," + // password associated with the email address
			"EMAIL TEXT NOT NULL," + // email address
			"ADDRESS TEXT)"; // home address, customer (1) only
			stmt.executeUpdate(sql);
			stmt.close();
			System.out.println("Accounts table has been created.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
		
	/**
	 * @param newAccount account must have type, name email
	 */
	public static void addAccount(Account newAccount) {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql;
			if (newAccount.getType() == 1) {
				sql = "INSERT INTO ACCOUNTS (" +
				"TYPE,NAME,USERNAME,PASSWORD,EMAIL,ADDRESS) " +
				"VALUES(" +
				newAccount.getType() + ", " +
				"'" + newAccount.getName() + "', " +
				"'" + newAccount.getUsername() + "', " +
				"'" + newAccount.getPassword() + "', " +
				"'" + newAccount.getEmail() + "', " +
				"'" + newAccount.getAddress() + "');";
			} else {
				sql = "INSERT INTO ACCOUNTS(" +
				"TYPE,NAME,USERNAME,PASSWORD,EMAIL,ADDRESS) " +
				"VALUES(" +
				newAccount.getType() + ", " +
				"'" + newAccount.getName() + "', " +
				"'" + newAccount.getUsername() + "', " +
				"'" + newAccount.getPassword() + "', " +
				"'" + newAccount.getEmail() + "', " +
				"null);";
			}
			stmt.executeLargeUpdate(sql);
			stmt.close();
			c.commit();
			System.out.println("New account added to table");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param Username 
	 * @return Of type Account
	 */
	public static Account getAccount(String username) {
		Statement stmt = null;
		Account result = new Account();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts WHERE Username = '" + username + "';");
			
			if (rs.isBeforeFirst()) { // returns true if data present in ResultSet, if not (ie no account at ID) returns false
				rs.next();
				System.out.println("Account found.");
				result = new Account(rs.getInt("type"), rs.getString("name"),
						rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("address"));
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
	 * @param username
	 * @param type
	 */
	public static void setAccountType(String username, int type) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET TYPE = " + type + " where Username = '" + username + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account  = " + username + " successfully updated");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param username
	 * @param name
	 */
	public static void setAccountName(String username, String name) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET NAME = '" + name + "' WHERE Username = '" + username + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account ID = " + username + " successfully updated");
			
		} catch (Exception e) {
			e.printStackTrace(); 
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param currentUsername
	 * @param newUsername
	 */
	public static void setAccountUsername(String currentUsername, String newUsername) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET USERNAME = '" + newUsername + "' WHERE Username = '" + currentUsername + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account Username = " + currentUsername + " successfully updated");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 */
	public static void setAccountPassword(String username, String password) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET PASSWORD = '" + password + "' WHERE Username = '" + username + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account username = " + username + " successfully updated");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param username
	 * @param email
	 */
	public static void setAccountEmail(int username, String email) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET EMAIL = '" + email + "' WHERE Username = '" + username + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account Username = " + username + " successfully updated");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param ID
	 * @param address
	 */
	public static void setAccountAddress(String username, String address) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET ADDRESS = '" + address + "' WHERE Username = '" + username + "';";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account Username = " + username + " updated");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param ID The 6-digit ID for the account
	 */
	public static void deleteAccount(String username) {
		if (accountExists(username)) {
			Statement stmt = null;
			try {
				c.setAutoCommit(false);
				stmt = c.createStatement();
				String sql = "DELETE FROM Accounts WHERE Username = '" + username + "';";
				stmt.executeUpdate(sql);
				stmt.close();
				c.commit();
				System.out.println("Account deleted");
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
		} else {
			System.out.println("No such account with that ID");
		}
	}
	
	public static boolean accountExists(String username) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts WHERE Username = '" + username + "';");
			
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
	
	/**
	 * 
	 * @return the number of accounts in the database
	 */
	public static int accountsLength() {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "SELECT count(*) FROM Accounts;";
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
	 * Print list of all accounts to console
	 *
	 */
	public static void printAccounts() {
		System.out.println("\nACCOUNTS DATABASE FULL PRINTOUT:\n");
		
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts;");
			
			while (rs.next()) {
				int type = rs.getInt("type");
				String name = rs.getString("name");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");
				String address = rs.getString("address");
				
				System.out.print("Type: ");
				switch(type) {
				case 1: System.out.println("Customer");
						break;
				case 2: System.out.println("Clerk");
						break;
				case 3: System.out.println("Manager");
						break;
				default: System.out.println("Unknown account type");
						break;
				}
				System.out.println("Name: " + name);
				System.out.println("Username: " + username);
				System.out.println("Password: " + password);
				System.out.println("Email: " + email);
				System.out.println("Address: " + address + "\n");
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
	 * WILL CLEAR ALL DATA FROM "Accounts" TABLE
	 * DO ***NOT*** USE ON ACCIDENT
	 * 
	 * Clears data from "Accounts" table but does not drop it,
	 * allowing further entries to be added without recreating table
	 */
	public static void clearAccounts() {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "DELETE FROM Accounts;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			System.out.println("Accounts table has been cleared");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * DROPS "Accounts" TABLE AND CLEARS ALL OF ITS DATA
	 * DO ***NOT*** USE ON ACCIDENT
	 * 
	 * Drops "Accounts" table, including all of its data
	 * The table will have to be recreated before use
	 */
	public static void dropTable() {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "DROP TABLE Accounts;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			System.out.println("Accounts table has been dropped");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}