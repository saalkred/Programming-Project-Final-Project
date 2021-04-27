package Accounts;
import DB.*;
import main.Driver;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.util.ArrayList;



public class Login implements ActionListener {
	private static JLabel useNameLabel;
	private static JTextField userNameText;
	private static JLabel passwordLabel;
	private static JPasswordField passwordText;
	private static JButton LoginButton;
	private static JButton CreateAccount;
	private static JLabel LoginSuccessful;
	private static JPanel panel;	
	private JComboBox choice;
	private static PostgresAccounts pAccount = new PostgresAccounts();
	private static PostgresPending pPending = new PostgresPending();


	public Login() {
		panel= new JPanel();

		JLabel title = new JLabel(" Clothing Shop ");
		title.setBounds(220, 150, 1200, 100);
		panel.add(title);
		title.setFont(new Font( null, Font.BOLD,60));
		title.setForeground(Color.white);

		panel.setLayout(null);

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

		//

		useNameLabel = new JLabel(" User Name:");
		useNameLabel.setBounds(200, 300, 200, 25);
		useNameLabel.setFont(new Font( "null", Font.BOLD,20));
		useNameLabel.setForeground(Color.white);
		panel.add(useNameLabel);

		userNameText = new JTextField(20);
		userNameText.setBounds(350, 300, 250, 25);
		panel.add(userNameText);
		//

		passwordLabel = new JLabel(" Password:");
		passwordLabel.setBounds(200, 400, 200, 25);
		passwordLabel.setFont(new Font( "null", Font.BOLD,20));
		passwordLabel.setForeground(Color.white);
		panel.add(passwordLabel);

		passwordText = new JPasswordField(20);
		passwordText.setBounds(350, 400, 250, 25);
		panel.add(passwordText);
		//

		CreateAccount = new JButton("Create Account");
		CreateAccount.setBounds(250, 480, 160, 20);
		panel.add(CreateAccount);
		CreateAccount.addActionListener(this);
		//
		LoginButton = new JButton("Login");
		LoginButton.setBounds(500, 480, 160, 20);
		LoginButton.addActionListener(this);
		panel.add(LoginButton);
		//

		LoginSuccessful = new JLabel("");
		LoginSuccessful.setBounds(650, 340, 300, 20);
		panel.add(LoginSuccessful);
		//

		JLabel backgrounnd = new JLabel(); 
		backgrounnd.setOpaque(true);
		backgrounnd.setBackground(Color.DARK_GRAY);
		backgrounnd.setBounds (0, 0, 1200, 850);
		panel.add(backgrounnd);

	}

	public void addPanel(JFrame frame) {
		choice.setSelectedIndex(0);
		frame.add(panel);
	}

	public void removePanel(JFrame frame) {
		frame.remove(panel);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==CreateAccount) {
			Driver.newChoice(1);
		}
		
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
					if (pAccount.getAccount(Driver.getCurrentAccount()).getType() == 3) {
						Driver.newChoice(5);
					} else {
						choice.setSelectedIndex(0);
					}
					break;
				case 3:
					if (pAccount.getAccount(Driver.getCurrentAccount()).getType() == 3) {
						Driver.newChoice(4);
					} else {
						choice.setSelectedIndex(0);
					}
					break;
			}
		}

		String user = userNameText.getText();
		@SuppressWarnings("deprecation")
		String password = passwordText.getText();


		if (e.getSource()== LoginButton) {
			if (pAccount.accountExists(user) && pAccount.getAccount(user).getPassword().equals(password)) {
				JOptionPane.showMessageDialog(null, "Login Successful", "Login", JOptionPane.PLAIN_MESSAGE );
				if (pPending.orderExists(user)) {
					Driver.shoppingCart = pPending.getOrder(user);
					pPending.deleteOrder(user);
				}
				Driver.setCurrentAccount(user);
				Driver.newChoice(3);

			} else {
				JOptionPane.showMessageDialog(null, "Wrong user name or password", "Login", JOptionPane.ERROR_MESSAGE);
			}

		}
	}
}



