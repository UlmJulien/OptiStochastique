package IHM;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import Fiches.MonitorDirectory;
import Security.ClientSession;
import Users.User;
import Users.UserReader;

import java.awt.Font;
import java.awt.Color;
import java.util.List;

public class AuthFrame {

	private JFrame frame;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private List<User> userList;
	private ClientSession session = null;

	/**
	 * Create the application.
	 */
	public AuthFrame(List<User> usrs) {
		userList = usrs;
		initialize();
	}
	
	public void run() {
		try {
			AuthFrame window = new AuthFrame(userList);
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean accountCheck(String fullString){
		String key = UserReader.truncPort(fullString);
		for(int i=0; i<userList.size();i++){
			System.out.println(userList.get(i).getPort() + " : " +key);
			String dbCurrentIp = userList.get(i).getPort();
			if(dbCurrentIp.compareTo(key)==0) return true;
		}
		return false;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		session = new ClientSession();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField_1 = new JTextField();
		textField_1.setBounds(188, 101, 150, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(188, 142, 150, 19);
		frame.getContentPane().add(passwordField);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(57, 144, 70, 15);
		frame.getContentPane().add(lblPassword);
		
		final JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(89, 103, 70, 15);
		frame.getContentPane().add(lblLogin);
		
		final Button getFilesButton = new Button("Get Files");
		getFilesButton.setVisible(false);
		getFilesButton.setBounds(252, 205, 86, 23);
		frame.getContentPane().add(getFilesButton);
		final JLabel lblNewLabel = new JLabel("Error login/pw");
		lblNewLabel.setVisible(false);
		
		final Button signInButton = new Button("Sign in");
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(accountCheck(textField_1.getText())){
					signInButton.setVisible(false);
					getFilesButton.setVisible(true);
					session = new ClientSession(UserReader.truncPort(textField_1.getText()));
					session.print();
				}
				else lblNewLabel.setVisible(true);
			}
		});
		signInButton.setBounds(252, 205, 86, 23);
		frame.getContentPane().add(signInButton);
		
		getFilesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MonitorDirectory md = new MonitorDirectory();
				md.checkChanges();
				
				session.print();
			}
		});
		
		JLabel lblMedicalFilesOf = new JLabel("MEDICAL FILES PROJECT");
		lblMedicalFilesOf.setFont(new Font("Dialog", Font.BOLD, 18));
		lblMedicalFilesOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedicalFilesOf.setBounds(12, 23, 424, 15);
		frame.getContentPane().add(lblMedicalFilesOf);
		
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(188, 173, 150, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnMenu.add(mntmQuit);
		
		
	}
}
