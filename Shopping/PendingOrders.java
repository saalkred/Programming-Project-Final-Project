package Shopping;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Accounts.Login;
import DB.Item;
import DB.PostgresAccounts;
import main.Driver;
import DB.PostgresPending;

public class PendingOrders extends ShoppingCart implements ActionListener {
	private JFrame frame;
	private JPanel panel;
	private JButton logout;
	private JComboBox choice, itemList;
	JComboBox items;

	public void displayPanel(JFrame frame) {
		frame.add(panel);
	}
	public PendingOrders() {


		panel= new JPanel();
		frame= new JFrame();
		frame.setSize(1200,850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		JLabel title = new JLabel("Pending Orders");
		title.setBounds(400, 10, 1200, 100);
		panel.add(title);
		title.setFont(new Font(null, Font.BOLD,55));

		ArrayList<String> optionsAL = new ArrayList<>();
		//if(PostgresAccounts.getAccount("user").getType() == 2) {
		//	optionsAL.add("Pending Orders");
		//	optionsAL.add("Item Manager");
		//} else if (PostgresAccounts.getAccount("user").getType() == 3) {
		//	optionsAL.add("Pending Orders");
		//	optionsAL.add("Item Manager");
		//}
		optionsAL.add("Shopping Cart");
		optionsAL.add("Pending Orders");
		optionsAL.add("Item Manager");

		String[] options = new String[optionsAL.size()];
		for(int i = 0; i < optionsAL.size(); i++) {
			options[i] = optionsAL.get(i);
		}

		ArrayList<String> username = PostgresPending.getPendingList(); //TODO: null pointer exception - need to fix
		String[] items = new String[username.size()];


		for(int i = 0; i < username.size(); i++) {
			items[i] = username.get(i);

			//okay let think this through. what do I want? 

			// I want to be able to fill the JComboBox with the 6 different order slots
			//there is a method called getOrder that gets the items from the order, and takes in a username parameter
			//there is a method called getPendingList that gives the usernames of the pending orders
			//I want to give the usernames to getOrder, and then the getOrder to JComboBox
			//the problems come in with type mismatches. 
			//ah, I can use toArray on an ArrayList to hopefully make it compatible with JComboBox
		}
		


		//how will the gui work? 
		//so theoretically, I'd like to have the checkbox tied to the combobox somehow. when certain 
		//item is selected, it will have the check option, and when it's checked, it will 
		//probably ask for confirmation, and then delete the item from pending orders with deleteOrders

		JLabel order1 = new JLabel("Order 1");
		JLabel order2 = new JLabel("Order 2");
		JLabel order3 = new JLabel("Order 3");
		JLabel order4 = new JLabel("Order 4");
		JLabel order5 = new JLabel("Order 5");
		JLabel order6 = new JLabel("Order 6");

		panel.setLayout(new GridLayout(2,6));
		panel.add(order1);
		panel.add(order2);
		panel.add(order3);
		panel.add(order4);
		panel.add(order5);
		panel.add(order6);
		
		itemList = new JComboBox(items);
		itemList.setSelectedIndex(2); //this might change
		panel.add(itemList);
		itemList.setBounds(500, 500, 150, 50);
		itemList.addActionListener(this);
		panel.setLayout(null);




		choice = new JComboBox(options);
		choice.setSelectedIndex(1);
		panel.add(choice);
		choice.setBounds(100, 50, 150, 25);
		choice.addActionListener(this);
		panel.setLayout(null);

		logout = new JButton("Log Out ->");
		panel.add(logout);
		logout.setBounds(950, 50, 150, 25);
		logout.addActionListener(this);

		frame.setVisible(true);


	}
	public void addPanel(JFrame frame) {
		frame.add(panel);
	}

	public void removePanel(JFrame frame) {
		frame.remove(panel);
	}





	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource() == choice) {
			int i = choice.getSelectedIndex();
			if(i == 0) {



				/*
				newChoice =
				0 - Login
				1 - Create Account Customer
				2 - Create Account Employee
				3 - Shopping
				4 - Item Manager
				5 - Pending Orders
				6 - Logout (leads back to Login page)
				Driver.getUsername();
				Driver.setUsername();

				 */
				
				
				Driver.newChoice(3);

			} else if(i == 1) {

				Driver.newChoice(5);

			} else if (i == 2) {

				Driver.newChoice(4);

			}
		}
		if(e.getSource() == itemList) {
			int j = itemList.getSelectedIndex();
		}
		if(e.getSource() == logout) { // Log out the user

			Driver.newChoice(6);

		}
		

	}

}
