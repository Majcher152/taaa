package proba1;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SpringLayout;
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
import java.awt.event.MouseWheelEvent;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class WyslijPaczke {

	private JFrame frame;
	private JTextField textField_waga;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JTextField textField_miasto_ad;
	private JTextField textField_adres_ad;
	private JTextField textField_kod_ad;
	private JTextField textField_miasto_na;
	private JTextField textField_adres_na;
	private JTextField textField_koszt;
	private JButton btnZamwKuriera;
	private JButton btnPowrot;
	private JTextField textField_kod_ad1;
	private JTextField textField_kod_na;
	private JTextField textField_kod_na1;

	/**
	 * Create the application.
	 */
	public WyslijPaczke() {
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
		frame.setBounds(100, 100, 500, 547);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblWaga = new JLabel("Waga:");

		textField_waga = new JTextField();
		textField_waga.setToolTipText("Podaj wage swojej przesy\u0142ki w kilogramach");
		textField_waga.setColumns(10);

		JLabel lblKg = new JLabel("kg");
		JLabel lblSzklo = new JLabel("Szklo:");
		JCheckBox chckbxTak = new JCheckBox("Tak");
		JLabel lblRodzaj = new JLabel("Rodzaj:");

		JRadioButton rdbtnList = new JRadioButton("Mala przesylka");
		buttonGroup.add(rdbtnList);
		JRadioButton rdbtnPaczka = new JRadioButton("Srednia przesylka");
		buttonGroup.add(rdbtnPaczka);
		JRadioButton rdbtnCosInnego = new JRadioButton("Duza przesylka");
		rdbtnCosInnego.setSelected(true);
		buttonGroup.add(rdbtnCosInnego);

		JLabel lblPrzesyka = new JLabel("Przesylka:");

		JRadioButton rdbtnEkspresowa = new JRadioButton("ekspresowa");
		rdbtnEkspresowa.setSelected(true);
		buttonGroup_1.add(rdbtnEkspresowa);
		JRadioButton rdbtnZwyka = new JRadioButton("zwykla");
		buttonGroup_1.add(rdbtnZwyka);

		JLabel lblDaneAdresata = new JLabel("Dane adresata:");
		JLabel lblMiasto = new JLabel("Miasto:");
		textField_miasto_ad = new JTextField();
		textField_miasto_ad.setColumns(10);
		JLabel lblUlica = new JLabel("Adres:");
		textField_adres_ad = new JTextField();
		textField_adres_ad.setColumns(10);
		JLabel lblNewLabel = new JLabel("Kod pocztowy:");
		textField_kod_ad = new JTextField("", 2);
		textField_kod_ad.setColumns(2);
		JLabel label = new JLabel("-");
		textField_kod_ad1 = new JTextField("", 3);
		textField_kod_ad1.setColumns(3);

		JLabel lblDaneNadawcy = new JLabel("Dane nadawcy:");
		JLabel lblMiasto_1 = new JLabel("Miasto:");
		JLabel lblUlica_1 = new JLabel("Adres:");
		JLabel lblKodPocztowy = new JLabel("Kod pocztowy:");
		textField_miasto_na = new JTextField();
		textField_miasto_na.setColumns(10);
		textField_adres_na = new JTextField();
		textField_adres_na.setColumns(10);

		JLabel lblKoszt = new JLabel("Koszt:");
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

		JLabel label_1 = new JLabel("-");

		textField_kod_na1 = new JTextField();
		textField_kod_na1.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout
								.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout
												.createSequentialGroup().addGap(201).addComponent(lblDaneAdresata))
										.addGroup(groupLayout.createSequentialGroup().addGap(204)
												.addComponent(lblDaneNadawcy))
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup().addGap(10)
																.addComponent(lblMiasto))
														.addGroup(groupLayout.createSequentialGroup().addContainerGap()
																.addComponent(lblUlica))
														.addGroup(groupLayout.createSequentialGroup().addContainerGap()
																.addComponent(lblNewLabel)))
												.addGap(22)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(textField_adres_ad, GroupLayout.PREFERRED_SIZE,
																201, GroupLayout.PREFERRED_SIZE)
														.addComponent(textField_miasto_ad, GroupLayout.PREFERRED_SIZE,
																201, GroupLayout.PREFERRED_SIZE)
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(textField_kod_ad,
																		GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(label)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(textField_kod_ad1,
																		GroupLayout.PREFERRED_SIZE, 26,
																		GroupLayout.PREFERRED_SIZE))))
										.addGroup(groupLayout.createSequentialGroup().addGap(10)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(lblUlica_1).addComponent(lblKodPocztowy)
														.addComponent(lblMiasto_1))
												.addGap(22)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(textField_adres_na, GroupLayout.PREFERRED_SIZE,
																201, GroupLayout.PREFERRED_SIZE)
														.addComponent(textField_miasto_na, GroupLayout.PREFERRED_SIZE,
																201, GroupLayout.PREFERRED_SIZE)
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(lblKoszt).addGap(18)
																.addComponent(textField_koszt,
																		GroupLayout.PREFERRED_SIZE, 43,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(lblZ))
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(textField_kod_na,
																		GroupLayout.PREFERRED_SIZE, 20,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(label_1)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(textField_kod_na1,
																		GroupLayout.PREFERRED_SIZE, 26,
																		GroupLayout.PREFERRED_SIZE))))
										.addGroup(groupLayout.createSequentialGroup().addContainerGap()
												.addComponent(btnZamwKuriera))
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addGroup(groupLayout.createSequentialGroup().addGap(10)
																.addComponent(lblPrzesyka))
														.addGroup(groupLayout.createSequentialGroup().addContainerGap()
																.addComponent(lblWaga))
														.addGroup(groupLayout.createSequentialGroup().addContainerGap()
																.addComponent(lblSzklo))
														.addGroup(groupLayout.createSequentialGroup().addContainerGap()
																.addComponent(lblRodzaj)))
												.addGap(44)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
														.addComponent(chckbxTak)
														.addGroup(groupLayout.createSequentialGroup()
																.addComponent(textField_waga,
																		GroupLayout.PREFERRED_SIZE, 43,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(lblKg))
														.addGroup(groupLayout.createSequentialGroup()
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(rdbtnEkspresowa).addGap(18)
																.addComponent(rdbtnZwyka))
														.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																.addComponent(btnPowrot).addGroup(groupLayout
																		.createSequentialGroup().addComponent(rdbtnList)
																		.addGap(6).addComponent(rdbtnPaczka).addGap(6)
																		.addComponent(rdbtnCosInnego))))))
								.addContainerGap(68, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblWaga)
								.addComponent(textField_waga, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblKg))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(35)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(rdbtnPaczka).addComponent(rdbtnList))
												.addComponent(rdbtnCosInnego)))
								.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblSzklo).addComponent(chckbxTak))
										.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblRodzaj)))
						.addGap(6)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(2).addComponent(lblPrzesyka))
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(rdbtnZwyka)
										.addComponent(rdbtnEkspresowa)))
						.addGap(15).addComponent(lblDaneAdresata).addGap(19)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblMiasto)
								.addComponent(textField_miasto_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(17)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblUlica)
								.addComponent(textField_adres_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_kod_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel).addComponent(label).addComponent(textField_kod_ad1,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(15).addComponent(lblDaneNadawcy).addGap(14)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblMiasto_1)
								.addComponent(textField_miasto_na, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(3).addComponent(lblUlica_1))
								.addComponent(textField_adres_na, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(20)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblKodPocztowy)
								.addComponent(textField_kod_na, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1).addComponent(textField_kod_na1, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(25)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblKoszt)
								.addComponent(textField_koszt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblZ))
						.addGap(18).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnZamwKuriera).addComponent(btnPowrot))
						.addGap(43)));
		frame.getContentPane().setLayout(groupLayout);
		btnPowrot.addActionListener(myAction);
		btnZamwKuriera.addActionListener(myAction);

	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "Zamow kuriera":
				if (komunikat_waga())
					break;
				if (komunikat_miasto(textField_miasto_ad.getText()) || komunikat_miasto(textField_miasto_na.getText()))
					break;
				if (komunikat_adres(textField_adres_ad.getText()) || komunikat_adres(textField_adres_na.getText()))
					break;
				if (komunikat_kod(textField_kod_ad.getText()) || (komunikat_kod(textField_kod_ad1.getText())))
					break;
				if (komunikat_kod(textField_kod_na.getText()) || (komunikat_kod(textField_kod_na1.getText())))
					break;

				break;
			case "Powrot":
				int tmp = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz opuscic strone?");
				if (tmp == JOptionPane.YES_OPTION) {
					frame.setVisible(false);
					new StronaGlowna();
					break;
				}
			}
		}

		private boolean komunikat_waga() {
			String waga = textField_waga.getText();
			double as = 0;
			try {
				as = Double.parseDouble(textField_waga.getText());
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
			if (as <= 0) {
				JOptionPane.showMessageDialog(null, "Przesylka musi posiadac wage wieksza od 0");
				return true;
			} else if (as > 1000) {
				JOptionPane.showMessageDialog(null, "Nasza firma nie oferuje transportu dla przesylek powyzej 1000kg");
				return true;
			} else
				return false;
		}

		private boolean komunikat_kod(String kod) {
			try {
				Double.parseDouble(kod);
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

		private boolean komunikat_adres(String adres) {
			try {
				Double.parseDouble(adres);
			} catch (Exception exp) {
				if (adres.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Podaj adres!");
					return true;
				} else
					return false;
			}
			return false;
		}
	}
}
