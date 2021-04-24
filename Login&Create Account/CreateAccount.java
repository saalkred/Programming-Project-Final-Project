import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateAccount {

	private static JFrame frame;
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
	private JLabel Address;
	private JTextField AddressText;
	private static JButton LoginButton;
	private static JButton CreateAccount;
	private static JLabel LoginSuccessful;
		
		CreateAccount(){			
		JPanel panel= new JPanel();
		frame= new JFrame();
		frame.setSize(1200,850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

				JLabel title = new JLabel(" Create Account ");
				title.setBounds(220, 150, 1200, 100);
				panel.add(title);
				title.setFont(new Font( null, Font.BOLD,55));
				
				
				panel.setLayout(null);
				//
			
				
				//
				
				UseNameLabel = new JLabel(" User Name:");
				UseNameLabel.setBounds(200, 300, 200, 25);
				UseNameLabel.setFont(new Font( null, Font.BOLD,20));
				panel.add(UseNameLabel);
				
				UserNameText = new JTextField(20);
				UserNameText.setBounds(350, 300, 250, 25);
				panel.add(UserNameText);
				//
				PasswordLabel = new JLabel(" Password: ");
				PasswordLabel.setBounds(200, 350, 200, 25);
				PasswordLabel.setFont(new Font( null, Font.BOLD,20));
				panel.add(PasswordLabel);
				
				PasswordText = new JPasswordField(20);
				PasswordText.setBounds(350, 350, 250, 25);
				panel.add(PasswordText);
				//
				
				FirstName = new JLabel(" First Name:");
				FirstName.setBounds(200, 400, 250, 25);
				FirstName.setFont(new Font( null, Font.BOLD,20));
				panel.add(FirstName);
				
				FirstNameText = new JTextField(20);
				FirstNameText.setBounds(350, 400, 250, 25);
				panel.add(FirstNameText);
				//
				LastName = new JLabel(" Last Name:");
				LastName.setBounds(200, 450, 250, 25);
				LastName.setFont(new Font( null, Font.BOLD,20));
				panel.add(LastName);
				
				LastNameText = new JTextField(20);
				LastNameText.setBounds(350, 450, 250, 25);
				panel.add(LastNameText);
				//
				Email = new JLabel(" Email: ");
				Email.setBounds(200, 500, 250, 25);
				Email.setFont(new Font( null, Font.BOLD,20));
				panel.add(Email);
				
				EmailText = new JTextField(20);
				EmailText.setBounds(350, 500, 250, 25);
				panel.add(EmailText);
				//
				Address = new JLabel(" Address: ");
				Address.setBounds(200, 550, 250, 25);
				Address.setFont(new Font( null, Font.BOLD,20));
				panel.add(Address);
				
				AddressText = new JTextField(20);
				AddressText.setBounds(350, 550, 250, 25);
				panel.add(AddressText);
				
				frame.setVisible(true);
				/*
				CreateAccount = new JButton("Create Account");
				CreateAccount.setBounds(250, 480, 160, 20);
				panel.add(CreateAccount);
				CreateAccount.addActionListener(new Login());
				//
				LoginButton = new JButton("Login");
				LoginButton.setBounds(500, 480, 160, 20);
				LoginButton.addActionListener(new Login());
				panel.add(LoginButton);
				//
				
				LoginSuccessful = new JLabel("");
				LoginSuccessful.setBounds(650, 340, 300, 20);
				panel.add(LoginSuccessful);
				
	
				
				
			
	/*	@Override
	
	 * public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String user = userNameText.getText();
			@SuppressWarnings("deprecation")
			String password = passwordText.getText();
			
			
			if (user.equals("boo") && password.equals("boos")) {
				LoginSuccessful.setText(" Login Successful ");
				LoginSuccessful.setForeground(Color.white);
			}else {
				LoginSuccessful.setText(" Wrong user name or password ");
				LoginSuccessful.setForeground(Color.white);
				}
			
			if(e.getSource()==CreateAccount) {
				frame.dispose();
				CreateAccount createAccounts = new CreateAccount();
			}
			
			}
			*/

		
		
		
		}
	}

