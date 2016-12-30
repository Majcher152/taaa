package proba1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class ZnajdzSwojaPaczke {

	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea_2;

	/**
	 * Create the application.
	 */
	public ZnajdzSwojaPaczke() {
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
		springLayout.putConstraint(SpringLayout.NORTH, lblPodajNumerPaczki, 22, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPodajNumerPaczki, 165, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblPodajNumerPaczki);
		
		JButton btnNewButton = new JButton("Wyszukaj");

		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, -1, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -21, SpringLayout.WEST, textField);
		frame.getContentPane().add(btnNewButton);
		
		 textArea_2 = new JTextArea();
		 springLayout.putConstraint(SpringLayout.NORTH, textArea_2, 37, SpringLayout.SOUTH, btnNewButton);
		textArea_2.setEditable(false);
		springLayout.putConstraint(SpringLayout.WEST, textArea_2, 35, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textArea_2, -35, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(textArea_2);
		
		JButton btnPowrot = new JButton("Powrot");
		springLayout.putConstraint(SpringLayout.SOUTH, textArea_2, -4, SpringLayout.NORTH, btnPowrot);
		springLayout.putConstraint(SpringLayout.WEST, btnPowrot, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnPowrot, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnPowrot);
		btnPowrot.addActionListener(myAction);
		btnNewButton.addActionListener(myAction);

	}

	final class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "Wyszukaj":
				String numer;
				textArea_2.setText("");
				 numer = textField.getText();
				 textField.setText("");
				 //###################tutaj musi byc nowa metoda gdzie bêdziemy w bazie sprawdzac ten numer 
				 //i wyswietlac komunikaty bledów
				 textArea_2.setText(numer);
				break;
			case "Powrot":
				frame.setVisible(false);
				new StronaGlowna();
				break;
			}
		}
	}
}
