package Shopping;

import java.awt.Font;
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
	private JComboBox choice;
	
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
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == choice) {
			int i = choice.getSelectedIndex();
			if(i == 0) {
				frame.dispose();
				ShoppingPage sp = new ShoppingPage();
			} else if(i == 1) {
				frame.dispose();
				PendingOrders PO = new PendingOrders();
			} else if (i == 2) {
				frame.dispose();
				ItemManager im = new ItemManager();
			}
		}
		if(e.getSource() == logout) { // Log out the user
			frame.dispose();
			Login.main(new String[1]); // TODO: Fix!
		}
		
	}
}
