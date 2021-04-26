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
import DB.PostgresAccounts;

public class PendingOrders extends ShoppingCart implements ActionListener {
	private JFrame frame;
	private JPanel panel;
	private JButton logout;
	private JComboBox choice, items;
	
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
				//frame.dispose();
				
				
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
		if(e.getSource() == logout) { // Log out the user
			
			Driver.newChoice(6);
			
		}
		
	}
	
}
