package proba1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

import baza.PACZKA;

public class WyswietlaniePaczkiKurierDoOdebrania {

	private JFrame frame;
	private PACZKA paczkaDoWyswietlenia;
	private BufferedReader input = null;
	private PrintWriter output = null;
	private Socket socket = null;
	private boolean komunikat = false;
	private boolean powrotDoZalogujkurier = false;
	private JButton btnNewButton;

	/**
	 * Create the application.
	 */
	public WyswietlaniePaczkiKurierDoOdebrania(String login, Socket socket, PACZKA paczkaDoWyswietlenia) {
		this.socket = socket;
		this.paczkaDoWyswietlenia = paczkaDoWyswietlenia;
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
		frame.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
			}

			public void windowLostFocus(WindowEvent arg0) {
				if (komunikat) {

				} else if (powrotDoZalogujkurier) {

				} else {
					JOptionPane.showMessageDialog(null, "Najpierw zamknij!");
					frame.show();
				}
			}
		});

		frame.setBounds(100, 100, 450, 353);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblNewLabel = new JLabel(paczkaDoWyswietlenia.getidPaczki() + "");

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblZmienStan = new JLabel("Zmien stan:");

		JButton btnPowrot = new JButton("Zamknij");
		btnPowrot.addActionListener(myAction);

		btnNewButton = new JButton("Do dostarczenia");
		btnNewButton.addActionListener(myAction);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING,
										groupLayout.createSequentialGroup()
												.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 407,
														GroupLayout.PREFERRED_SIZE)
												.addContainerGap())
								.addGroup(Alignment.TRAILING,
										groupLayout.createSequentialGroup().addComponent(lblNewLabel)
												.addPreferredGap(ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
												.addComponent(btnPowrot).addContainerGap())
								.addGroup(groupLayout.createSequentialGroup().addComponent(lblZmienStan)
										.addContainerGap(368, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
										.addGap(251)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(btnPowrot))
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(lblZmienStan).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnNewButton).addContainerGap(94, Short.MAX_VALUE)));

		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		frame.getContentPane().setLayout(groupLayout);
		textArea.setText(paczkaDoWyswietlenia.toString());

	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ustawienieInputOutput();
			String mess;
			String co = ((JButton) e.getSource()).getText();
			int tmp;
			switch (co) {
			case "Do dostarczenia":
				
				komunikat = true;
				tmp = JOptionPane.showConfirmDialog(null, "Na pewno?");
				if (tmp == JOptionPane.YES_OPTION) {
					btnNewButton.setEnabled(false);
					mess = "Do dostarczenia";
					output.println(mess);
					output.flush();
					output.println(paczkaDoWyswietlenia.getidPaczki());
					output.flush();
				}
				komunikat = false;
				break;
			case "Zamknij":
				powrotDoZalogujkurier = true;
				frame.setVisible(false);
				break;
			}
		}

		private void ustawienieInputOutput() {
			try {
				output = new PrintWriter(socket.getOutputStream(), true);
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
}
