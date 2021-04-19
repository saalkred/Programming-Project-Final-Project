package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Random;


/*
 * This class contains NO data, but allows access to "Accounts" table in postgres
 * Individual accounts referenced by randomly generated 6-digit numeric ID
 */
public class PostgresAccounts {
	static Connection c = null;
	
	/**
	 * Main method establishes connection to database for any calls within it
	 * @param args
	 */
	public static void main(String[] args){
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
		if (!tableExists()) { // checks for existence of "Accounts" table
			System.out.println("Accounts table creation required");
			createAccountsTable(); // create "Accounts" table if not present
		} else {
			System.out.println("Accounts table present");
		}
		//Example addition of new account
		//Account newAccount = new Account(3, "Gerold Kaplan", "gskaplan@asu.edu", "1000 State St");
		//addAccount(newAccount);
		
		//clearAccounts();
		//dropTable();
		
		//setAccountName(443615, "Sammy Kaplan");
		
		
		printAccounts();
		
		try { // the Connection should be closed at the end of this class
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
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
			"ID INT PRIMARY KEY NOT NULL," + // sequential IDs
			"TYPE INT NOT NULL," + // account type: 1 = customer, 2 = clerk, 3 = manager
			"NAME TEXT NOT NULL," + // first and last name delineated by space
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
	 * Generates randomized IDs, and checks that they are unique within the table
	 * 
	 * 
	 * @return a 6-digit integer to be used as an account ID
	 */
	public static int newAccountID() {
		Random ran = new Random();
		int newID = 0;
		String temp = "";
		do {
			for (int i = 0; i < 6; i++) {
				temp += ran.nextInt(10);
			}
			newID = Integer.parseInt(temp);
			temp = "";
		} while (accountExists(newID));
		
		return newID;
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
				"ID,TYPE,NAME,EMAIL,ADDRESS) " +
				"VALUES(" +
				newAccountID() + ", " +
				newAccount.getType() + ", " +
				"'" + newAccount.getName() + "', " +
				"'" + newAccount.getEmail() + "', " +
				"'" + newAccount.getAddress() + "');";
			} else {
				sql = "INSERT INTO ACCOUNTS(" +
				"ID,TYPE,NAME,EMAIL,ADDRESS) " +
				"VALUES(" +
				newAccountID() + ", " +
				newAccount.getType() + ", " +
				"'" + newAccount.getName() + "', " +
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
	 * @param ID
	 * @return
	 */
	public static Account getAccount(int ID) {
		Statement stmt = null;
		Account result = new Account();
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts WHERE ID = " + ID + ";");
			
			if (rs.isBeforeFirst()) { // returns true if data present in ResultSet, if not (ie no account at ID) returns false
				rs.next();
				System.out.println("Account found.");
				result = new Account(rs.getInt("id"), rs.getInt("type"), rs.getString("name"),
											 rs.getString("email"), rs.getString("address"));
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
	 * @param ID
	 * @param type
	 */
	public static void setAccountType(int ID, int type) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET TYPE = " + type + " where ID = " + ID + ";";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account ID = " + ID + " successfully updated");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param ID
	 * @param name
	 */
	public static void setAccountName(int ID, String name) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET NAME = '" + name + "' WHERE ID = " + ID + ";";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account ID = " + ID + " successfully updated");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
	/**
	 * 
	 * @param ID
	 * @param email
	 */
	public static void setAccountEmail(int ID, String email) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET EMAIL = '" + email + "' WHERE ID = " + ID + ";";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account ID = " + ID + " successfully updated");
			
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
	public static void setAccountAddress(int ID, String address) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET ADDRESS = '" + address + "' WHERE ID = " + ID + ";";
			stmt.executeUpdate(sql);
			c.commit();
			stmt.close();
			System.out.println("Account ID = " + ID + " updated");
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
	public static void deleteAccount(int ID) {
		if (accountExists(ID)) {
			Statement stmt = null;
			try {
				c.setAutoCommit(false);
				stmt = c.createStatement();
				String sql = "DELETE FROM Accounts WHERE ID = " + ID + ";";
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
	
	/**
	 * 
	 * @param ID The 6-digit ID for the account
	 * @return true if an account with that ID exists, false if one does not
	 */
	public static boolean accountExists(int ID) {
		if (getAccount(ID).getID() == 0) {
			return false;
		} else {
			return true;
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
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Accounts;");
			
			while (rs.next()) {
				int ID = rs.getInt("id");
				int type = rs.getInt("type");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				
				System.out.println("ID: " + ID);
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