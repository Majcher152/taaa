package proba1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.MouseWheelEvent;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class WyslijPaczke {

	private double kasa;
	private JFrame frame;
	private JTextField textField_waga;
	private final ButtonGroup buttonGroup_wielkosc_przesylki = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTextField textField_ulica_ad;
	private JTextField textField_kod_ad;
	private JTextField textField_ulica_na;
	private JTextField textField_koszt;
	private JButton btnZamwKuriera;
	private JButton btnPowrot;
	private JTextField textField_kod_ad1;
	private JTextField textField_kod_na;
	private JTextField textField_kod_na1;
	private JTextField textField_nr_dom_ad;
	private JTextField textField_nr_mie_ad;
	private JTextField textField_nr_dom_na;
	private JTextField textField_nr_mie_na;
	private JCheckBox chckbx_mie_na;
	private JCheckBox chckbx_mie_ad;
	private JLabel lblKoszt;
	private JCheckBox chckbxTak;
	private PrintWriter output = null;
	private BufferedReader input = null;
	private Socket socket = null;
	private boolean flag = true;
	private String rodzaj = null;
	private boolean ekspres = false;
	private JRadioButton rdbtnEkspresowa;
	private JComboBox comboBox_miasto_ad;
	private JComboBox comboBox_miasto_na;

	/**
	 * Create the application.
	 */
	public WyslijPaczke(Socket socket) {
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
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 641);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblWaga = new JLabel("Waga:");

		textField_waga = new JTextField();
		textField_waga.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String waga = textField_waga.getText();
				double kg = 0;
				try {
					kg = Double.parseDouble(waga);
					if (kg > 0 && kg <= 5) {
						kg = kg + kg * 0.2;
						textField_koszt.setText("" + kg);
					}
					if (kg > 5 && kg <= 100) {
						kg = kg + kg * 0.5;
						textField_koszt.setText("" + kg);
					}
					if (kg > 100 && kg < 1000) {
						kg = kg + kg * 0.7;
						textField_koszt.setText("" + kg);
					}
					if (chckbxTak.isSelected()) {
						kg += 10;
						textField_koszt.setText("" + kg);
					}
					kasa = kg;
				} catch (Exception exp) {
					if (waga.isEmpty())
						textField_koszt.setText("");
					kasa = 0;
				}
			}
		});

		textField_waga.setToolTipText("Podaj wage swojej przesy\u0142ki w kilogramach");
		textField_waga.setColumns(10);
		textField_waga.setDocument(new JTextFieldLimit(3));

		JLabel lblKg = new JLabel("kg");
		JLabel lblSzklo = new JLabel("Szklo:");
		chckbxTak = new JCheckBox("Tak");
		chckbxTak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String koszt = textField_koszt.getText();
				double kg = 0;
				try {
					kg = Double.parseDouble(koszt);
					if (chckbxTak.isSelected()) {
						kg += 10.0;
						textField_koszt.setText("" + kg);
					} else {
						kg -= 10.0;
						textField_koszt.setText("" + kg);
					}
					kasa = kg;
				} catch (Exception exp) {
					if (koszt.isEmpty())
						textField_koszt.setText("");
					kasa = 0;
				}
			}
		});
		JLabel lblRodzaj = new JLabel("Rodzaj:");

		JRadioButton rdbtnList = new JRadioButton("Koperta");
		rdbtnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rodzaj = "Koperta";
				String waga = textField_koszt.getText();
				double kg = kasa;
				if (waga.isEmpty())
					textField_koszt.setText("");
				else {
					textField_koszt.setText("" + kg);
				}
			}
		});
		buttonGroup_wielkosc_przesylki.add(rdbtnList);
		JRadioButton rdbtnPaczka = new JRadioButton("Paczka");
		rdbtnPaczka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rodzaj = "Paczka";
				String waga = textField_koszt.getText();
				double kg = kasa;
				if (waga.isEmpty())
					textField_koszt.setText("");
				else {
					kg += 20;
					textField_koszt.setText("" + kg);
				}
			}
		});
		buttonGroup_wielkosc_przesylki.add(rdbtnPaczka);
		JRadioButton rdbtnCosInnego = new JRadioButton("Paleta");
		rdbtnCosInnego.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				rodzaj = "Paleta";
				String waga = textField_koszt.getText();
				double kg = kasa;
				if (waga.isEmpty())
					textField_koszt.setText("");
				else {
					kg += 100;
					textField_koszt.setText("" + kg);
				}
			}
		});

		/*
		 * rdbtnCosInnego.addActionListener(new ActionListener() { public void
		 * actionPerformed(ActionEvent e) { String waga =
		 * textField_koszt.getText(); double kg = 0; try { kg =
		 * Double.parseDouble(waga); kg += 100; textField_koszt.setText("" +
		 * kg); } catch (Exception exp) { if (waga.isEmpty())
		 * textField_koszt.setText(""); }
		 * 
		 * } });
		 */
		buttonGroup_wielkosc_przesylki.add(rdbtnCosInnego);

		JLabel lblPrzesyka = new JLabel("Przesylka:");

		rdbtnEkspresowa = new JRadioButton("ekspresowa");
		rdbtnEkspresowa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnCosInnego.isSelected()) {
					kasa = kasa + kasa * 0.5;
					textField_koszt.setText("" + kasa);
				} else {
					kasa = kasa - kasa * 0.3;
					textField_koszt.setText("" + kasa);
				}
			}
		});
		buttonGroup_1.add(rdbtnEkspresowa);
		JRadioButton rdbtnZwyka = new JRadioButton("zwykla");
		rdbtnZwyka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnCosInnego.isSelected()) {
					textField_koszt.setText("" + kasa);
				}
			}
		});
		buttonGroup_1.add(rdbtnZwyka);

		JLabel lblDaneAdresata = new JLabel("Dane adresata:");
		JLabel lblMiasto = new JLabel("Miasto:");
		JLabel lblUlica = new JLabel("Ulica:");
		textField_ulica_ad = new JTextField();
		textField_ulica_ad.setToolTipText("Podaj ulica skad kurier ma odebrac przesylke");
		textField_ulica_ad.setColumns(10);
		JLabel lblNewLabel = new JLabel("Kod pocztowy:");
		textField_kod_ad = new JTextField();
		textField_kod_ad.setColumns(10);
		textField_kod_ad.setDocument(new JTextFieldLimit(2));
		JLabel label = new JLabel("-");
		textField_kod_ad1 = new JTextField();
		textField_kod_ad1.setColumns(10);
		textField_kod_ad1.setDocument(new JTextFieldLimit(3));

		JLabel lblDaneNadawcy = new JLabel("Dane nadawcy:");
		JLabel lblMiasto_1 = new JLabel("Miasto:");
		JLabel lblUlica_1 = new JLabel("Ulica:");
		JLabel lblKodPocztowy = new JLabel("Kod pocztowy:");
		textField_ulica_na = new JTextField();
		textField_ulica_na.setToolTipText("Podaj ulica gdzie  kurier  ma dostarczyc przesylke");
		textField_ulica_na.setColumns(10);

		lblKoszt = new JLabel("Koszt:");
		lblKoszt.setFont(new Font("Tahoma", Font.BOLD, 14));
		textField_koszt = new JTextField();
		textField_koszt.setEditable(false);
		textField_koszt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_koszt.setColumns(10);
		JLabel lblZ = new JLabel("z\u0142");
		lblZ.setFont(new Font("Tahoma", Font.PLAIN, 14));

		btnZamwKuriera = new JButton("Zamow kuriera");

		btnPowrot = new JButton("Powrot");

		textField_kod_na = new JTextField();
		textField_kod_na.setColumns(10);
		textField_kod_na.setDocument(new JTextFieldLimit(2));

		JLabel label_1 = new JLabel("-");

		textField_kod_na1 = new JTextField();
		textField_kod_na1.setColumns(10);
		textField_kod_na1.setDocument(new JTextFieldLimit(3));

		JLabel lblNrDomuI = new JLabel("Nr domu:");

		textField_nr_dom_ad = new JTextField();
		textField_nr_dom_ad.setColumns(10);

		JLabel lblNrMieszkania = new JLabel("Nr mieszkania:");

		textField_nr_mie_ad = new JTextField();
		textField_nr_mie_ad.setEditable(false);
		textField_nr_mie_ad.setColumns(10);

		JLabel lblNrDomu = new JLabel("Nr domu:");

		textField_nr_dom_na = new JTextField();
		textField_nr_dom_na.setColumns(10);

		JLabel lblNrMieszkania_1 = new JLabel("Nr mieszkania:");

		textField_nr_mie_na = new JTextField();
		textField_nr_mie_na.setEditable(false);
		textField_nr_mie_na.setColumns(10);

		comboBox_miasto_ad = new JComboBox();
		comboBox_miasto_ad.setModel(new DefaultComboBoxModel(
				new String[] { "Krakow", "Warszawa", "Katowice", "Poznan", "Wroclaw", "Gdansk", "Gdynia" }));

		comboBox_miasto_na = new JComboBox();
		comboBox_miasto_na.setModel(new DefaultComboBoxModel(
				new String[] { "Krakow", "Warszawa", "Katowice", "Poznan", "Wroclaw", "Gdansk", "Gdynia" }));

		chckbx_mie_ad = new JCheckBox("");
		chckbx_mie_ad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbx_mie_ad.isSelected())
					textField_nr_mie_ad.setEditable(true);
				else {
					textField_nr_mie_ad.setEditable(false);
					textField_nr_mie_ad.setText("");
				}
			}
		});

		chckbx_mie_na = new JCheckBox("");
		chckbx_mie_na.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbx_mie_na.isSelected())
					textField_nr_mie_na.setEditable(true);
				else {
					textField_nr_mie_na.setEditable(false);
					textField_nr_mie_na.setText("");
				}
			}
		});
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, lblZ, 3, SpringLayout.NORTH, textField_koszt);
		springLayout.putConstraint(SpringLayout.WEST, lblZ, 4, SpringLayout.EAST, textField_koszt);
		springLayout.putConstraint(SpringLayout.NORTH, textField_koszt, -3, SpringLayout.NORTH, lblKoszt);
		springLayout.putConstraint(SpringLayout.WEST, textField_koszt, 8, SpringLayout.EAST, lblKoszt);
		springLayout.putConstraint(SpringLayout.EAST, textField_koszt, 0, SpringLayout.EAST, rdbtnList);
		springLayout.putConstraint(SpringLayout.WEST, lblKoszt, 0, SpringLayout.WEST, textField_ulica_na);
		springLayout.putConstraint(SpringLayout.SOUTH, lblKoszt, -18, SpringLayout.NORTH, btnZamwKuriera);
		springLayout.putConstraint(SpringLayout.NORTH, btnPowrot, 0, SpringLayout.NORTH, btnZamwKuriera);
		springLayout.putConstraint(SpringLayout.EAST, btnPowrot, -10, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnZamwKuriera, 0, SpringLayout.WEST, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.SOUTH, btnZamwKuriera, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField_nr_mie_na, 6, SpringLayout.EAST, lblNrMieszkania_1);
		springLayout.putConstraint(SpringLayout.EAST, textField_nr_mie_na, -193, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField_nr_mie_ad, 6, SpringLayout.EAST, lblNrMieszkania);
		springLayout.putConstraint(SpringLayout.EAST, textField_nr_mie_ad, -193, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_nr_mie_na, -3, SpringLayout.NORTH, lblNrDomu);
		springLayout.putConstraint(SpringLayout.WEST, lblNrMieszkania_1, 171, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, chckbx_mie_na, 0, SpringLayout.SOUTH, lblNrDomu);
		springLayout.putConstraint(SpringLayout.EAST, chckbx_mie_na, -6, SpringLayout.WEST, lblNrMieszkania_1);
		springLayout.putConstraint(SpringLayout.NORTH, lblNrMieszkania_1, 0, SpringLayout.NORTH, lblNrDomu);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnZwyka, 12, SpringLayout.EAST, rdbtnEkspresowa);
		springLayout.putConstraint(SpringLayout.NORTH, lblNrMieszkania, 0, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.WEST, lblNrMieszkania, 6, SpringLayout.EAST, chckbx_mie_ad);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNrMieszkania, 280, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, chckbx_mie_ad, 0, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.WEST, chckbx_mie_ad, 0, SpringLayout.WEST, lblKg);
		springLayout.putConstraint(SpringLayout.NORTH, textField_waga, -3, SpringLayout.NORTH, lblWaga);
		springLayout.putConstraint(SpringLayout.WEST, textField_waga, 0, SpringLayout.WEST, textField_ulica_na);
		springLayout.putConstraint(SpringLayout.EAST, textField_waga, 138, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_kod_ad1, -3, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, textField_kod_ad1, 6, SpringLayout.EAST, label);
		springLayout.putConstraint(SpringLayout.EAST, textField_kod_ad1, 0, SpringLayout.EAST, textField_kod_na1);
		springLayout.putConstraint(SpringLayout.WEST, lblKg, 102, SpringLayout.EAST, lblWaga);
		springLayout.putConstraint(SpringLayout.WEST, textField_kod_na1, 2, SpringLayout.EAST, label_1);
		springLayout.putConstraint(SpringLayout.EAST, textField_kod_na1, -333, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_kod_na1, -3, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnZwyka, -4, SpringLayout.NORTH, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnCosInnego, 0, SpringLayout.NORTH, rdbtnList);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnCosInnego, 9, SpringLayout.EAST, rdbtnPaczka);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnPaczka, 0, SpringLayout.NORTH, rdbtnList);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnPaczka, 6, SpringLayout.EAST, rdbtnList);
		springLayout.putConstraint(SpringLayout.NORTH, lblKg, 0, SpringLayout.NORTH, lblWaga);
		springLayout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, label_1);
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.EAST, label_1, 0, SpringLayout.EAST, textField_nr_dom_na);
		springLayout.putConstraint(SpringLayout.NORTH, textField_kod_na, -3, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.WEST, textField_kod_na, 0, SpringLayout.WEST, textField_ulica_na);
		springLayout.putConstraint(SpringLayout.EAST, textField_kod_na, 115, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comboBox_miasto_na, 50, SpringLayout.EAST, lblMiasto_1);
		springLayout.putConstraint(SpringLayout.EAST, comboBox_miasto_na, -193, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField_ulica_na, 59, SpringLayout.EAST, lblUlica_1);
		springLayout.putConstraint(SpringLayout.EAST, textField_ulica_na, -193, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_nr_dom_na, -3, SpringLayout.NORTH, lblNrDomu);
		springLayout.putConstraint(SpringLayout.WEST, textField_nr_dom_na, 0, SpringLayout.WEST, textField_ulica_na);
		springLayout.putConstraint(SpringLayout.EAST, textField_nr_dom_na, 121, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textField_ulica_na, 0, SpringLayout.SOUTH, lblUlica_1);
		springLayout.putConstraint(SpringLayout.SOUTH, lblDaneNadawcy, -21, SpringLayout.NORTH, comboBox_miasto_na);
		springLayout.putConstraint(SpringLayout.NORTH, comboBox_miasto_na, -3, SpringLayout.NORTH, lblMiasto_1);
		springLayout.putConstraint(SpringLayout.WEST, lblDaneNadawcy, 0, SpringLayout.WEST, lblDaneAdresata);
		springLayout.putConstraint(SpringLayout.NORTH, comboBox_miasto_ad, 180, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comboBox_miasto_ad, 95, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, comboBox_miasto_ad, 289, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_ulica_ad, 217, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField_ulica_ad, 95, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField_ulica_ad, 289, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_nr_mie_ad, 257, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_nr_dom_ad, 257, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField_nr_dom_ad, 95, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField_nr_dom_ad, 121, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_kod_ad, 289, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField_kod_ad, 95, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField_kod_ad, 115, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnEkspresowa, 109, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, rdbtnEkspresowa, 95, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblDaneAdresata, 150, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblDaneAdresata, 190, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnList, 78, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, rdbtnList, 95, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, chckbxTak, 50, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, chckbxTak, 95, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 292, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblKodPocztowy, 490, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblKodPocztowy, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNrDomu, 452, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNrDomu, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblMiasto_1, 370, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblMiasto_1, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNrDomuI, 255, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNrDomuI, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNrDomuI, 280, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblUlica, 220, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblUlica, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblMiasto, 183, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblMiasto, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblUlica_1, 413, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblUlica_1, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblRodzaj, 80, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblRodzaj, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblSzklo, 54, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblSzklo, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblWaga, 26, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblWaga, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblPrzesyka, 113, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblPrzesyka, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().add(lblPrzesyka);
		frame.getContentPane().add(lblWaga);
		frame.getContentPane().add(lblSzklo);
		frame.getContentPane().add(lblRodzaj);
		frame.getContentPane().add(lblUlica_1);
		frame.getContentPane().add(lblMiasto);
		frame.getContentPane().add(lblUlica);
		frame.getContentPane().add(lblNrDomuI);
		frame.getContentPane().add(lblMiasto_1);
		frame.getContentPane().add(lblNrDomu);
		frame.getContentPane().add(lblKodPocztowy);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(textField_ulica_na);
		frame.getContentPane().add(textField_nr_dom_na);
		frame.getContentPane().add(chckbx_mie_na);
		frame.getContentPane().add(lblNrMieszkania_1);
		frame.getContentPane().add(textField_nr_mie_na);
		frame.getContentPane().add(textField_kod_na);
		frame.getContentPane().add(label_1);
		frame.getContentPane().add(textField_kod_na1);
		frame.getContentPane().add(chckbxTak);
		frame.getContentPane().add(textField_waga);
		frame.getContentPane().add(lblKg);
		frame.getContentPane().add(rdbtnList);
		frame.getContentPane().add(lblDaneAdresata);
		frame.getContentPane().add(rdbtnPaczka);
		frame.getContentPane().add(rdbtnEkspresowa);
		frame.getContentPane().add(rdbtnZwyka);
		frame.getContentPane().add(textField_kod_ad);
		frame.getContentPane().add(label);
		frame.getContentPane().add(textField_kod_ad1);
		frame.getContentPane().add(textField_nr_dom_ad);
		frame.getContentPane().add(chckbx_mie_ad);
		frame.getContentPane().add(lblNrMieszkania);
		frame.getContentPane().add(textField_nr_mie_ad);
		frame.getContentPane().add(textField_ulica_ad);
		frame.getContentPane().add(comboBox_miasto_ad);
		frame.getContentPane().add(comboBox_miasto_na);
		frame.getContentPane().add(rdbtnCosInnego);
		frame.getContentPane().add(lblDaneNadawcy);
		frame.getContentPane().add(btnZamwKuriera);
		frame.getContentPane().add(btnPowrot);
		frame.getContentPane().add(textField_koszt);
		frame.getContentPane().add(lblZ);
		frame.getContentPane().add(lblKoszt);
		btnPowrot.addActionListener(myAction);
		btnZamwKuriera.addActionListener(myAction);

		try {
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output.println("Wyslij Paczke");
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

	private class MyActionListener implements ActionListener {
		private double wagaWartosc;
		private int domAd;
		private int domNa;
		private int mieAd;
		private int mieNa;
		private int kod1Ad;
		private int kod1Na;
		private int kod2Ad;
		private int kod2Na;

		@Override
		public void actionPerformed(ActionEvent e) {
			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "Zamow kuriera":
				if (komunikat_waga())
					break;
				// if (komunikat_miasto(textField_miasto_ad.getText()) ||
				// komunikat_miasto(textField_miasto_na.getText()))
				// break;
				if (komunikat_ulica(textField_ulica_ad.getText()) || komunikat_ulica(textField_ulica_na.getText()))
					break;
				if (komunikat_dom(textField_nr_dom_ad.getText()) || komunikat_dom(textField_nr_dom_na.getText()))
					break;
				if (komunikat_mieszkanie_ad(textField_nr_mie_ad.getText())
						|| komunikat_mieszkanie_na(textField_nr_mie_na.getText()))
					break;
				if (komunikat_kod(textField_kod_ad.getText()) || (komunikat_kod(textField_kod_ad1.getText())))
					break;
				if (komunikat_kod(textField_kod_na.getText()) || (komunikat_kod(textField_kod_na1.getText())))
					break;
				Paczka p = new Paczka(wagaWartosc, chckbxTak.isSelected(), rodzaj, rdbtnEkspresowa.isSelected(),
						(String) comboBox_miasto_ad.getSelectedItem(), textField_ulica_ad.getText(), domAd, mieAd,
						kod1Ad, kod2Ad, (String) comboBox_miasto_na.getSelectedItem(), textField_ulica_na.getText(),
						domNa, mieNa, kod1Na, kod2Na);
				System.out.println(kod1Ad + " " + kod2Ad + " " + kod1Na + " " + kod1Na + " ");
				JOptionPane.showMessageDialog(null, p.toString());

				break;
			case "Powrot":
				int tmp = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz opuscic strone?");
				if (tmp == JOptionPane.YES_OPTION) {
					frame.setVisible(false);
					new StronaGlowna(socket);
					break;
				}
			}
		}

		private boolean komunikat_waga() {
			String waga = textField_waga.getText();
			wagaWartosc = 0;
			try {
				wagaWartosc = Double.parseDouble(textField_waga.getText());
			} catch (Exception exp) {
				if (waga.isEmpty())
					JOptionPane.showMessageDialog(null, "Przesylka musi posiadac wage");
				else if (waga.contains(","))
					JOptionPane.showMessageDialog(null, "Zamiast przecinka wstaw kropke.");
				else if (waga.isEmpty())
					JOptionPane.showMessageDialog(null, "Przesylka musi posiadac wage");
				else
					JOptionPane.showMessageDialog(null, "Waga musi byc liczba!");
				return true;
			}
			if (wagaWartosc <= 0) {
				JOptionPane.showMessageDialog(null, "Przesylka musi posiadac wage wieksza od 0");
				return true;
			} else if (wagaWartosc >= 1000) {
				JOptionPane.showMessageDialog(null, "Nasza firma nie oferuje transportu dla przesylek 1000kg i wyzej");
				return true;
			} else
				return false;
		}

		private boolean komunikat_kod(String kod) {
			System.out.println(kod);
			// KURWA TAK BO JAK WPISZESZ COS W 1 KOD "2" A POTEM MASZ W DRUGIM KODZIE "22" TO TA KURWA DWOJA TEZ JEST JAPIERDOLE
			try {
				if (kod.contains(textField_kod_ad.getText()))
					kod1Ad = (int) Double.parseDouble(kod);
				else if (kod.contains(textField_kod_na.getText()))
					kod1Na = (int) Double.parseDouble(kod);
				else if (kod.contains( textField_kod_ad1.getText()))
					kod2Ad = (int) Double.parseDouble(kod);
				else if (kod.contains(textField_kod_na1.getText()))
					kod2Na = (int) Double.parseDouble(kod);
			} catch (Exception exp) {
				if (kod.isEmpty())
					JOptionPane.showMessageDialog(null, "Uzupelnuj kod pocztowy!");
				else
					JOptionPane.showMessageDialog(null, "Kod musi byc liczba!");
				return true;
			}
			return false;
		}

		private boolean komunikat_miasto(String miasto) {
			try {
				Double.parseDouble(miasto);
			} catch (Exception exp) {
				if (miasto.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Podaj miasto!");
					return true;
				} else
					return false;
			}
			JOptionPane.showMessageDialog(null, "Miasto nie moze byc liczba!");
			return true;
		}

		private boolean komunikat_ulica(String ulica) {
			try {
				Double.parseDouble(ulica);
			} catch (Exception exp) {
				if (ulica.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Podaj ulice!");
					return true;
				} else
					return false;
			}
			JOptionPane.showMessageDialog(null, "Ulica nie moze byc liczba!");
			return true;
		}

		private boolean komunikat_dom(String dom) {
			try {
				if (dom.contains(textField_nr_dom_ad.getText()))
					domAd = (int) Double.parseDouble(dom);
				else
					domNa = (int) Double.parseDouble(dom);
			} catch (Exception exp) {
				if (dom.isEmpty())
					JOptionPane.showMessageDialog(null, "Uzupelnij numer domu!");
				else
					JOptionPane.showMessageDialog(null, "Numer domu musi byc liczba!");
				return true;
			}
			return false;
		}

		private boolean komunikat_mieszkanie_na(String mieszkanie) {
			try {
				mieNa = (int) Double.parseDouble(mieszkanie);
			} catch (Exception exp) {
				if (mieszkanie.isEmpty()) {
					if (chckbx_mie_na.isSelected()) {
						JOptionPane.showMessageDialog(null, "Uzupelnij numer mieszkania!");
						return true;
					} else
						return false;
				} else {
					JOptionPane.showMessageDialog(null, "Numer mieszkania musi byc liczba!");
					return true;
				}
			}
			return false;
		}

		private boolean komunikat_mieszkanie_ad(String mieszkanie) {
			try {
				mieAd = (int) Double.parseDouble(mieszkanie);
			} catch (Exception exp) {
				if (mieszkanie.isEmpty()) {
					if (chckbx_mie_ad.isSelected()) {
						JOptionPane.showMessageDialog(null, "Uzupelnij numer mieszkania!");
						return true;
					} else
						return false;
				} else {
					JOptionPane.showMessageDialog(null, "Numer mieszkania musi byc liczba!");
					return true;
				}
			}
			return false;
		}

	}

	private class JTextFieldLimit extends PlainDocument {
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}

}
