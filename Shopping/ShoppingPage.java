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

import Accounts.Login;

public class ShoppingPage implements ActionListener {

	private JFrame frame;
	private JPanel panel;
	private JButton checkout, logout;
	private JComboBox choice, search;
	
	public ShoppingPage() {
		panel= new JPanel();
		frame= new JFrame();
		frame.setSize(1200,850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		JLabel title = new JLabel("Shopping");
		title.setBounds(500, 10, 1200, 100);
		panel.add(title);
		title.setFont(new Font(null, Font.BOLD,55));
		
		ArrayList<String> optionsAL = new ArrayList<>();
		if() {
			
		}
		choice = new JComboBox(options);
		panel.add(choice);
		choice.setBounds(100, 50, 300, 25);
		choice.addActionListener(this);
		panel.setLayout(null);
		
		logout = new JButton("Log Out ->");
		panel.add(logout);
		logout.setBounds(1000, 50, 100, 25);
		logout.addActionListener(this);
		
		checkout = new JButton("Checkout");
		panel.add(checkout);
		checkout.setBounds(800, 700, 300, 25);
		checkout.addActionListener(this);
		
		frame.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == checkout) {
			JOptionPane.showMessageDialog(null, "Your order for x items ($y) was completed successfully.", "Order Successful", JOptionPane.DEFAULT_OPTION);
		}
		if(e.getSource() == logout) { // Log out the user
			frame.dispose();
			Login restart = new Login(); // TODO: Fix!
		}
		
	}

}