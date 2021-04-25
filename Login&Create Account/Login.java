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



public class Login implements ActionListener {
	private static JFrame frame;
	private static JLabel useNameLabel;
	private static JTextField userNameText;
	private static JLabel passwordLabel;
	private static JPasswordField passwordText;
	private static JButton LoginButton;
	private static JButton CreateAccount;
	private static JLabel LoginSuccessful;
	

	public static void main(String[] args) {
		ImageIcon image= new ImageIcon("ss.png"); //setting an image 
		
		JPanel panel= new JPanel();
		frame= new JFrame();
		frame.setSize(1200,850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		JLabel title = new JLabel(" Clothing Shop ");
		title.setBounds(220, 150, 1200, 100);
		panel.add(title);
		title.setFont(new Font( null, Font.BOLD,60));
		title.setForeground(Color.white);
		
		panel.setLayout(null);
		//
	
		
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
		//
		
		JLabel img= new JLabel();
		img.setBounds(0, 0, 1200, 850);
		img.setIcon(image);
		panel.add(img);
		
		
		frame.setVisible(true);
	}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
	
	if(e.getSource()==CreateAccount) {
		frame.dispose();
		CreateAccountCustomer createAccounts = new CreateAccountCustomer();
	}
	
	String user = userNameText.getText();
	@SuppressWarnings("deprecation")
	String password = passwordText.getText();
	
	
	if (e.getSource()== LoginButton) {
			if (user.equals("boo") && password.equals("boos")) {
		JOptionPane.showMessageDialog(null, "Login Successful", "Login", JOptionPane.PLAIN_MESSAGE );
		frame.dispose();
			
	} else {
		JOptionPane.showMessageDialog(null, "Wrong user name or password", "Login", JOptionPane.ERROR_MESSAGE);
		}
	}
}
	}


