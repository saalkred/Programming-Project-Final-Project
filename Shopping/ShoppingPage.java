package Shopping;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Accounts.Login;

import main.Driver;

import DB.Item;
import DB.PostgresAccounts;
import DB.PostgresInventory;

public class ShoppingPage implements ActionListener {

	private PostgresInventory pi = new PostgresInventory();
	
	private JPanel panel = new JPanel();
	private JButton checkout, logout, search;
	private JComboBox choice;
	private JTextField searchBar,
	caption1, caption2, caption3, caption4, caption5, caption6;
	private JScrollPane scrollPane;
	
	public ShoppingPage() {
		JLabel title = new JLabel("Shopping Page");
		title.setBounds(375, 10, 1200, 100);
		panel.add(title);
		title.setFont(new Font(null, Font.BOLD,55));
		
		ArrayList<String> optionsAL = new ArrayList<>();
		optionsAL.add("Shopping Cart");
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
		panel.setLayout(null);
		
		logout = new JButton("Log Out ->");
		panel.add(logout);
		logout.setBounds(950, 50, 150, 25);
		logout.addActionListener(this);
		
		checkout = new JButton("Checkout");
		panel.add(checkout);
		checkout.setBounds(850, 700, 200, 25);
		checkout.addActionListener(this);

		search = new JButton("Search!");
		panel.add(search);
		search.setBounds(525,200,175,60);
		search.addActionListener(this);
		
		searchBar = new JTextField("");
		searchBar.setEditable(true);
		searchBar.setFont(new Font("SansSerif", Font.BOLD, 23));
		panel.add(searchBar);
		searchBar.setBounds(25,200,475,60);
		searchBar.addActionListener(this);
		
		scrollPane = new JScrollPane(new JTextArea(5,10));
		panel.add(scrollPane);
		scrollPane.setBounds(750,200, 400, 500);
	}
	
	public void addPanel(JFrame frame) {
		frame.add(panel);
	}
	
	public void removePanel(JFrame frame) {
		frame.remove(panel);
	}
	
	public void itemDisplay() {
		ArrayList<Item> al = new ArrayList<>();
		al = pi.getInventory();
		for(int i = 0; i < al.size(); i++) {
			System.out.println(al.get(i).getDescription());
		}
	}
	
	public ArrayList<String> addNavigationByType(String username) {
		ArrayList<String> optionsAL = new ArrayList<>();
		if(PostgresAccounts.getAccount("skaplan").getType() == 2 || 
				PostgresAccounts.getAccount("skaplan").getType() ==  3) {
			optionsAL.add("Pending Orders");
			optionsAL.add("Item Manager");
		}
		return optionsAL;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == choice) {
			int i = choice.getSelectedIndex();
			if(i == 1) {
				Driver.newChoice(5);
			} else if (i == 2) {
				Driver.newChoice(4);
			}
		}
		if(e.getSource() == checkout) {
			JOptionPane.showMessageDialog(null, "Your order for x items ($y) was completed successfully.", "Order Successful", JOptionPane.DEFAULT_OPTION);
		}
		if(e.getSource() == logout) { // Log out the user
			Driver.newChoice(0);;
		}
		if(e.getSource() == search) {
			Driver.newChoice(3);
			//sp.itemDisplay();
		}
	}
}
