import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI implements ActionListener {
	
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
		JFrame frame= new JFrame();
		frame.setSize(1200,850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		JLabel title = new JLabel(" Shop ");
		title.setBounds(400, 100, 1200, 100);
		panel.add(title);
		title.setFont(new Font("MV Boli", Font.BOLD,80));
		title.setForeground(Color.white);
		
		panel.setLayout(null);
		//
	
		
		//
		
		useNameLabel = new JLabel(" User Name ");
		useNameLabel.setBounds(360, 220, 100, 25);
		useNameLabel.setForeground(Color.white);
		panel.add(useNameLabel);
		
		userNameText = new JTextField(20);
		userNameText.setBounds(450, 220, 150, 25);
		panel.add(userNameText);
		//
		
		passwordLabel = new JLabel(" Password");
		passwordLabel.setBounds(360, 320, 100, 25);
		passwordLabel.setForeground(Color.white);
		panel.add(passwordLabel);
		
		passwordText = new JPasswordField(20);
		passwordText.setBounds(450, 320, 150, 25);
		panel.add(passwordText);
		//
		
		CreateAccount = new JButton("Create Account");
		CreateAccount.setBounds(350, 380, 150, 20);
		CreateAccount.addActionListener(new GUI());
		panel.add(CreateAccount);
		//
		LoginButton = new JButton("Login");
		LoginButton.setBounds(560, 380, 120, 20);
		LoginButton.addActionListener(new GUI());
		panel.add(LoginButton);
		//
		
		LoginSuccessful = new JLabel("");
		LoginSuccessful.setBounds(650, 340, 300, 20);
		panel.add(LoginSuccessful);
		
		JLabel img= new JLabel();
		img.setBounds(0, 0, 1200, 850);
		img.setIcon(image);
		panel.add(img);
		
		
		frame.setVisible(true);
	}
@Override
public void actionPerformed(ActionEvent e) {
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
	}

}
