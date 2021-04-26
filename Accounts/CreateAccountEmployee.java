package Accounts;
import DB.*;
import Shopping.ShoppingPage;
import main.Driver;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class CreateAccountEmployee implements ActionListener{
	//private static PostgresAccounts pAccount = new PostgresAccounts();
	private JLabel UseNameLabel;
	private JTextField UserNameText;
	private JLabel PasswordLabel;
	private JPasswordField PasswordText;
	private JLabel FirstName;
	private JTextField FirstNameText;
	private JLabel LastName;
	private JTextField LastNameText;
	private JLabel Email;
	private JTextField EmailText;
	private JComboBox choice;
	private JLabel position;
	private JComboBox positionChoice;
	private JButton CreateAccount;
	private int i=1;
	private JPanel panel;	
	private JComboBox navigation;
	private int ii=1;
	
	private PostgresAccounts pAccounts = new PostgresAccounts();

	public CreateAccountEmployee(){			
		panel= new JPanel();
		panel.setLayout(null);

		JLabel title = new JLabel(" Create Account ");
		title.setBounds(220, 150, 1200, 100);
		panel.add(title);
		title.setFont(new Font("Font", Font.BOLD,55));	

		String [] options= {"Customer","Employee"};
		choice = new JComboBox(options);
		choice.setSelectedIndex(1);
		panel.add(choice);
		choice.setBounds(800, 400, 300, 25);
		choice.addActionListener(this);



		//

		UseNameLabel = new JLabel(" User Name:");
		UseNameLabel.setBounds(200, 300, 200, 25);
		UseNameLabel.setFont(new Font("Font", Font.BOLD,20));
		panel.add(UseNameLabel);

		UserNameText = new JTextField(20);
		UserNameText.setBounds(350, 300, 250, 25);
		panel.add(UserNameText);


		//
		PasswordLabel = new JLabel(" Password: ");
		PasswordLabel.setBounds(200, 350, 200, 25);
		PasswordLabel.setFont(new Font("Font", Font.BOLD,20));
		panel.add(PasswordLabel);

		PasswordText = new JPasswordField(20);
		PasswordText.setBounds(350, 350, 250, 25);
		panel.add(PasswordText);
		//

		FirstName = new JLabel(" First Name:");
		FirstName.setBounds(200, 400, 250, 25);
		FirstName.setFont(new Font("Font", Font.BOLD,20));
		panel.add(FirstName);

		FirstNameText = new JTextField(20);
		FirstNameText.setBounds(350, 400, 250, 25);
		panel.add(FirstNameText);
		//
		LastName = new JLabel(" Last Name:");
		LastName.setBounds(200, 450, 250, 25);
		LastName.setFont(new Font("Font", Font.BOLD,20));
		panel.add(LastName);

		LastNameText = new JTextField(20);
		LastNameText.setBounds(350, 450, 250, 25);
		panel.add(LastNameText);
		//
		Email = new JLabel(" Email: ");
		Email.setBounds(200, 500, 250, 25);
		Email.setFont(new Font("Font", Font.BOLD,20));
		panel.add(Email);

		EmailText = new JTextField(20);
		EmailText.setBounds(350, 500, 250, 25);
		panel.add(EmailText);
		//
		position = new JLabel(" Position: ");
		position.setBounds(200, 550, 250, 25);
		position.setFont(new Font("Font", Font.BOLD,20));
		panel.add(position);
		//

		String [] position= {"Employee","Manager"};
		positionChoice = new JComboBox(position);
		panel.add(positionChoice);
		positionChoice.setBounds(350, 550, 250, 25);
		panel.setLayout(null);
		//
		CreateAccount = new JButton("CreateAccount");
		CreateAccount.setBounds(650, 600, 160, 40);
		CreateAccount.addActionListener(this);
		panel.add(CreateAccount);
		//
		String [] navigations= {"Login Page","Create Account Page"};
		navigation = new JComboBox(navigations);
		navigation.setSelectedIndex(1);
		panel.add(navigation);
		navigation.setBounds(10, 10, 150, 25);
		navigation.addActionListener(this);
	}


	public void addPanel(JFrame frame) {
		frame.add(panel);

	}

	public void removePanel(JFrame frame) {
		frame.remove(panel);


	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==choice) {
			i=choice.getSelectedIndex();

			if(i==0) {
				Driver.newChoice(1);
			}
		}

		if(e.getSource()==CreateAccount) {
			String name = FirstNameText.getText() + " " + LastNameText.getText();
			int position;
			if (positionChoice.getSelectedItem().equals("Employee")) {
				position = 2;
			} else {
				position = 3;
			}
			pAccounts.addAccount(new Account(position, name, UserNameText.getText(), PasswordText.getText(), EmailText.getText()));
			Driver.newChoice(3);
		}
		ii=navigation.getSelectedIndex();
		switch (ii){
		case 0:
			Driver.newChoice(0);
			break;

		case 1:

			break;

		case 2:
			Driver.newChoice(3);
			break;
		}

	}
}

