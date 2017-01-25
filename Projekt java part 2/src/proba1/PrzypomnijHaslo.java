package proba1;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class PrzypomnijHaslo {

	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea;
	private PrintWriter output = null;
	private BufferedReader input = null;
	private Socket socket = null;
	private boolean flag = true;

	public PrzypomnijHaslo(Socket socket) {
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
		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JButton btnPowrot = new JButton("Powrot");
		btnPowrot.setBackground(new Color(255, 255, 255));
		springLayout.putConstraint(SpringLayout.WEST, btnPowrot, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnPowrot, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnPowrot);

		JLabel lblPodajSwojLogin = new JLabel("Podaj swoj login:");
		springLayout.putConstraint(SpringLayout.NORTH, lblPodajSwojLogin, 20, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPodajSwojLogin, 0, SpringLayout.WEST, btnPowrot);
		frame.getContentPane().add(lblPodajSwojLogin);

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, lblPodajSwojLogin);
		springLayout.putConstraint(SpringLayout.WEST, textField, 26, SpringLayout.EAST, lblPodajSwojLogin);
		springLayout.putConstraint(SpringLayout.EAST, textField, 333, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnSzukaj = new JButton("Szukaj");
		btnSzukaj.setBackground(new Color(255, 255, 255));
		springLayout.putConstraint(SpringLayout.NORTH, btnSzukaj, 19, SpringLayout.SOUTH, lblPodajSwojLogin);
		springLayout.putConstraint(SpringLayout.WEST, btnSzukaj, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnSzukaj);

		JLabel lblPodpowiedz = new JLabel("Podpowiedz:");
		springLayout.putConstraint(SpringLayout.NORTH, lblPodpowiedz, 23, SpringLayout.SOUTH, btnSzukaj);
		springLayout.putConstraint(SpringLayout.WEST, lblPodpowiedz, 0, SpringLayout.WEST, btnPowrot);
		frame.getContentPane().add(lblPodpowiedz);

		textArea = new JTextArea();
		textArea.setEditable(false);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 62, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 16, SpringLayout.EAST, lblPodpowiedz);
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, 142, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, textArea, 338, SpringLayout.EAST, lblPodpowiedz);
		frame.getContentPane().add(textArea);

		btnSzukaj.addActionListener(myAction);
		btnPowrot.addActionListener(myAction);
	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ustawienieInputOutput();
			String co = ((JButton) e.getSource()).getText();
			String mess = null;
			switch (co) {
			case "Szukaj":
				mess = "PrzypomnijHaslo";
				io(output, input, mess);
				String login = textField.getText();
				output.println(login);
				output.flush();
				flag = true;
				while (flag) {
					try {
						if (input.ready()) {
							String aaa = input.readLine();
							if (aaa.equals("")) {
								JOptionPane.showMessageDialog(null, "Nie ma takiego loginu.");
							}
							textField.setText("");
							textArea.setText(aaa);
							flag = false;
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("5");
						e1.printStackTrace();
					}
				}

				break;
			case "Powrot":
				mess = "Powrot";
				io(output, input, mess);
				frame.setVisible(false);
				new Logowanie(socket);
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
	}
}
