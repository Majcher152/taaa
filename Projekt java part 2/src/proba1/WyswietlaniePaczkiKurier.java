package proba1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import baza.PACZKA;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
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
import java.awt.event.WindowAdapter;
import javax.swing.JOptionPane;

public class WyswietlaniePaczkiKurier {

	private JFrame frame;
	private PACZKA paczkaDoWyswietlenia;
	private BufferedReader input = null;
	private PrintWriter output = null;
	private Socket socket = null;
	private boolean komunikat = false;
	private boolean powrotDoZalogujkurier = false;
	private JButton btnNewButton;
	private JButton btnWDrodzeDo;
	private JButton btnPrzekazanaDoOdebrania;
	private JButton btnOdebrana;
	private JButton btnNewButton_1;

	/**
	 * Create the application.
	 */
	public WyswietlaniePaczkiKurier(String login, Socket socket, PACZKA paczkaDoWyswietlenia) {
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
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Pierwsza czesc kodu pocztowego to dwie cyfry!");

				// powrotDoZalogujkurier = true;
				// frame.setVisible(false);
				int ans = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz opuscic program?");
				if (ans == JOptionPane.YES_OPTION) {
					// Zamkniêcie okna
					frame.dispose();
				}
			}
		});
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

		btnNewButton = new JButton("Przekazana do nadania");
		btnNewButton.addActionListener(myAction);

		btnWDrodzeDo = new JButton("W drodze do miasta adresata");
		btnWDrodzeDo.addActionListener(myAction);

		btnPrzekazanaDoOdebrania = new JButton("Przekazana do odebrania");
		btnPrzekazanaDoOdebrania.addActionListener(myAction);

		btnOdebrana = new JButton("Odebrana");
		btnOdebrana.addActionListener(myAction);

		btnNewButton_1 = new JButton("W punkcie odbioru");
		btnNewButton_1.addActionListener(myAction);

		if (paczkaDoWyswietlenia.getStan().equals("Przekazana do nadania")) {
			btnNewButton.setEnabled(false);
		} else if (paczkaDoWyswietlenia.getStan().equals("W drodze do miasta adresata")) {
			btnNewButton.setEnabled(false);
			btnWDrodzeDo.setEnabled(false);
		} else if (paczkaDoWyswietlenia.getStan().equals("Przekazana do odebrania")) {
			btnNewButton.setEnabled(false);
			btnWDrodzeDo.setEnabled(false);
			btnPrzekazanaDoOdebrania.setEnabled(false);
		} else if (paczkaDoWyswietlenia.getStan().equals("W punkcie odbioru")) {
			btnNewButton.setEnabled(false);
			btnWDrodzeDo.setEnabled(false);
			btnPrzekazanaDoOdebrania.setEnabled(false);
			btnNewButton_1.setEnabled(false);

		}
		JLabel lblLub = new JLabel("lub");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING,
										groupLayout.createSequentialGroup()
												.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 407,
														GroupLayout.PREFERRED_SIZE)
												.addContainerGap())
								.addGroup(groupLayout.createSequentialGroup().addComponent(lblNewLabel)
										.addPreferredGap(ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
										.addComponent(btnPowrot).addContainerGap())
								.addGroup(Alignment.TRAILING,
										groupLayout.createSequentialGroup().addComponent(lblZmienStan)
												.addContainerGap(368, Short.MAX_VALUE))
								.addGroup(Alignment.TRAILING,
										groupLayout.createSequentialGroup()
												.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 173,
														Short.MAX_VALUE)
												.addGap(251))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup().addGroup(groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnOdebrana, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
										.addComponent(btnPrzekazanaDoOdebrania, Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
										.addComponent(btnWDrodzeDo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
										.addGap(26).addComponent(lblLub).addGap(28)
										.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
										.addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
								.addComponent(btnPowrot))
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblZmienStan)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnWDrodzeDo)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnPrzekazanaDoOdebrania)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnOdebrana)
								.addComponent(btnNewButton_1).addComponent(lblLub))
						.addContainerGap(29, Short.MAX_VALUE)));

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
			case "Przekazana do nadania":
				komunikat = true;
				tmp = JOptionPane.showConfirmDialog(null, "Na pewno?");
				if (tmp == JOptionPane.YES_OPTION) {
					btnNewButton.setEnabled(false);
					mess = "Przekazana do nadania";
					output.println(mess);
					output.flush();
					output.println(paczkaDoWyswietlenia.getidPaczki());
					output.flush();
				}
				komunikat = false;
				break;
			case "W drodze do miasta adresata":
				komunikat = true;
				tmp = JOptionPane.showConfirmDialog(null, "Na pewno?");
				if (tmp == JOptionPane.YES_OPTION) {
					btnNewButton.setEnabled(false);
					btnWDrodzeDo.setEnabled(false);
					mess = "W drodze do miasta adresata";
					output.println(mess);
					output.flush();
					output.println(paczkaDoWyswietlenia.getidPaczki());
					output.flush();
				}
				komunikat = false;
				break;
			case "Przekazana do odebrania":
				komunikat = true;
				tmp = JOptionPane.showConfirmDialog(null, "Na pewno?");
				if (tmp == JOptionPane.YES_OPTION) {
					btnNewButton.setEnabled(false);
					btnWDrodzeDo.setEnabled(false);
					btnPrzekazanaDoOdebrania.setEnabled(false);
					mess = "Przekazana do odebrania";
					output.println(mess);
					output.flush();
					output.println(paczkaDoWyswietlenia.getidPaczki());
					output.flush();
				}
				komunikat = false;
				break;
			case "Odebrana":
				komunikat = true;
				tmp = JOptionPane.showConfirmDialog(null, "Na pewno?");
				if (tmp == JOptionPane.YES_OPTION) {
					btnNewButton.setEnabled(false);
					btnWDrodzeDo.setEnabled(false);
					btnPrzekazanaDoOdebrania.setEnabled(false);
					btnOdebrana.setEnabled(false);
					btnNewButton_1.setEnabled(false);
					mess = "Odebrana";
					output.println(mess);
					output.flush();
					output.println(paczkaDoWyswietlenia.getidPaczki());
					output.flush();
				}
				komunikat = false;
				break;
			case "W punkcie odbioru":
				komunikat = true;
				tmp = JOptionPane.showConfirmDialog(null, "Na pewno?");
				if (tmp == JOptionPane.YES_OPTION) {
					btnNewButton.setEnabled(false);
					btnWDrodzeDo.setEnabled(false);
					btnPrzekazanaDoOdebrania.setEnabled(false);
					btnNewButton_1.setEnabled(false);
					mess = "W punkcie odbioru";
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
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

		private void io(PrintWriter output, BufferedReader input, String mess) {
			boolean flag = true;
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

		private String odczytWiadomosciOdSerwera() {
			boolean flagWiadomoscOdSerwera = true;
			String wiadomoscOdSerwera = null;
			while (flagWiadomoscOdSerwera) {
				try {
					if (input.ready()) {
						wiadomoscOdSerwera = input.readLine();
						flagWiadomoscOdSerwera = false;
					}
				} catch (Exception exc) {
					exc.printStackTrace();
					try {
						// Zamkniecie gniazda klienta
						socket.close();
						flagWiadomoscOdSerwera = false;
					} catch (Exception e) {
					}
				}
			}
			return wiadomoscOdSerwera;
		}
	}
}
