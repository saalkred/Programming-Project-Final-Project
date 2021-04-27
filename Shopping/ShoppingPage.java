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
import DB.PostgresAccounts;
import DB.PostgresInventory;
import main.Driver;

public class ShoppingPage implements ActionListener {

	private PostgresInventory pi = new PostgresInventory();

	private JPanel panel = new JPanel();
	private JPanel innerPanel, LabelDesc1, LabelDesc2, LabelDesc3, LabelDesc4, LabelDesc5, LabelDesc6;
	private JButton checkout, logout, search;
	private JComboBox choice;
	private JTextField searchBar;
	private JButton caption1, caption2, caption3, caption4, caption5, caption6;
	private JScrollPane scrollPane;
	private JLabel JL1, JL2, JL3, JL4, JL5, JL6;
	private JTextArea JTA1, JTA2, JTA3, JTA4, JTA5, JTA6;
	private JScrollPane JSP1, JSP2, JSP3, JSP4, JSP5, JSP6;
	private JPanel captionAndTitle1, captionAndTitle2, captionAndTitle3, captionAndTitle4, captionAndTitle5, captionAndTitle6;

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

		String addToCart = "Add To Cart";
		caption1 = new JButton(addToCart);
		caption2 = new JButton(addToCart);
		caption3 = new JButton(addToCart);
		caption4 = new JButton(addToCart);
		caption5 = new JButton(addToCart);
		caption6 = new JButton(addToCart);

		JL1 = new JLabel();
		JL2 = new JLabel();
		JL3 = new JLabel();
		JL4 = new JLabel();
		JL5 = new JLabel();
		JL6 = new JLabel();

		JTA1 = new JTextArea();
		JTA2 = new JTextArea();
		JTA3 = new JTextArea();
		JTA4 = new JTextArea();
		JTA5 = new JTextArea();
		JTA6 = new JTextArea();

		innerPanel = new JPanel();
		innerPanel.setPreferredSize(new Dimension(200,200));
		panel.add(innerPanel);
		innerPanel.setBounds(25,275,675,400);
		
		itemDisplay();
	}

	public void addPanel(JFrame frame) {
		frame.add(panel);
	}

	public void removePanel(JFrame frame) {
		frame.remove(panel);
	}

	public void itemDisplay() {		
		int i = 2;
		int j = 6;
		JPanel[][] panelHolder = new JPanel[i][j];    
		innerPanel.setLayout(new GridLayout(i,j,0,0));

		JPanel captionAndTitle1 = new JPanel(new GridLayout(2,1));
		JPanel captionAndTitle2 = new JPanel(new GridLayout(2,1));
		JPanel captionAndTitle3 = new JPanel(new GridLayout(2,1));
		JPanel captionAndTitle4 = new JPanel(new GridLayout(2,1));
		JPanel captionAndTitle5 = new JPanel(new GridLayout(2,1));
		JPanel captionAndTitle6 = new JPanel(new GridLayout(2,1));

		JL1.setText(pi.getInventory().get(0).getTitle() + " - $" + pi.getInventory().get(0).getPrice());
		JTA1.setText(pi.getInventory().get(0).getDescription());
		JSP1 = new JScrollPane(JTA1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		captionAndTitle1.add(JL1); captionAndTitle1.add(JSP1);

		JL2.setText(pi.getInventory().get(1).getTitle() + " - $" + pi.getInventory().get(1).getPrice());
		JTA2.setText(pi.getInventory().get(1).getDescription());
		JSP2 = new JScrollPane(JTA2, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		captionAndTitle2.add(JL2); captionAndTitle2.add(JSP2);

		JL3.setText(pi.getInventory().get(2).getTitle() + " - $" + pi.getInventory().get(2).getPrice());
		JTA3.setText(pi.getInventory().get(2).getDescription());
		JSP3 = new JScrollPane(JTA3, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		captionAndTitle3.add(JL3); captionAndTitle3.add(JSP3);

		JL4.setText(pi.getInventory().get(3).getTitle() + " - $" + pi.getInventory().get(3).getPrice());
		JTA4.setText(pi.getInventory().get(3).getDescription());
		JSP4 = new JScrollPane(JTA4, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		captionAndTitle4.add(JL4); captionAndTitle4.add(JSP4);

		JL5.setText(pi.getInventory().get(4).getTitle() + " - $" + pi.getInventory().get(4).getPrice());
		JTA5.setText(pi.getInventory().get(4).getDescription());
		JSP5 = new JScrollPane(JTA5, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		captionAndTitle5.add(JL5); captionAndTitle5.add(JSP5);

		JL6.setText(pi.getInventory().get(5).getTitle() + " - $" + pi.getInventory().get(5).getPrice());
		JTA6.setText(pi.getInventory().get(5).getDescription());
		JSP6 = new JScrollPane(JTA6, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		captionAndTitle6.add(JL6); captionAndTitle6.add(JSP6);

		JTA1.setEditable(false);
		JTA2.setEditable(false);
		JTA3.setEditable(false);
		JTA4.setEditable(false);
		JTA5.setEditable(false);
		JTA6.setEditable(false);
		
		innerPanel.add(captionAndTitle1);
		innerPanel.add(caption1);
		innerPanel.add(captionAndTitle2);
		innerPanel.add(caption2);
		innerPanel.add(captionAndTitle3);
		innerPanel.add(caption3);
		innerPanel.add(captionAndTitle4);
		innerPanel.add(caption4);
		innerPanel.add(captionAndTitle5);
		innerPanel.add(caption5);
		innerPanel.add(captionAndTitle6);
		innerPanel.add(caption6);

		caption1.addActionListener(this);
		caption2.addActionListener(this);
		caption3.addActionListener(this);
		caption4.addActionListener(this);
		caption5.addActionListener(this);
		caption6.addActionListener(this);
	}

	public void updateBySearch(String text) {
		ArrayList<Item> al = pi.searchInventory(text);
		for(int i = 0; i < al.size(); i++) {
			System.out.println(al.get(i).getTitle());
		}
		if(al.size() > 6) {
			for(int i = 6; i < al.size(); i++) {
				al.remove(i);
			} 
		}
		
		// Remove Duplicates
		for(int i = 0; i < al.size(); i++) {
			int count = 0;
			Item temp = al.get(i);
			for(int k = i+1; k < al.size()-1-count; k++) {
				if(temp.getTitle().equals(al.get(k).getTitle())) {
					al.remove(k);
					count++;
				}
			}
		}
		
		if(al.size() == 1) {	
			innerPanel.removeAll();
			
			innerPanel.add(captionAndTitle1);
			innerPanel.add(caption1);
			
			JL1.setText(al.get(0).getTitle() + " - $" + al.get(0).getPrice());
			JTA1.setText(al.get(0).getDescription());
			JSP1 = new JScrollPane(JTA1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			caption1.addActionListener(this);
			
			JTA1.setEditable(false);
			
			Driver.refresh();
			SwingUtilities.updateComponentTreeUI(innerPanel);

		} else if (al.size() == 2) {
			JL1.setText(al.get(0).getTitle() + " - $" + al.get(0).getPrice());
			JTA1.setText(al.get(0).getDescription());
			JTA1.setEditable(false);
			JL2.setText(al.get(1).getTitle() + " - $" + al.get(1).getPrice());
			JTA2.setText(al.get(1).getDescription());
			JTA2.setEditable(false);
			innerPanel.remove(captionAndTitle3);
			caption3.removeActionListener(this);
			innerPanel.remove(caption3);
			innerPanel.remove(captionAndTitle4);
			caption4.removeActionListener(this);
			innerPanel.remove(caption4);
			innerPanel.remove(captionAndTitle5);
			caption5.removeActionListener(this);
			innerPanel.remove(caption5);
			innerPanel.remove(captionAndTitle6);
			caption6.removeActionListener(this);
			innerPanel.remove(caption6);
			Driver.refresh();
			SwingUtilities.updateComponentTreeUI(innerPanel);
		} else if (al.size() == 3) {
			JL1.setText(al.get(0).getTitle() + " - $" + al.get(0).getPrice());
			JTA1.setText(al.get(0).getDescription());
			JTA1.setEditable(false);
			JL2.setText(al.get(1).getTitle() + " - $" + al.get(1).getPrice());
			JTA2.setText(al.get(1).getDescription());
			JTA2.setEditable(false);
			JL3.setText(al.get(2).getTitle() + " - $" + al.get(2).getPrice());
			JTA3.setText(al.get(2).getDescription());
			innerPanel.remove(captionAndTitle4);
			caption4.removeActionListener(this);
			innerPanel.remove(caption4);
			innerPanel.remove(captionAndTitle5);
			caption5.removeActionListener(this);
			innerPanel.remove(caption5);
			innerPanel.remove(captionAndTitle6);
			caption6.removeActionListener(this);
			innerPanel.remove(caption6);
			Driver.refresh();
			SwingUtilities.updateComponentTreeUI(innerPanel);
		} else if(al.size() == 4) {
			JL1.setText(al.get(0).getTitle() + " - $" + al.get(0).getPrice());
			JTA1.setText(al.get(0).getDescription());
			JTA1.setEditable(false);
			JL2.setText(al.get(1).getTitle() + " - $" + al.get(1).getPrice());
			JTA2.setText(al.get(1).getDescription());
			JTA2.setEditable(false);
			JL3.setText(al.get(2).getTitle() + " - $" + al.get(2).getPrice());
			JTA3.setText(al.get(2).getDescription());
			JL4.setText(al.get(3).getTitle() + " - $" + al.get(3).getPrice());
			JTA4.setText(al.get(3).getTitle() + " - $" + al.get(4).getPrice());
			innerPanel.remove(captionAndTitle5);
			caption5.removeActionListener(this);
			innerPanel.remove(caption5);
			innerPanel.remove(captionAndTitle6);
			caption6.removeActionListener(this);
			innerPanel.remove(caption6);
			Driver.refresh();
			SwingUtilities.updateComponentTreeUI(innerPanel);
		} else if(al.size() == 5) {
			JL1.setText(al.get(0).getTitle() + " - $" + al.get(0).getPrice());
			JTA1.setText(al.get(0).getDescription());
			JL2.setText(al.get(1).getTitle() + " - $" + al.get(1).getPrice());
			JTA2.setText(al.get(1).getDescription());
			JL3.setText(al.get(2).getTitle() + " - $" + al.get(2).getPrice());
			JTA3.setText(al.get(2).getDescription());
			JL4.setText(al.get(3).getTitle() + " - $" + al.get(3).getPrice());
			JTA4.setText(al.get(3).getDescription());
			JL5.setText(al.get(4).getTitle() + " - $" + al.get(4).getPrice());
			JTA5.setText(al.get(4).getDescription());
			innerPanel.remove(captionAndTitle6);
			caption6.removeActionListener(this);
			innerPanel.remove(caption6);
			Driver.refresh();
			SwingUtilities.updateComponentTreeUI(innerPanel);
		} else if (al.size() == 6) {
			JL1.setText(al.get(0).getTitle() + " - $" + al.get(0).getPrice());
			JTA1.setText(al.get(0).getDescription());
			JL2.setText(al.get(1).getTitle() + " - $" + al.get(1).getPrice());
			JTA2.setText(al.get(1).getDescription());
			JL3.setText(al.get(2).getTitle() + " - $" + al.get(2).getPrice());
			JTA3.setText(al.get(2).getDescription());
			JL4.setText(al.get(3).getTitle() + " - $" + al.get(3).getPrice());
			JTA4.setText(al.get(3).getDescription());
			JL5.setText(al.get(4).getTitle() + " - $" + al.get(4).getPrice());
			JTA5.setText(al.get(4).getDescription());
			JL6.setText(al.get(5).getTitle() + " - $" + al.get(5).getPrice());
			JTA6.setText(al.get(5).getDescription());
			Driver.refresh();
			SwingUtilities.updateComponentTreeUI(innerPanel);
		}
	}

	public ArrayList<String> addNavigationByType(String username) {
		ArrayList<String> optionsAL = new ArrayList<>();
		if(PostgresAccounts.getAccount(Driver.getCurrentAccount()).getType() == 2 || 
				PostgresAccounts.getAccount(Driver.getCurrentAccount()).getType() ==  3) {
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
				choice.setSelectedIndex(1);
				Driver.newChoice(5);
			} else if (i == 2) {
				choice.setSelectedIndex(2);
				Driver.newChoice(4);
			}
		}
		if(e.getSource() == caption1) {
			Driver.shoppingCart.add(PostgresInventory.getItem(JL1.getText().substring(0,JL1.getText().indexOf(" - "))));
		} else if(e.getSource() == caption2) {
			Driver.shoppingCart.add(PostgresInventory.getItem(JL1.getText().substring(0,JL2.getText().indexOf(" - "))));
		} else if(e.getSource() == caption3) {
			Driver.shoppingCart.add(PostgresInventory.getItem(JL1.getText().substring(0,JL3.getText().indexOf(" - "))));
		} else if(e.getSource() == caption4) {
			Driver.shoppingCart.add(PostgresInventory.getItem(JL1.getText().substring(0,JL4.getText().indexOf(" - "))));
		} else if(e.getSource() == caption5) {
			Driver.shoppingCart.add(PostgresInventory.getItem(JL1.getText().substring(0,JL5.getText().indexOf(" - "))));
		} else if(e.getSource() == caption6) {
			Driver.shoppingCart.add(PostgresInventory.getItem(JL1.getText().substring(0,JL6.getText().indexOf(" - "))));
		}
		if(e.getSource() == checkout) {
			double totalPrice = 0;
			for(int i = 0; i < Driver.shoppingCart.size(); i++) {
				totalPrice += Driver.shoppingCart.get(i).getPrice();
			}
			JOptionPane.showMessageDialog(null, "Your order for " + Driver.shoppingCart.size()  + " items ($ " + totalPrice + " ) was completed successfully.", "Order Successful", JOptionPane.DEFAULT_OPTION);
		}
		if(e.getSource() == logout) {
			Driver.newChoice(0);
		}
		if(e.getSource() == search) {
			if(!(searchBar.getText().replaceAll("\\s+","").equals(""))) {
				updateBySearch(searchBar.getText());
				SwingUtilities.updateComponentTreeUI(innerPanel);	
			} else {
				itemDisplay();
			}
		}
	}
}
