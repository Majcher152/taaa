package proba1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import baza.PACZKA;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ZalogujKurier {

	private JFrame frame;
	private String login;
	private PrintWriter output = null;
	private BufferedReader input = null;
	private Socket socket = null;
	private boolean flag = true;
	public static JList list;
	private ArrayList<PACZKA> doOdebrania;
	private ArrayList<PACZKA> doDostarczenia;
	private String[] nazwyDoOdebrania = null;
	private String[] nazwyDoDostarczenia;
	private JComboBox comboBox;
	private JScrollPane scrollPane;

	public ZalogujKurier(String login, Socket socket) {
		this.login = login;
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
		flag = true;
		ustawienieInputOutput();
		output.println("Jestesmy w Zaloguj Kurier");
		output.flush();
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			doOdebrania = (ArrayList<PACZKA>) ois.readObject();
			doDostarczenia = (ArrayList<PACZKA>) ois.readObject();
			// String paczkaWyswietlanie = doOdebrania.toString();
		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!(doOdebrania.isEmpty())) {
			nazwyDoOdebrania = new String[doOdebrania.size()];

			for (int i = 0; i < doOdebrania.size(); i++) {
				nazwyDoOdebrania[i] = doOdebrania.get(i).getidPaczki() + "";
			}
		}
		// System.out.println(nazwyDoOdebrania.length);
		if (!(doDostarczenia.isEmpty())) {
			nazwyDoDostarczenia = new String[doDostarczenia.size()];

			for (int i = 0; i < doDostarczenia.size(); i++) {
				nazwyDoDostarczenia[i] = doDostarczenia.get(i).getidPaczki() + "";
			}
		}
		frame = new JFrame();
		frame.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
			}
		});
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JButton btnWyloguj = new JButton("Wyloguj");
		springLayout.putConstraint(SpringLayout.EAST, btnWyloguj, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnWyloguj);

		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.SOUTH, btnWyloguj, 0, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, panel, 15, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -149, SpringLayout.EAST, frame.getContentPane());
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Konto", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 349, SpringLayout.NORTH, frame.getContentPane());
		frame.getContentPane().add(panel);

		comboBox = new JComboBox();

		comboBox.setModel(new DefaultComboBoxModel(new String[] { "", "Do odebrania", "Do dostarczenia" }));
		comboBox.setSelectedIndex(0);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				comboBox.removeItem("");
				if (comboBox.getSelectedItem().toString().contains("Do odebrania")) {

					list.setModel(new AbstractListModel() {

						String[] values = nazwyDoOdebrania;

						public int getSize() {
							if (values != null)
								return values.length;
							else
								return 0;
						}

						public Object getElementAt(int index) {
							return values[index];
						}
					});

				} else {
					list.setModel(new AbstractListModel() {
						String[] values = nazwyDoDostarczenia;

						public int getSize() {
							try {
								return values.length;
							} catch (NullPointerException e) {
								return 0;
							}

						}

						public Object getElementAt(int index) {
							return values[index];
						}
					});

				}
			}
		});

		JLabel lblPrzesylka = new JLabel("Przesylki:");

		scrollPane = new JScrollPane();

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		springLayout.putConstraint(SpringLayout.EAST, textArea, -72, SpringLayout.EAST, frame.getContentPane());
		textArea.setText(login + " ");
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -291, SpringLayout.NORTH, btnWyloguj);
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 0, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, textArea, 6, SpringLayout.EAST, panel);

		JLabel lblLogin = new JLabel("Login:");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(3).addComponent(lblPrzesylka).addGap(18)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
								.addComponent(lblLogin).addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane,
								GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)))
						.addGap(0)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addContainerGap()
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPrzesylka)))
						.addGroup(
								gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblLogin)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE).addGap(4)));

		list = new JList();
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int index = list.getSelectedIndex();
				odswiezanieListy();
				if (comboBox.getSelectedItem().toString().contains("Do odebrania")) {
					try {
						if (index != -1 && doOdebrania.get(index) != null) {
							new WyswietlaniePaczkiKurierDoOdebrania(login, socket, doOdebrania.get(index));
						}
					} catch (IndexOutOfBoundsException e) {}
				} else if (comboBox.getSelectedItem().toString().contains("Do dostarczenia")){
					try {
						if (index != -1 && doDostarczenia.get(index) != null) {
							new WyswietlaniePaczkiKurier(login, socket, doDostarczenia.get(index));
						}
					} catch (IndexOutOfBoundsException e) {}
				}
			}

		});

		scrollPane.setViewportView(list);
		panel.setLayout(gl_panel);

		JButton btnOdswiez = new JButton("Odswiez");

		springLayout.putConstraint(SpringLayout.NORTH, btnOdswiez, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnOdswiez, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnOdswiez);

		btnWyloguj.addActionListener(myAction);
		btnOdswiez.addActionListener(myAction);

	}

	// MOZNA TEZ DODAC PRZYCISK ODSWIEZ, BEDZIE TO LEPIEJ WYGLADAC
	private void odswiezanieListy() {
		output.println("Odswiez");
		output.flush();
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			doOdebrania = (ArrayList<PACZKA>) ois.readObject();
			doDostarczenia = (ArrayList<PACZKA>) ois.readObject();
			// String paczkaWyswietlanie = doOdebrania.toString();
		} catch (IOException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!(doOdebrania.isEmpty())) {
			nazwyDoOdebrania = new String[doOdebrania.size()];

			for (int i = 0; i < doOdebrania.size(); i++) {
				nazwyDoOdebrania[i] = doOdebrania.get(i).getidPaczki() + "";
			}
		} else {
			nazwyDoOdebrania = new String[1];
			nazwyDoOdebrania[0] = "";
			// scrollPane.setViewportView(l);
		}
		if (!(doDostarczenia.isEmpty())) {
			nazwyDoDostarczenia = new String[doDostarczenia.size()];

			for (int i = 0; i < doDostarczenia.size(); i++) {
				nazwyDoDostarczenia[i] = doDostarczenia.get(i).getidPaczki() + "";
			}
		} else {
			nazwyDoDostarczenia = new String[1];
			nazwyDoDostarczenia[0] = "";
		}
		if (comboBox.getSelectedItem().toString().contains("Do odebrania")) {
			list.setModel(new AbstractListModel() {

				String[] values = nazwyDoOdebrania;

				public int getSize() {
					if (values != null)
						return values.length;
					else
						return 0;
				}

				public Object getElementAt(int index) {
					return values[index];
				}
			});

		} else {
			list.setModel(new AbstractListModel() {
				String[] values = nazwyDoDostarczenia;

				public int getSize() {
					return values.length;
				}

				public Object getElementAt(int index) {
					return values[index];
				}

			});

		}
		scrollPane.setViewportView(list);
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

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String co = ((JButton) e.getSource()).getText();
			ustawienieInputOutput();
			switch (co) {
			case "Odswiez":
				odswiezanieListy();
				break;
			case "Wyloguj":
				int tmp = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz sie wylogowac?");
				if (tmp == JOptionPane.YES_OPTION) {
					String mess = "Wyloguj";
					io(output, input, mess);
					frame.setVisible(false);
					new StronaGlowna(socket);
					break;
				}
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