package main;

import Accounts.*;
import Shopping.*;
import DB.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Driver {
	private static Login login = new Login();
	private static CreateAccountCustomer createActCust = new CreateAccountCustomer();
	private static CreateAccountEmployee createActEmpl = new CreateAccountEmployee();
	private static ShoppingPage shopping = new ShoppingPage();
	private static PendingOrders pending = new PendingOrders();
	private static ItemManager manager = new ItemManager();
	
	
	private static Object syncObject = new Object();
	private static String currentAccountUsername;
	public static ArrayList<Item> shoppingCart = new ArrayList<Item>();
	
	private static PostgresPending pPending = new PostgresPending();
	
	private static int choice = 0;
	
	public static JFrame frame = new JFrame();
	
	
	public static void main(String[] args) {
		frame.setSize(1200,850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		login.addPanel(frame);
		
		do {
			sleep();
			switch (choice) {
				case 0:
					login.addPanel(frame);
					refresh();
					break;
				case 1:
					createActCust.addPanel(frame);
					refresh();
					break;
				case 2: 
					createActEmpl.addPanel(frame);
					refresh();
					break;
				case 3:
					shopping.addPanel(frame);
					refresh();
					break;
				case 4:
					manager.addPanel(frame);
					refresh();
					break;
				case 5:
					pending.addPanel(frame);
					refresh();
					break;
				case 6:
					pPending.addPending(currentAccountUsername, 1, shoppingCart);
					currentAccountUsername = "";
					shoppingCart.clear();
					login.addPanel(frame);
					refresh();
					break;
			}
		}  while (choice != 6);
	}
	
	
	public static void sleep() {
		synchronized(syncObject) {
		    try {
		        // Calling wait() will block this thread until another thread
		        // calls notify() on the object.
		        syncObject.wait();
		    } catch (InterruptedException e) {
		        // Happens if someone interrupts your thread.
		    }
		}
	}
	
	private static void wake() {
		synchronized(syncObject) {
		    syncObject.notify();
		}
	}
	
	public static void newChoice(int newChoice) {
		switch (choice) {
			case 0:
				login.removePanel(frame);
				break;
			case 1:
				createActCust.removePanel(frame);
				break;
			case 2:
				createActEmpl.removePanel(frame);
				break;
			case 3:
				shopping.removePanel(frame);
				break;
			case 4:
				manager.removePanel(frame);
				break;
			case 5:
				pending.removePanel(frame);
				break;
			case 6:
				login.removePanel(frame);
				break;
		}
		choice = newChoice;
		wake();
	}
	
	public static void setCurrentAccount (String username) {
		currentAccountUsername = username;
	}
	
	public static String getCurrentAccount () {
		return currentAccountUsername;
	}
	
	public static void refresh() {
		SwingUtilities.updateComponentTreeUI(frame);
	}
}