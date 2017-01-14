package proba1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

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
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
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
import java.awt.TextField;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class WyslijPaczke {

	private Date aktualnaData;
	private boolean flagaData = false;
	private Date selectedDate;
	private double wyswietlana_cena;
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
	private float[] koszta = new float[4];
	private float koszt;
	private JTextField textField_nazwisko_na;
	private JTextField textField_imie_na;
	private JTextField textField_nazwisko_ad;
	private JTextField textField_imie_ad;
	private SpringLayout springLayout_1;
	private SpringLayout springLayout_2;

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

	private void ustawienieKosztu(float a, int i) {
		koszta[i] = a;

		if (koszta[2] != 0)
			koszt = koszta[0] * koszta[2] + koszta[1] + koszta[3];
		else
			koszt = koszta[0] + koszta[1] + koszta[3];
		koszt *= 100;
		koszt = Math.round(koszt);
		koszt /= 100;
		textField_koszt.setText(koszt + "");

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		MyActionListener myAction = new MyActionListener();
		frame = new JFrame();
		frame.setBounds(100, 100, 641, 680);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblWaga = new JLabel("Waga:");

		textField_waga = new JTextField();
		textField_waga.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String waga = textField_waga.getText();
				float waga_cena = 0;
				float kg = 0;
				try {
					kg = Float.parseFloat(waga);
					if (kg > 0 && kg <= 5) {
						waga_cena = (float) (kg + kg * 0.2);
						ustawienieKosztu(waga_cena, 0);
					}
					if (kg > 5 && kg <= 100) {
						waga_cena = (float) (kg + kg * 0.5);
						ustawienieKosztu(waga_cena, 0);
					}
					if (kg > 100 && kg < 1000) {
						waga_cena = (float) (kg + kg * 0.7);
						ustawienieKosztu(waga_cena, 0);
					}
				} catch (Exception exp) {
					if (waga.isEmpty())
						ustawienieKosztu(waga_cena, 0);
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
				float waga_cena = 0;
				try {
					if (chckbxTak.isSelected()) {
						waga_cena = (float) 10.0;
						ustawienieKosztu(waga_cena, 1);

					} else {
						waga_cena = 0;
						ustawienieKosztu(waga_cena, 1);
					}
				} catch (Exception exp) {
				}
			}
		});
		JLabel lblRodzaj = new JLabel("Rodzaj:");

		JRadioButton rdbtnList = new JRadioButton("Koperta");
		rdbtnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float waga_cena = 0;
				if (rdbtnList.isSelected()) {
					waga_cena = 1;
					ustawienieKosztu(waga_cena, 2);
				}
			}
		});
		buttonGroup_wielkosc_przesylki.add(rdbtnList);
		JRadioButton rdbtnPaczka = new JRadioButton("Paczka");
		rdbtnPaczka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float waga_cena = 0;
				if (rdbtnPaczka.isSelected()) {
					waga_cena = 2;
					ustawienieKosztu(waga_cena, 2);
				}

			}
		});
		buttonGroup_wielkosc_przesylki.add(rdbtnPaczka);
		JRadioButton rdbtnCosInnego = new JRadioButton("Paleta");
		rdbtnCosInnego.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				float waga_cena = 0;
				if (rdbtnCosInnego.isSelected()) {
					waga_cena = 3;
					ustawienieKosztu(waga_cena, 2);
				}
			}
		});
		buttonGroup_wielkosc_przesylki.add(rdbtnCosInnego);

		JLabel lblPrzesyka = new JLabel("Przesylka:");

		rdbtnEkspresowa = new JRadioButton("ekspresowa");
		rdbtnEkspresowa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float waga_cena = 0;
				if (rdbtnEkspresowa.isSelected()) {
					waga_cena = 100;
					ustawienieKosztu(waga_cena, 3);
				}
			}
		});
		buttonGroup_1.add(rdbtnEkspresowa);
		JRadioButton rdbtnZwyka = new JRadioButton("zwykla");
		rdbtnZwyka.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float waga_cena = 0;
				if (rdbtnZwyka.isSelected()) {
					waga_cena = 0;
					ustawienieKosztu(waga_cena, 3);
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
		lblKoszt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_koszt = new JTextField();
		textField_koszt.setEditable(false);
		textField_koszt.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_koszt.setColumns(10);
		JLabel lblZ = new JLabel("z\u0142");
		lblZ.setFont(new Font("Tahoma", Font.PLAIN, 18));

		btnZamwKuriera = new JButton("Zamow kuriera");
		btnZamwKuriera.setFont(new Font("Tahoma", Font.BOLD, 15));

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
		UtilDateModel model = new UtilDateModel();
		Calendar calendar = Calendar.getInstance();
		model.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);
		aktualnaData = model.getValue();

		Properties prop = new Properties();
		prop.put("text.today", "Today");
		prop.put("text.month", "Month");
		prop.put("text.year", "Year");

		JDatePanelImpl datePanel = new JDatePanelImpl(model, prop);
		// JDatePanelImpl datePanel = new JDatePanelImpl(model, null);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		// JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, null);

		datePicker.getJFormattedTextField().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				if (flagaData) {
					selectedDate = (Date) datePicker.getModel().getValue();
					if (aktualnaData.before(selectedDate))
						System.out.println(selectedDate);
					else
						JOptionPane.showMessageDialog(null,
								"Nasza firma nie przyjmuje zamówieñ na aktualny dzieñ ani nie \njest w stanie cofn¹æ siê w czasie. Za problemy przepraszamy.\n:(");
				}
				flagaData = true;
			}
		});

		frame.getContentPane().add(datePicker);

		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.WEST, datePicker, 0, SpringLayout.WEST, lblUlica_1);
		springLayout.putConstraint(SpringLayout.SOUTH, datePicker, 0, SpringLayout.SOUTH, textField_koszt);
		springLayout.putConstraint(SpringLayout.NORTH, textField_koszt, 63, SpringLayout.SOUTH, textField_kod_ad1);
		springLayout.putConstraint(SpringLayout.WEST, textField_koszt, 12, SpringLayout.EAST, lblKoszt);
		springLayout.putConstraint(SpringLayout.EAST, textField_koszt, -441, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblZ, 3, SpringLayout.NORTH, textField_koszt);
		springLayout.putConstraint(SpringLayout.WEST, lblZ, 6, SpringLayout.EAST, textField_koszt);
		springLayout.putConstraint(SpringLayout.NORTH, btnZamwKuriera, 24, SpringLayout.SOUTH, textField_koszt);
		springLayout.putConstraint(SpringLayout.WEST, btnZamwKuriera, 0, SpringLayout.WEST, lblKoszt);
		springLayout.putConstraint(SpringLayout.SOUTH, btnZamwKuriera, -61, SpringLayout.NORTH, btnPowrot);
		springLayout.putConstraint(SpringLayout.EAST, btnZamwKuriera, -398, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblKoszt, 3, SpringLayout.NORTH, textField_koszt);
		springLayout.putConstraint(SpringLayout.EAST, lblKoszt, 0, SpringLayout.EAST, textField_kod_ad);
		springLayout.putConstraint(SpringLayout.WEST, btnPowrot, 0, SpringLayout.WEST, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.SOUTH, btnPowrot, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_kod_ad1, -3, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.WEST, textField_kod_ad1, 7, SpringLayout.EAST, label);
		springLayout.putConstraint(SpringLayout.EAST, textField_kod_ad1, 0, SpringLayout.EAST, chckbxTak);
		springLayout.putConstraint(SpringLayout.WEST, textField_nr_dom_na, 0, SpringLayout.WEST, textField_ulica_na);
		springLayout.putConstraint(SpringLayout.NORTH, textField_nr_dom_ad, 2, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.WEST, textField_nr_dom_ad, 0, SpringLayout.WEST, textField_kod_ad);
		springLayout.putConstraint(SpringLayout.EAST, textField_nr_dom_ad, 0, SpringLayout.EAST, textField_kod_ad);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnPaczka, 17, SpringLayout.EAST, rdbtnList);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnZwyka, -4, SpringLayout.NORTH, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnZwyka, 39, SpringLayout.EAST, rdbtnEkspresowa);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnCosInnego, -4, SpringLayout.NORTH, lblRodzaj);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnCosInnego, 19, SpringLayout.EAST, rdbtnPaczka);
		springLayout.putConstraint(SpringLayout.WEST, textField_waga, 85, SpringLayout.EAST, lblWaga);
		springLayout.putConstraint(SpringLayout.EAST, textField_waga, -455, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblKg, 0, SpringLayout.NORTH, lblWaga);
		springLayout.putConstraint(SpringLayout.WEST, lblKg, 6, SpringLayout.EAST, textField_waga);
		springLayout.putConstraint(SpringLayout.NORTH, textField_waga, -3, SpringLayout.NORTH, lblWaga);
		springLayout.putConstraint(SpringLayout.NORTH, chckbxTak, -4, SpringLayout.NORTH, lblSzklo);
		springLayout.putConstraint(SpringLayout.WEST, chckbxTak, 0, SpringLayout.WEST, rdbtnList);
		springLayout.putConstraint(SpringLayout.NORTH, lblDaneAdresata, 16, SpringLayout.SOUTH, rdbtnEkspresowa);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnEkspresowa, -4, SpringLayout.NORTH, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnEkspresowa, 0, SpringLayout.WEST, rdbtnList);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnList, -4, SpringLayout.NORTH, lblRodzaj);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnList, 0, SpringLayout.WEST, label);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnPaczka, -4, SpringLayout.NORTH, lblRodzaj);
		springLayout.putConstraint(SpringLayout.NORTH, lblDaneNadawcy, 0, SpringLayout.NORTH, lblDaneAdresata);
		springLayout.putConstraint(SpringLayout.EAST, lblDaneNadawcy, -76, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_kod_na1, -3, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.WEST, textField_kod_na1, 456, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField_kod_na1, 0, SpringLayout.EAST, chckbx_mie_na);
		springLayout.putConstraint(SpringLayout.WEST, label, 127, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.WEST, label_1, 6, SpringLayout.EAST, textField_kod_na);
		springLayout.putConstraint(SpringLayout.NORTH, textField_kod_na, -3, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.WEST, textField_kod_na, 0, SpringLayout.WEST, textField_ulica_na);
		springLayout.putConstraint(SpringLayout.EAST, textField_kod_na, -181, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, chckbx_mie_na, 0, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.EAST, chckbx_mie_na, -6, SpringLayout.WEST, lblNrMieszkania_1);
		springLayout.putConstraint(SpringLayout.WEST, textField_nr_mie_na, 569, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNrMieszkania, 6, SpringLayout.EAST, chckbx_mie_ad);
		springLayout.putConstraint(SpringLayout.WEST, lblDaneAdresata, 165, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField_nr_mie_na, -10, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNrMieszkania_1, 5, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.EAST, lblNrMieszkania_1, -6, SpringLayout.WEST, textField_nr_mie_na);
		springLayout.putConstraint(SpringLayout.WEST, textField_nr_mie_ad, 6, SpringLayout.EAST, lblNrMieszkania);
		springLayout.putConstraint(SpringLayout.EAST, textField_nr_mie_ad, -52, SpringLayout.WEST, lblNrDomu);
		springLayout.putConstraint(SpringLayout.NORTH, textField_nr_mie_na, 2, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.WEST, chckbx_mie_ad, 144, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField_nr_dom_na, -181, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, textField_nr_dom_na, 2, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.WEST, comboBox_miasto_ad, 50, SpringLayout.EAST, lblMiasto);
		springLayout.putConstraint(SpringLayout.EAST, comboBox_miasto_ad, -42, SpringLayout.WEST, lblMiasto_1);
		springLayout.putConstraint(SpringLayout.WEST, textField_ulica_ad, 59, SpringLayout.EAST, lblUlica);
		springLayout.putConstraint(SpringLayout.EAST, textField_ulica_ad, -42, SpringLayout.WEST, lblUlica_1);
		springLayout.putConstraint(SpringLayout.WEST, textField_kod_ad, 14, SpringLayout.EAST, lblNewLabel);
		springLayout.putConstraint(SpringLayout.EAST, textField_kod_ad, -6, SpringLayout.WEST, label);
		springLayout.putConstraint(SpringLayout.NORTH, textField_ulica_na, -3, SpringLayout.NORTH, lblUlica_1);
		springLayout.putConstraint(SpringLayout.WEST, textField_ulica_na, 418, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField_ulica_na, 0, SpringLayout.EAST, comboBox_miasto_na);
		springLayout.putConstraint(SpringLayout.NORTH, comboBox_miasto_na, -3, SpringLayout.NORTH, lblMiasto);
		springLayout.putConstraint(SpringLayout.EAST, comboBox_miasto_na, -10, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblKodPocztowy, 0, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblKodPocztowy, 0, SpringLayout.WEST, lblUlica_1);
		springLayout.putConstraint(SpringLayout.NORTH, lblNrDomu, 5, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.WEST, lblNrDomu, 0, SpringLayout.WEST, lblUlica_1);
		springLayout.putConstraint(SpringLayout.NORTH, lblUlica_1, 0, SpringLayout.NORTH, lblUlica);
		springLayout.putConstraint(SpringLayout.WEST, lblUlica_1, 0, SpringLayout.WEST, lblMiasto_1);
		springLayout.putConstraint(SpringLayout.NORTH, lblMiasto_1, 0, SpringLayout.NORTH, lblMiasto);
		springLayout.putConstraint(SpringLayout.SOUTH, lblMiasto, -23, SpringLayout.NORTH, lblUlica);
		springLayout.putConstraint(SpringLayout.NORTH, comboBox_miasto_ad, -3, SpringLayout.NORTH, lblMiasto);
		springLayout.putConstraint(SpringLayout.WEST, lblMiasto, 0, SpringLayout.WEST, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.NORTH, textField_ulica_ad, -3, SpringLayout.NORTH, lblUlica);
		springLayout.putConstraint(SpringLayout.NORTH, lblNrDomuI, 324, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblNrDomuI, -17, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblUlica, 0, SpringLayout.WEST, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.SOUTH, lblUlica, -17, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.NORTH, chckbx_mie_ad, 0, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.NORTH, textField_nr_mie_ad, 2, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.NORTH, lblNrMieszkania, 5, SpringLayout.NORTH, lblNrDomuI);
		springLayout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.NORTH, textField_kod_ad, -3, SpringLayout.NORTH, lblNewLabel);
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 366, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNrDomuI, 0, SpringLayout.WEST, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, lblPrzesyka);
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

		JLabel lblNazwisko = new JLabel("Nazwisko:");
		springLayout.putConstraint(SpringLayout.WEST, lblMiasto_1, 0, SpringLayout.WEST, lblNazwisko);
		frame.getContentPane().add(lblNazwisko);

		JLabel lblImie = new JLabel("Imie:");
		springLayout.putConstraint(SpringLayout.WEST, lblNazwisko, 0, SpringLayout.WEST, lblImie);
		frame.getContentPane().add(lblImie);

		textField_nazwisko_na = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField_nazwisko_na, 36, SpringLayout.EAST, lblNazwisko);
		springLayout.putConstraint(SpringLayout.EAST, textField_nazwisko_na, -10, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, comboBox_miasto_na, 0, SpringLayout.WEST, textField_nazwisko_na);
		springLayout.putConstraint(SpringLayout.NORTH, textField_nazwisko_na, -3, SpringLayout.NORTH, lblNazwisko);
		frame.getContentPane().add(textField_nazwisko_na);
		textField_nazwisko_na.setColumns(10);

		textField_imie_na = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField_imie_na, -3, SpringLayout.NORTH, lblImie);
		springLayout.putConstraint(SpringLayout.WEST, textField_imie_na, 60, SpringLayout.EAST, lblImie);
		springLayout.putConstraint(SpringLayout.EAST, textField_imie_na, -10, SpringLayout.EAST,
				frame.getContentPane());
		frame.getContentPane().add(textField_imie_na);
		textField_imie_na.setColumns(10);

		JLabel lblNazwisko_1 = new JLabel("Nazwisko:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNazwisko, 0, SpringLayout.NORTH, lblNazwisko_1);
		springLayout.putConstraint(SpringLayout.WEST, lblNazwisko_1, 0, SpringLayout.WEST, lblPrzesyka);
		springLayout.putConstraint(SpringLayout.SOUTH, lblNazwisko_1, -22, SpringLayout.NORTH, lblMiasto);
		frame.getContentPane().add(lblNazwisko_1);

		JLabel lblImie_1 = new JLabel("Imie:");
		springLayout.putConstraint(SpringLayout.NORTH, lblImie, 0, SpringLayout.NORTH, lblImie_1);
		springLayout.putConstraint(SpringLayout.WEST, lblImie_1, 0, SpringLayout.WEST, lblPrzesyka);
		frame.getContentPane().add(lblImie_1);

		textField_nazwisko_ad = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField_nazwisko_ad, -3, SpringLayout.NORTH, lblNazwisko_1);
		springLayout.putConstraint(SpringLayout.WEST, textField_nazwisko_ad, 0, SpringLayout.WEST, textField_kod_ad);
		springLayout.putConstraint(SpringLayout.EAST, textField_nazwisko_ad, -42, SpringLayout.WEST, lblNazwisko);
		frame.getContentPane().add(textField_nazwisko_ad);
		textField_nazwisko_ad.setColumns(10);

		textField_imie_ad = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField_imie_ad, 61, SpringLayout.EAST, lblImie_1);
		springLayout.putConstraint(SpringLayout.SOUTH, textField_imie_ad, -17, SpringLayout.NORTH,
				textField_nazwisko_ad);
		springLayout.putConstraint(SpringLayout.EAST, textField_imie_ad, -333, SpringLayout.EAST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblImie, 42, SpringLayout.EAST, textField_imie_ad);
		springLayout.putConstraint(SpringLayout.NORTH, lblImie_1, 3, SpringLayout.NORTH, textField_imie_ad);
		frame.getContentPane().add(textField_imie_ad);
		textField_imie_ad.setColumns(10);

		JLabel lblKiedyKurierMa = new JLabel("Kiedy kurier ma przyjecha by odebra\u0107 przesylke: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblKiedyKurierMa, 26, SpringLayout.SOUTH, textField_kod_na);
		springLayout.putConstraint(SpringLayout.WEST, lblKiedyKurierMa, 0, SpringLayout.WEST, lblUlica_1);
		frame.getContentPane().add(lblKiedyKurierMa);
		btnPowrot.addActionListener(myAction);
		btnZamwKuriera.addActionListener(myAction);

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
		private String imieAd;
		private String imieNa;
		private String nazwiskoAd;
		private String nazwiskoNa;

		@Override
		public void actionPerformed(ActionEvent e) {
			String co = ((JButton) e.getSource()).getText();
			ustawienieInputOutput();
			switch (co) {
			case "Zamow kuriera":
				if (komunikat_waga())
					break;
				else if (komunikat_ulica(textField_ulica_ad.getText()) || komunikat_ulica(textField_ulica_na.getText()))
					break;
				else if (komunikat_imie_ad(textField_imie_ad.getText())
						|| komunikat_imie_na(textField_imie_na.getText()))
					break;
				else if (komunikat_nazwisko_ad(textField_nazwisko_ad.getText())
						|| komunikat_nazwisko_na(textField_nazwisko_na.getText()))
					break;
				else if (komunikat_dom_ad(textField_nr_dom_ad.getText())
						|| komunikat_dom_na(textField_nr_dom_na.getText()))
					break;
				else if (komunikat_mieszkanie_ad(textField_nr_mie_ad.getText())
						|| komunikat_mieszkanie_na(textField_nr_mie_na.getText()))
					break;
				else if (komunikat_kod_ad(textField_kod_ad.getText())
						|| (komunikat_kod_ad2(textField_kod_ad1.getText())))
					break;
				else if (komunikat_kod_na(textField_kod_na.getText())
						|| (komunikat_kod_na2(textField_kod_na1.getText())))
					break;
				else if (komunikat_data_odebrania())
					break;
				else {
					String mess = "Wyslij Paczke";
					io(output, input, mess);
					String kodAd = kod1Ad + "-" + kod2Ad;
					String kodNa = kod1Na + "-" + kod2Na;
					int paczka_ID = (int) Double.parseDouble(odczytWiadomosciOdSerwera());
					Paczka p = new Paczka(paczka_ID, koszt, wagaWartosc, chckbxTak.isSelected(), rodzaj,
							rdbtnEkspresowa.isSelected(), (String) comboBox_miasto_ad.getSelectedItem(),
							textField_ulica_ad.getText(), domAd, mieAd, kodAd, imieAd, nazwiskoAd,
							(String) comboBox_miasto_na.getSelectedItem(), textField_ulica_na.getText(), domNa, mieNa,
							kodNa, imieNa, nazwiskoNa, selectedDate);
					JOptionPane.showMessageDialog(null, p.toString());
					JOptionPane.showMessageDialog(null,
							"Prosze zapisac numer przesylki!\nNumer przesylki: " + p.getPaczkaID());
					ObjectOutputStream oos;
					try {
						oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(p);
						oos.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frame.setVisible(false);
					new StronaGlowna(socket);
					break;
				}
			case "Powrot":
				int tmp = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz opuscic strone?");
				if (tmp == JOptionPane.YES_OPTION) {
					String mess = "Powrot";
					io(output, input, mess);
					frame.setVisible(false);
					new StronaGlowna(socket);
					break;
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

		private boolean komunikat_kod_ad(String kod) {
			try {
				if (kod.contains(textField_kod_ad.getText()) && kod.length() == 2)
					kod1Ad = (int) Double.parseDouble(kod);
				else if (kod.contains(textField_kod_ad.getText()) && kod.length() != 2) {
					JOptionPane.showMessageDialog(null, "Pierwsza czesc kodu pocztowego to dwie cyfry!");
					return true;
				}
			} catch (Exception exp) {
				if (kod.isEmpty())
					JOptionPane.showMessageDialog(null, "Uzupelnuj kod pocztowy!");
				else
					JOptionPane.showMessageDialog(null, "Kod musi byc liczba!");
				return true;
			}
			return false;
		}

		private boolean komunikat_kod_ad2(String kod) {
			try {
				if (kod.contains(textField_kod_ad1.getText()) && kod.length() == 3)
					kod2Ad = (int) Double.parseDouble(kod);
				else if (kod.contains(textField_kod_ad1.getText()) && kod.length() != 3) {
					JOptionPane.showMessageDialog(null, "Druga czesc kodu pocztowego to trzy cyfry!");
					return true;
				}
			} catch (Exception exp) {
				if (kod.isEmpty())
					JOptionPane.showMessageDialog(null, "Uzupelnuj kod pocztowy!");
				else
					JOptionPane.showMessageDialog(null, "Kod musi byc liczba!");
				return true;
			}
			return false;
		}

		private boolean komunikat_kod_na(String kod) {
			try {
				if (kod.contains(textField_kod_na.getText()) && kod.length() == 2)
					kod1Na = (int) Double.parseDouble(kod);
				else if (kod.contains(textField_kod_na.getText()) && kod.length() != 2) {
					JOptionPane.showMessageDialog(null, "Pierwsza czesc kodu pocztowego to dwie cyfry!");
					return true;
				}
			} catch (Exception exp) {
				if (kod.isEmpty())
					JOptionPane.showMessageDialog(null, "Uzupelnuj kod pocztowy!");
				else
					JOptionPane.showMessageDialog(null, "Kod musi byc liczba!");
				return true;
			}
			return false;
		}

		private boolean komunikat_kod_na2(String kod) {
			try {
				if (kod.contains(textField_kod_na1.getText()) && kod.length() == 3)
					kod2Na = (int) Double.parseDouble(kod);
				else if (kod.contains(textField_kod_na1.getText()) && kod.length() != 3) {
					JOptionPane.showMessageDialog(null, "Druga czesc kodu pocztowego to trzy cyfry!");
					return true;
				}
			} catch (Exception exp) {
				if (kod.isEmpty())
					JOptionPane.showMessageDialog(null, "Uzupelnuj kod pocztowy!");
				else
					JOptionPane.showMessageDialog(null, "Kod musi byc liczba!");
				return true;
			}
			return false;
		}

		private boolean komunikat_imie_ad(String imie) {
			try {
				imieAd = imie;
				Double.parseDouble(imie);
			} catch (Exception exp) {
				if (imie.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Podaj imie adresata!");
					return true;
				} else
					return false;
			}
			JOptionPane.showMessageDialog(null, "Imie nie moze byc liczba!");
			return true;
		}

		private boolean komunikat_imie_na(String imie) {
			try {
				imieNa = imie;
				Double.parseDouble(imie);
			} catch (Exception exp) {
				if (imie.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Podaj imie nadawcy!");
					return true;
				} else
					return false;
			}
			JOptionPane.showMessageDialog(null, "Imie nie moze byc liczba!");
			return true;
		}

		private boolean komunikat_nazwisko_ad(String nazwisko) {
			try {
				nazwiskoAd = nazwisko;
				Double.parseDouble(nazwisko);
			} catch (Exception exp) {
				if (nazwisko.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Podaj nazwisko adresata!");
					return true;
				} else
					return false;
			}
			JOptionPane.showMessageDialog(null, "Nazwisko nie moze byc liczba!");
			return true;
		}

		private boolean komunikat_nazwisko_na(String nazwisko) {
			try {
				nazwiskoNa = nazwisko;
				Double.parseDouble(nazwisko);
			} catch (Exception exp) {
				if (nazwisko.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Podaj nazwisko nadawcy!");
					return true;
				} else
					return false;
			}
			JOptionPane.showMessageDialog(null, "Nazwisko nie moze byc liczba!");
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

		private boolean komunikat_dom_ad(String dom) {
			try {
				domAd = (int) Double.parseDouble(dom);
			} catch (Exception exp) {
				if (dom.isEmpty())
					JOptionPane.showMessageDialog(null, "Uzupelnij numer domu adresata!");
				else
					JOptionPane.showMessageDialog(null, "Numer domu adresata musi byc liczba!");
				return true;
			}
			return false;
		}

		private boolean komunikat_dom_na(String dom) {
			try {
				domNa = (int) Double.parseDouble(dom);
			} catch (Exception exp) {
				if (dom.isEmpty())
					JOptionPane.showMessageDialog(null, "Uzupelnij numer domu nadawcy!");
				else
					JOptionPane.showMessageDialog(null, "Numer domu nadawcy musi byc liczba!");
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

		private boolean komunikat_data_odebrania() {

			if (selectedDate == aktualnaData) {
				JOptionPane.showMessageDialog(null,
						"Nasza firma nie przyjmuje zamówieñ na aktualny dzieñ ani nie \njest w stanie cofn¹æ siê w czasie. Za problemy przepraszamy.\n:(");
				return true;
			} else if (selectedDate == null) {
				JOptionPane.showMessageDialog(null,
						"Nasza firma nie przyjmuje zamówieñ na aktualny dzieñ ani nie \njest w stanie cofn¹æ siê w czasie. Za problemy przepraszamy.\n:(");
				return true;
			} else if (aktualnaData.after(selectedDate)) {
				JOptionPane.showMessageDialog(null,
						"Nasza firma nie przyjmuje zamówieñ na aktualny dzieñ ani nie \njest w stanie cofn¹æ siê w czasie. Za problemy przepraszamy.\n:(");
				return true;
			} else
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
