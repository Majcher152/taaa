package proba1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.JPasswordField;

public class Logowanie {

	private JFrame frame;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private Socket socket = null;
	private boolean flag = true;
	private PrintWriter output = null;
	private BufferedReader input = null;

	/**
	 * Create the application.
	 */
	public Logowanie(Socket socket) {
		this.socket = socket;
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
			ustawienieInputOutput();
			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "Zaloguj":
				String login = textField_1.getText();
				String haslo1 = passwordField.getText();
				if (sprawdzanieHasla(login, haslo1)) {
					new ZalogujKurier(login, socket);
					frame.setVisible(false);
					break;
				} else {
					JOptionPane.showMessageDialog(null, "Niepoprawny login lub haslo");
					break;
				}
			case "Przypomnij haslo":
				new PrzypomnijHaslo(socket);
				frame.setVisible(false);
				break;
			case "Powrot":
				String mess = "Powrot";
				io(output, input, mess);
				frame.setVisible(false);
				new StronaGlowna(socket);
				break;
			}
		}

		private void ustawienieInputOutput() {
			try {
				output = new PrintWriter(socket.getOutputStream(), true);
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

		private void io(PrintWriter output, BufferedReader input, String mess) {
			flag = true;
			output.println(mess);
			output.flush();
			while (flag) {
				try {
					if (input.ready()) {
						String aaa = input.readLine();
						System.out.println(aaa);
						flag = false;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("5");
					e1.printStackTrace();
				}
			}
		}

		private boolean sprawdzanieHasla(String login, String haslo) {
			output.println("ZalogujKurier");
			output.flush();
			output.println(login);
			output.flush();
			output.println(haslo);
			output.flush();
			boolean flagHaslo = true;
			while (flagHaslo) {
				try {
					if (input.ready()) {
						String loginHaslo = input.readLine();
						System.out.println("Haslo " + loginHaslo);
						if (loginHaslo.contains("Poprawne"))
							return true;
						else
							return false;

					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("5");
					e1.printStackTrace();
				}
			}
			return false;
		}

		private void nieWiem() {
			try {
				output = new PrintWriter(socket.getOutputStream(), true);
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output.println("ZalogujKurier");
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (flag) {
				try {
					if (input.ready()) {
						String aaa = input.readLine();
						System.out.println(aaa);
						flag = false;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("5");
					e1.printStackTrace();
				}
			}
		}
	}

}
