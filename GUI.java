import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JPanel panel= new JPanel();
		JFrame frame= new JFrame();
		frame.setSize(400,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);


		panel.setLayout(null);
		
		JLabel label = new JLabel(" User Name");
		label.setBounds(150, 90, 100, 25);
		panel.add(label);
		
		JTextField userName = new JTextField(20);
		userName.setBounds(115, 120, 150, 25);
		panel.add(userName);
		
		
		frame.setVisible(true);
	}

}
