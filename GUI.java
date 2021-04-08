import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private static JLabel LoginSuccessful;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JPanel panel= new JPanel();
		JFrame frame= new JFrame();
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);


		panel.setLayout(null);
		//
		
		useNameLabel = new JLabel(" User Name ");
		useNameLabel.setBounds(150, 90, 100, 25);
		panel.add(useNameLabel);
		
		userNameText = new JTextField(20);
		userNameText.setBounds(115, 120, 150, 25);
		panel.add(userNameText);
		//
		
		passwordLabel = new JLabel(" Password");
		passwordLabel.setBounds(150, 170, 100, 25);
		panel.add(passwordLabel);
		
		passwordText = new JPasswordField(20);
		passwordText.setBounds(115, 200, 150, 25);
		panel.add(passwordText);
		//
		
		LoginButton = new JButton("Login");
		LoginButton.setBounds(240, 240, 70, 20);
		LoginButton.addActionListener(new GUI());
		panel.add(LoginButton);
		//
		
		LoginSuccessful = new JLabel("");
		LoginSuccessful.setBounds(180, 300, 300, 20);
		panel.add(LoginSuccessful);
		
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String user = userNameText.getText();
		@SuppressWarnings("deprecation")
		String password = passwordText.getText();
		
		
		if (user.equals("boo") && password.equals("boobs")) {
			LoginSuccessful.setText(" Login Successful ");
		}else {
			LoginSuccessful.setText(" Wrong user name or password ");
			}
		}
		
	

}
