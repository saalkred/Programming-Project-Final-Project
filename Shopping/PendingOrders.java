package Shopping;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
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

public class PendingOrders implements ActionListener {
	private JPanel panel, innerPanel, oPanel1, oPanel2, oPanel3, oPanel4, oPanel5, oPanel6;
	private JButton logout, oButton1, oButton2, oButton3, oButton4, oButton5, oButton6;
	private JComboBox choice;
	
	private ArrayList<String> pendingList = new ArrayList<String>();
	
	private PostgresPending pending = new PostgresPending();
	private PostgresAccounts pAccount = new PostgresAccounts();

	public PendingOrders() {
		panel= new JPanel();
		panel.setBounds(0, 0, 1200,850);
		panel.setLayout(new GridBagLayout());

		JLabel title = new JLabel("Pending Orders");
		title.setBounds(400, 10, 1200, 100);
		panel.add(title);
		title.setFont(new Font(null, Font.BOLD,55));

		ArrayList<String> optionsAL = new ArrayList<>();
		optionsAL.add("Login");
		optionsAL.add("Shopping");
		optionsAL.add("Pending Orders");
		optionsAL.add("Item Manager");
		
		String[] options = new String[optionsAL.size()];
		for(int i = 0; i < optionsAL.size(); i++) {
			options[i] = optionsAL.get(i);
		}

		choice = new JComboBox(options);
		choice.setSelectedIndex(0);
		panel.add(choice);
		choice.setBounds(100, 50, 150, 25);
		choice.addActionListener(this);

		pendingList = pending.getPendingList();
		
		for (int i = 0; i < pendingList.size(); i++) {
			if (pending.getOrderStatus(pendingList.get(i)) == 1) {
				pendingList.remove(i);
				i--;
			}
		}

		innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(2,3));
		innerPanel.setBounds(200, 150, 800, 600);
		
		
		oPanel1 = new JPanel();
		oPanel1.setLayout(new GridLayout(3, 1));
		oPanel2 = new JPanel();
		oPanel2.setLayout(new GridLayout(3, 1));
		oPanel3 = new JPanel();
		oPanel3.setLayout(new GridLayout(3, 1));
		oPanel4 = new JPanel();
		oPanel4.setLayout(new GridLayout(3, 1));
		oPanel5 = new JPanel();
		oPanel5.setLayout(new GridLayout(3, 1));
		oPanel6 = new JPanel();
		oPanel6.setLayout(new GridLayout(3, 1));
		
		
		oButton1 = new JButton("Confirm");
		oButton1.addActionListener(this);
		oPanel1.add(oButton1);
		
		oButton2 = new JButton("Confirm");
		oButton2.addActionListener(this);
		oPanel2.add(oButton2);
		
		oButton3 = new JButton("Confirm");
		oButton3.addActionListener(this);
		oPanel3.add(oButton3);
		
		oButton4 = new JButton("Confirm");
		oButton4.addActionListener(this);
		oPanel4.add(oButton4);
		
		oButton5 = new JButton("Confirm");
		oButton5.addActionListener(this);
		oPanel5.add(oButton5);
		
		oButton6 = new JButton("Confirm");
		oButton6.addActionListener(this);
		oPanel6.add(oButton6);
		
		
		Font orderFont = new Font("orderFont", Font.BOLD, 20);
		
		
		JLabel order1 = new JLabel("Order 1: " + pendingList.get(0));
		order1.setFont(orderFont);
		oPanel1.add(order1);
		
		JLabel order2 = new JLabel("Order 2: " + pendingList.get(1));
		order2.setFont(orderFont);
		oPanel2.add(order2);
		
		JLabel order3 = new JLabel("Order 3: " + pendingList.get(2));
		order3.setFont(orderFont);
		oPanel3.add(order3);
		
		JLabel order4 = new JLabel("Order 4: " + pendingList.get(3));
		order4.setFont(orderFont);
		oPanel4.add(order4);
		
		JLabel order5 = new JLabel("Order 5: " + pendingList.get(4));
		order5.setFont(orderFont);
		oPanel5.add(order5);
		
		JLabel order6 = new JLabel("Order 6: " + pendingList.get(5));
		order6.setFont(orderFont);
		oPanel6.add(order6);
		
		if (pending.getOrderStatus(pendingList.get(0)) == 3) {
			oPanel1.add(new JLabel("CONFIRMED"));
		}
		
		if (pending.getOrderStatus(pendingList.get(1)) == 3) {
			oPanel2.add(new JLabel("CONFIRMED"));
		}
		
		if (pending.getOrderStatus(pendingList.get(2)) == 3) {
			oPanel3.add(new JLabel("CONFIRMED"));
		}
		
		if (pending.getOrderStatus(pendingList.get(3)) == 3) {
			oPanel4.add(new JLabel("CONFIRMED"));
		}
		
		if (pending.getOrderStatus(pendingList.get(4)) == 3) {
			oPanel5.add(new JLabel("CONFIRMED"));
		}
		
		if (pending.getOrderStatus(pendingList.get(5)) == 3) {
			oPanel6.add(new JLabel("CONFIRMED"));
		}
		
		innerPanel.add(oPanel1);
		innerPanel.add(oPanel2);
		innerPanel.add(oPanel3);
		innerPanel.add(oPanel4);
		innerPanel.add(oPanel5);
		innerPanel.add(oPanel6);

		panel.add(innerPanel);

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
	}
	
	public void addPanel(JFrame frame) {
		choice.setSelectedIndex(2);
		frame.add(panel);
	}

	public void removePanel(JFrame frame) {
		frame.remove(panel);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == choice) {
			int index = choice.getSelectedIndex();
			switch (index) {
				case 0:
					Driver.newChoice(0);
					break;
				case 1:
					Driver.newChoice(3);
					break;
				case 2:
					Driver.newChoice(5);
					break;
				case 3:
					Driver.newChoice(4);
					break;
			}
		}
		
		if (e.getSource() == oButton1) {
			if (pending.getOrderStatus(pendingList.get(0)) != 3) {
				pending.setOrderStatus(pendingList.get(0), 3);
				oPanel1.add(new JLabel("CONFIRMED"));
				Driver.newChoice(5);
				Driver.refresh();
			}
		}
		
		if (e.getSource() == oButton2) {
			if (pending.getOrderStatus(pendingList.get(1)) != 3) {
				pending.setOrderStatus(pendingList.get(1), 3);
				oPanel2.add(new JLabel("CONFIRMED"));
				Driver.newChoice(5);
				Driver.refresh();
			}
		}
		
		if (e.getSource() == oButton3) {
			if (pending.getOrderStatus(pendingList.get(2)) != 3) {
				pending.setOrderStatus(pendingList.get(2), 3);
				oPanel3.add(new JLabel("CONFIRMED"));
				Driver.newChoice(5);
				Driver.refresh();
			}
		}
		
		if (e.getSource() == oButton4) {
			if (pending.getOrderStatus(pendingList.get(3)) != 3) {
				pending.setOrderStatus(pendingList.get(3), 3);
				oPanel4.add(new JLabel("CONFIRMED"));
				Driver.newChoice(5);
				Driver.refresh();
			}
		}
		
		if (e.getSource() == oButton5) {
			if (pending.getOrderStatus(pendingList.get(4)) != 3) {
				pending.setOrderStatus(pendingList.get(4), 3);
				oPanel5.add(new JLabel("CONFIRMED"));
				Driver.newChoice(5);
				Driver.refresh();
			}
		}
		
		if (e.getSource() == oButton6) {
			if (pending.getOrderStatus(pendingList.get(5)) != 3) {
				pending.setOrderStatus(pendingList.get(5), 3);
				oPanel6.add(new JLabel("CONFIRMED"));
				Driver.newChoice(5);
				Driver.refresh();
			}
		}
		
		
		if(e.getSource() == logout) { // Log out the user

			Driver.newChoice(6);

		}



	}

}



