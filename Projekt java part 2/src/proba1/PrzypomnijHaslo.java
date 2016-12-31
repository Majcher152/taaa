package proba1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class PrzypomnijHaslo {

	private JFrame frame;
	private JTextField textField;
	private JTextArea textArea;

	public PrzypomnijHaslo() {

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
	 * asdddddd
	 * asd
	 * asd
	 * asd
	 * a
	 * d
	 * asd
	 * asd
	 * a
	 * aa
	 * aaaaaa
	 * a
	 * a
	 * 
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MyActionListener myAction = new MyActionListener();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JButton btnPowrot = new JButton("Powrot");
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
			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "Szukaj":
				String login = textField.getText();
				textField.setText("");
				textArea.setText(login);
				break;
			case "Powrot":
				frame.setVisible(false);
				new StronaGlowna();
				break;
			}
		}
	}
}
