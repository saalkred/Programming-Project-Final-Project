package Shopping;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.SwingUtilities;

import DB.Item;
import DB.PostgresInventory;
import main.Driver;

public class ItemManager implements ActionListener {
	private String title;
	private float price;
	private String description;
	private int stock;
	private JPanel panel, innerPanel;
	private JButton one, two, three, four;
	private JLabel JL1;
	private JTextArea JTA1;
	private JScrollPane JSP1;
	private JTextField tsearchBar, tquantity, tprice, titems, itemDisplay, adding, deleting;
	private JButton logout, newItem, search, delete, confirm, add;
	private JComboBox choice;
	String searching;
	
	private PostgresInventory inventory = new PostgresInventory();
	
	public ItemManager() { 
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

		innerPanel = new JPanel(new GridLayout(1,5));
		innerPanel.setPreferredSize(new Dimension(200,200));
		panel.add(innerPanel);
		innerPanel.setBounds(125,275,675,400);
		innerPanel.setLayout(new GridLayout(5,1));
		
		JL1 = new JLabel();
		JTA1 = new JTextArea();
		one = new JButton("Delete");
		three = new JButton("Quantity (enter)");
		four = new JButton("Price (enter)");
		
		one.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
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

	public void updateBySearch(String text) {
		ArrayList<Item> al = inventory.searchInventory(text);
		for(int i = 0; i < al.size(); i++) {
			System.out.println(al.get(i).getTitle());
		}
		
		if(!(al.size() > 1)) {
			JPanel captionAndTitle1 = new JPanel(new GridLayout(1,2));
			
			JL1.setText(al.get(0).getTitle() + "\n $" + al.get(0).getPrice());
			JTA1.setText(al.get(0).getDescription());
			JSP1 = new JScrollPane(JL1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			captionAndTitle1.add(JL1); captionAndTitle1.add(JSP1);
			
			JTA1.setEditable(false);
			
			innerPanel.add(captionAndTitle1);
			innerPanel.add(one);
			innerPanel.add(two);
			innerPanel.add(three);
			innerPanel.add(four);
			
			one.addActionListener(this);
			two.addActionListener(this);
			three.addActionListener(this);
			four.addActionListener(this);
			
			SwingUtilities.updateComponentTreeUI(innerPanel);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == search) {
			ArrayList<Item> itemList = inventory.searchInventory(tsearchBar.getText());
			
			updateBySearch(tsearchBar.getText());
//			JLabel itemSearch = new JLabel(tsearchBar.getText());
//			panel.add(itemSearch);
//			System.out.println("Search List Populated");
		}


		if(e.getSource() == add) {
			float price = Float.parseFloat(JOptionPane.showInputDialog(null, "Input the price you want the item to be! It will automatically add"));
			int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Input the amount y of inventory stock"));
			String description = JOptionPane.showInputDialog(null, "Enter the description please!");
			inventory.addItem(new Item(tsearchBar.getText(), price, description, quantity));
			Driver.newChoice(4);
		}

		if(e.getSource() == delete) {
			JLabel itemSearch = new JLabel ("Delete " + tsearchBar.getText() + "?");
			panel.add(itemSearch);
			deleteItem(tsearchBar.getText());
			System.out.println("Item Deleted");

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
		if(e.getSource() == one) {
			inventory.deleteItem(PostgresInventory.getItem(tsearchBar.getText()).getTitle());
			Driver.newChoice(4);
		}
		if (e.getSource() == three) {
			int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Input the amount of inventory stock for the searched item:"));
			inventory.setItemStock(PostgresInventory.getItem(tsearchBar.getText()).getTitle(), quantity);
			Driver.newChoice(4);
		}
		if(e.getSource() == four) {
			int price = Integer.parseInt(JOptionPane.showInputDialog(null, "Input the price you want the item to be! It will automatically change the price of the item you entered"));
			inventory.setItemPrice(PostgresInventory.getItem(tsearchBar.getText()).getTitle(), price);
			Driver.newChoice(4);
		}
		if(e.getSource() == logout) { // Log out the user
			Driver.newChoice(6);

		}


	}
}
