package DB;

import DB.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;


/*
 * This class contains NO data, but allows access only to "Accounts" table in postgres
 * Individual accounts referenced by randomly generated 6-digit numeric ID
 */
public class PostgresAccounts {
	static Connection c = null;
	
	/**
	 * Main method establishes connection to database for any classes within it
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
		if (!tableExists("Accounts")) { // checks for existence of "Accounts" table
			System.out.println("Accounts table creation required");
			createAccountsTable(); // create accounts table if not present
		} else {
			System.out.println("Accounts table present");
		}
		//Example addition of new account
		//String[] newAccount = {"3", "Manager McBoss", "theboss@website.com", "2400 S Address St"};
		//addAccount(newAccount);
		
		
		clearAccounts();
		printAccounts();
	}
	
	/**
	 * 
	 */
	public static boolean tableExists(String tableName) {
		Statement stmt = null;
		
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName + ";");
			rs.close();
			stmt.close();
			return true;
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
			System.out.println("Table has been created.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
		
	/**
	 * @param values [0], account type; [1], name (First Last); [2], email; [3], address (can be null)
	 */
	public static void addAccount(String[] values) {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql;
			if (Integer.parseInt(values[0]) == 1) {
				sql = "INSERT INTO ACCOUNTS (" +
				"ID,TYPE,NAME,EMAIL,ADDRESS) " +
				"VALUES(" +
				accountsLength() + ", " +
				values[0] + ", " +
				"'" + values[1] + "', " +
				"'" + values[2] + "', " +
				"'" + values[3] + "');";
			} else {
				sql = "INSERT INTO ACCOUNTS(" +
				"ID,TYPE,NAME,EMAIL,ADDRESS) " +
				"VALUES(" +
				accountsLength() + ", " +
				values[0] + ", " +
				"'" + values[1] + "', " +
				"'" + values[2] + "', " +
				"null);";
			}
			stmt.executeLargeUpdate(sql);
			stmt.close();
			c.commit();
			System.out.println("Element added to table");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
	
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
			} else {
				System.out.println("No such account");
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
	
	public static void editAccountType(int ID, int type) {
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
	
	public static void editAccountName(int ID, String name) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET NAME = " + name + " where ID = " + ID + ";";
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
	
	public static void editAccountEmail(int ID, String email) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET EMAIL = " + email + " where ID = " + ID + ";";
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
	
	public static void editAccountAddress(int ID, String address) {
		Statement stmt = null;
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE Accounts SET ADDRESS = " + address + " where ID = " + ID + ";";
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
				c.commit();
				System.out.println("Account deleted");
				stmt.close();
			
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
	 * @return true if the account with that ID exists, false if it does not
	 */
	public static boolean accountExists(int ID) {
		if (getAccount(ID).getName() != null) {
			return true;
		} else {
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
		Statement stmt = null;
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("select * from accounts;");
			
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
	 * 
	 */
	public static void clearAccounts() {
		Statement stmt = null;
		
		try {
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "TRUNCATE TABLE Accounts;";
			stmt.execute(sql);
			stmt.close();
			System.out.println("Accounts table has been cleared");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}
}