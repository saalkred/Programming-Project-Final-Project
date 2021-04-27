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
import javax.swing.JTextField;

import Accounts.Login;
import DB.Item;
import Shopping.ItemManager;
import Shopping.PendingOrders;
import Shopping.ShoppingPage;
import main.Driver;
import DB.PostgresInventory;
import DB.PostgresPending;

public class ItemManager implements ActionListener {
	private String title;
	private float price;
	private String description;
	private int stock;
	private JPanel panel;
	private JTextField tsearchBar, tquantity, tprice, titems, itemDisplay, adding, deleting;
	private JButton logout, newItem, search, delete, confirm, add;
	private JComboBox choice;
	String searching;

	private PostgresInventory inventory = new PostgresInventory();




	private String item;



	public ItemManager() { 
		System.out.println();


		panel= new JPanel();


		search = new JButton("Search");
		delete = new JButton("Delete");
		add = new JButton("Add");
		search.addActionListener(this);
		delete.addActionListener(this);
		add.addActionListener(this);

		titems = new JTextField(20);

		JLabel title = new JLabel("Item Manager");
		title.setBounds(425, 10, 1200, 100);
		panel.add(title);
		title.setFont(new Font(null, Font.BOLD,55));

		panel.add(add);
		panel.add(delete);


		search = new JButton("Search!");
		panel.add(search);
		search.setBounds(500,200,175,60);
		search.addActionListener(this);

		tsearchBar = new JTextField(40);
		tsearchBar.setEditable(true);
		tsearchBar.setFont(new Font("SansSerif", Font.BOLD, 23));
		panel.add(tsearchBar);
		tsearchBar.setBounds(250,300,475,60);
		tsearchBar.addActionListener(this);


	}


	public void deleteItem(String itemSearch) {
		itemSearch = tsearchBar.getText();
		System.out.println("Delete " + itemSearch + "?"); 
		//when JButton confirm, then 
		Item deleteItem = new Item(title, price, description, stock);//TODO: need to add function to make title, price, desc and stock fill in with user input
		String itemTitle = deleteItem.getTitle();
		inventory.deleteItem(itemTitle); //TODO: unsure of how to fix this error

	}


	public void addPanel(JFrame frame) {
		frame.add(panel);
	}

	public void removePanel(JFrame frame) {
		frame.remove(panel);
	}



	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == search) {
			ArrayList<Item> itemList = inventory.searchInventory(tsearchBar.getText());
			JLabel itemSearch = new JLabel(item);
			panel.add(itemSearch);
			System.out.println("Search List Populated");
		}


		if(e.getSource() == add) {
			JLabel addItemLabel = new JLabel(item);
			panel.add(addItemLabel);
			inventory.addItem(new Item(tsearchBar.getText(), price, description, stock));
		}

		if(e.getSource() == delete) {
			JLabel itemSearch = new JLabel ("Delete " + tsearchBar.getText() + "?");
			panel.add(itemSearch);
			deleteItem(tsearchBar.getText());

		}


		if(e.getSource() == choice) {
			int i = choice.getSelectedIndex();
			if(i == 0) {
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