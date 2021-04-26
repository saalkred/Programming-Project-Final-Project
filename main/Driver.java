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
	
	private static int choice = 0;
	
	public static JFrame frame = new JFrame();
	
	
	public static void main(String[] args) {
		frame.setSize(1200,850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		login.addPanel(frame);
		
		while (choice != 6) {
			sleep();
			switch (choice) {
				case 0:
					//frame.removeAll();
					login.addPanel(frame);
					refresh();
					break;
				case 1:
					//frame.removeAll();
					createActCust.addPanel(frame);
					refresh();
					break;
				case 2: 
					//frame.removeAll();
					createActEmpl.addPanel(frame);
					refresh();
					break;
				case 3:
					//frame.removeAll();
					shopping.addPanel(frame);
					refresh();
					System.out.println("You should be seeing shopping now");
					break;
				case 4:
					//frame.removeAll();
					manager.addPanel(frame);
					refresh();
					break;
				case 5:
					//frame.removeAll();
					pending.addPanel(frame);
					refresh();
					break;
				case 6:
					break;
			}
		}
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
		}
		choice = newChoice;
		wake();
	}
	
	public static void setCurrentAccount (String username) {
		currentAccountUsername = username;
	}
	
	public static void refresh() {
		SwingUtilities.updateComponentTreeUI(frame);
	}
}