package proba1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.JPasswordField;

public class Logowanie {

	private JFrame frame;
	private JTextField textField_1;
	private JPasswordField passwordField;

	/**
	 * Create the application.
	 */
	public Logowanie() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					initialize();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MyActionListener myAction = new MyActionListener();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JLabel lblLogin = new JLabel("Login");
		springLayout.putConstraint(SpringLayout.NORTH, lblLogin, 44, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblLogin, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblLogin, 53, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblLogin);

		textField_1 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField_1, -3, SpringLayout.NORTH, lblLogin);
		springLayout.putConstraint(SpringLayout.WEST, textField_1, 6, SpringLayout.EAST, lblLogin);
		springLayout.putConstraint(SpringLayout.EAST, textField_1, -153, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblHaslo = new JLabel("Haslo");
		springLayout.putConstraint(SpringLayout.NORTH, lblHaslo, 23, SpringLayout.SOUTH, lblLogin);
		springLayout.putConstraint(SpringLayout.WEST, lblHaslo, 0, SpringLayout.WEST, lblLogin);
		frame.getContentPane().add(lblHaslo);

		JButton btnNewButton = new JButton("Przypomnij haslo");
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(myAction);

		JButton btnPowrot = new JButton("Powrot");
		springLayout.putConstraint(SpringLayout.WEST, btnPowrot, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnPowrot, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnPowrot);
		btnPowrot.addActionListener(myAction);

		JButton btnZaloguj = new JButton("Zaloguj");
		springLayout.putConstraint(SpringLayout.NORTH, btnZaloguj, 23, SpringLayout.SOUTH, lblHaslo);
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 0, SpringLayout.NORTH, btnZaloguj);
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 25, SpringLayout.EAST, btnZaloguj);
		springLayout.putConstraint(SpringLayout.WEST, btnZaloguj, 0, SpringLayout.WEST, lblLogin);
		frame.getContentPane().add(btnZaloguj);
		
		passwordField = new JPasswordField();
		springLayout.putConstraint(SpringLayout.NORTH, passwordField, -3, SpringLayout.NORTH, lblHaslo);
		springLayout.putConstraint(SpringLayout.WEST, passwordField, 0, SpringLayout.WEST, textField_1);
		springLayout.putConstraint(SpringLayout.EAST, passwordField, 0, SpringLayout.EAST, textField_1);
		frame.getContentPane().add(passwordField);
		btnZaloguj.addActionListener(myAction);
	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
				String co = ((JButton) e.getSource()).getText();
				switch (co) {
				case "Zaloguj":
					String login = textField_1.getText();
					String haslo1 = passwordField.getText();
					new ZalogujKurier(login,haslo1);
					frame.setVisible(false);
					break;
				case "Przypomnij haslo":
					new PrzypomnijHaslo();
					frame.setVisible(false);
					break;
				case "Powrot":
					frame.setVisible(false);
					new StronaGlowna();
					break;
				}
			}
	}
}
