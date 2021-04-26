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
	private JFrame frame;
	private JPanel panel;
	private JTextField searchBar, quantity, price;
	private JButton logout, newItem, search, delete, confirm;
	private JComboBox choice;




	private String itemSearch;
	private String item;



	public ItemManager() { 
		System.out.println();


		panel= new JPanel();
		frame= new JFrame();
		frame.setSize(1200,850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);




		JLabel title = new JLabel("Item Manager");
		title.setBounds(425, 10, 1200, 100);
		panel.add(title);
		title.setFont(new Font(null, Font.BOLD,55));

		frame.setVisible(true);

	}
	public void find(String search) {

		ArrayList<Item> itemList = PostgresInventory.getInventory();
		itemList.toArray();



		for(int i = 0; i < itemList.size(); i++) {


			Item itemSearch = itemList.get(i);
			if(search == itemSearch.getTitle()) {
				//itemSearch.print();
				String item = itemSearch.getTitle();
				System.out.println(item);
				//trying to make this search query have addItem and deleteItem options.
			}

		}


	}

	public void deleteItem(String itemSearch) {
		this.item = itemSearch;
		System.out.println("Delete " + itemSearch + "?"); //make this into GUI 
		//when JButton confirm, then 
		deleteItem(itemSearch);
	}

	public void addItem(String itemSearch) {
		this.item = itemSearch;
		addItem(itemSearch);
	}
	public void addPanel(JFrame frame) {
		frame.add(panel);
	}

	public void removePanel(JFrame frame) {
		frame.remove(panel);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
	
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
			Driver.newChoice(0);

		}
		//if(e.getSource == )

	}
}