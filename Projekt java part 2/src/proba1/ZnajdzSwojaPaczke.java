package proba1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ZnajdzSwojaPaczke {

	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea_2;
	private PrintWriter output = null;
	private BufferedReader input = null;
	private Socket socket = null;
	private boolean flag = true;

	/**
	 * Create the application.
	 */
	public ZnajdzSwojaPaczke(Socket socket) {
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

		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField, 135, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -46, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblPodajNumerPaczki = new JLabel("Podaj numer paczki");
		springLayout.putConstraint(SpringLayout.NORTH, textField, 19, SpringLayout.SOUTH, lblPodajNumerPaczki);
		springLayout.putConstraint(SpringLayout.NORTH, lblPodajNumerPaczki, 22, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPodajNumerPaczki, 165, SpringLayout.WEST,
				frame.getContentPane());
		frame.getContentPane().add(lblPodajNumerPaczki);

		JButton btnNewButton = new JButton("Wyszukaj");

		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, -1, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -21, SpringLayout.WEST, textField);
		frame.getContentPane().add(btnNewButton);

		JButton btnPowrot = new JButton("Powrot");
		springLayout.putConstraint(SpringLayout.WEST, btnPowrot, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnPowrot, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnPowrot);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 21, SpringLayout.SOUTH, btnNewButton);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 37, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -6, SpringLayout.NORTH, btnPowrot);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 392, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		
				textArea_2 = new JTextArea();
				scrollPane.setViewportView(textArea_2);
				springLayout.putConstraint(SpringLayout.NORTH, textArea_2, 104, SpringLayout.SOUTH, btnNewButton);
				springLayout.putConstraint(SpringLayout.WEST, textArea_2, 8, SpringLayout.WEST, btnNewButton);
				textArea_2.setEditable(false);
				springLayout.putConstraint(SpringLayout.EAST, textArea_2, -35, SpringLayout.EAST, frame.getContentPane());
				springLayout.putConstraint(SpringLayout.SOUTH, textArea_2, -4, SpringLayout.NORTH, btnPowrot);
		btnPowrot.addActionListener(myAction);
		btnNewButton.addActionListener(myAction);
	}

	final class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ustawienieInputOutput();
			String mess = null;
			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "Wyszukaj":
				mess = "ZnajdzSwojaPaczke";
				io(output, input, mess);
				String numer;
				textArea_2.setText("");
				numer = textField.getText();
				textField.setText("");
				output.println(numer);
				output.flush();
				try {
					Paczka p = null;
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					p = (Paczka) ois.readObject();
					String paczkaWyswietlanie = p.toString();
					textArea_2.setText(p.toString());
				} catch (IOException | ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case "Powrot":
				mess = "Powrot";
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
	}
}
