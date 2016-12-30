package proba1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import javax.swing.JLabel;
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

public class WyslijPaczke {

	private JFrame frame;
	private JPanel panel;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private final ButtonGroup buttonGroup = new ButtonGroup();

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
		frame.setResizable(false);
		panel = new JPanel();
		panel.setAutoscrolls(true);
		frame.setBounds(100, 100, 595, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		panel.setLayout(springLayout);
		
		JLabel lblWaga = new JLabel("Waga:");
		springLayout.putConstraint(SpringLayout.NORTH, lblWaga, 13, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblWaga, 10, SpringLayout.WEST, frame.getContentPane());
		panel.add(lblWaga);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, -3, SpringLayout.NORTH, lblWaga);
		springLayout.putConstraint(SpringLayout.WEST, textField, 44, SpringLayout.EAST, lblWaga);
		panel.add(textField);
		textField.setColumns(10);
		
	
		JLabel lblSzklo = new JLabel("Szklo:");
		springLayout.putConstraint(SpringLayout.NORTH, lblSzklo, 14, SpringLayout.SOUTH, lblWaga);
		springLayout.putConstraint(SpringLayout.WEST, lblSzklo, 0, SpringLayout.WEST, lblWaga);
		panel.add(lblSzklo);
		
		JCheckBox chckbxTak = new JCheckBox("Tak");
		springLayout.putConstraint(SpringLayout.NORTH, chckbxTak, -4, SpringLayout.NORTH, lblSzklo);
		springLayout.putConstraint(SpringLayout.WEST, chckbxTak, 0, SpringLayout.WEST, textField);
		panel.add(chckbxTak);
		
		JLabel lblRodzaj = new JLabel("Rodzaj:");
		springLayout.putConstraint(SpringLayout.NORTH, lblRodzaj, 14, SpringLayout.SOUTH, lblSzklo);
		springLayout.putConstraint(SpringLayout.WEST, lblRodzaj, 0, SpringLayout.WEST, lblWaga);
		panel.add(lblRodzaj);
		
		JRadioButton rdbtnList = new JRadioButton("List");
		buttonGroup.add(rdbtnList);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnList, -4, SpringLayout.NORTH, lblRodzaj);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnList, 0, SpringLayout.WEST, textField);
		panel.add(rdbtnList);
		
		JRadioButton rdbtnPaczka = new JRadioButton("Paczka");
		buttonGroup.add(rdbtnPaczka);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnPaczka, -4, SpringLayout.NORTH, lblRodzaj);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnPaczka, 6, SpringLayout.EAST, rdbtnList);
		panel.add(rdbtnPaczka);
		
		JRadioButton rdbtnCosInnego = new JRadioButton("Cos innego");
		buttonGroup.add(rdbtnCosInnego);
		springLayout.putConstraint(SpringLayout.NORTH, rdbtnCosInnego, -4, SpringLayout.NORTH, lblRodzaj);
		springLayout.putConstraint(SpringLayout.WEST, rdbtnCosInnego, 6, SpringLayout.EAST, rdbtnPaczka);
		panel.add(rdbtnCosInnego);
		
		JLabel lblKg = new JLabel("kg");
		springLayout.putConstraint(SpringLayout.NORTH, lblKg, 0, SpringLayout.NORTH, lblWaga);
		springLayout.putConstraint(SpringLayout.WEST, lblKg, 6, SpringLayout.EAST, textField);
		panel.add(lblKg);
		
		JButton btnPowrot = new JButton("Powrot");
		springLayout.putConstraint(SpringLayout.WEST, btnPowrot, 0, SpringLayout.WEST, lblWaga);
		springLayout.putConstraint(SpringLayout.SOUTH, btnPowrot, -10, SpringLayout.SOUTH, frame.getContentPane());
		panel.add(btnPowrot);
		btnPowrot.addActionListener(myAction);
		
		JButton btnWyslijZamowienie = new JButton("Wyslij zamowienie");
		btnWyslijZamowienie.addActionListener(myAction);

		springLayout.putConstraint(SpringLayout.EAST, textField, 0, SpringLayout.EAST, btnWyslijZamowienie);
		springLayout.putConstraint(SpringLayout.WEST, btnWyslijZamowienie, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnWyslijZamowienie, -6, SpringLayout.NORTH, btnPowrot);
		panel.add(btnWyslijZamowienie);
		
		JLabel lblDaneAdresata = new JLabel("Dane adresata:");
		panel.add(lblDaneAdresata);
		
		JLabel lblDaneNadawcy = new JLabel("Dane nadawcy:");
		springLayout.putConstraint(SpringLayout.WEST, lblDaneAdresata, 0, SpringLayout.WEST, lblDaneNadawcy);
		springLayout.putConstraint(SpringLayout.WEST, lblDaneNadawcy, 156, SpringLayout.WEST, frame.getContentPane());
		panel.add(lblDaneNadawcy);
		
		JLabel lblMiasto = new JLabel("Miasto:");
		springLayout.putConstraint(SpringLayout.NORTH, lblMiasto, 43, SpringLayout.SOUTH, lblRodzaj);
		springLayout.putConstraint(SpringLayout.WEST, lblMiasto, 0, SpringLayout.WEST, lblWaga);
		panel.add(lblMiasto);
		
		textField_1 = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField_1, 52, SpringLayout.EAST, lblMiasto);
		springLayout.putConstraint(SpringLayout.EAST, textField_1, -168, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, lblDaneAdresata, -6, SpringLayout.NORTH, textField_1);
		springLayout.putConstraint(SpringLayout.NORTH, textField_1, -3, SpringLayout.NORTH, lblMiasto);
		panel.add(textField_1);
		//textField_1.setColumns(10);
		
		JLabel lblUlica = new JLabel("Ulica:");
		springLayout.putConstraint(SpringLayout.NORTH, lblUlica, 15, SpringLayout.SOUTH, lblMiasto);
		springLayout.putConstraint(SpringLayout.WEST, lblUlica, 10, SpringLayout.WEST, frame.getContentPane());
		panel.add(lblUlica);
		
		textField_2 = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, textField_2, 61, SpringLayout.EAST, lblUlica);
		springLayout.putConstraint(SpringLayout.EAST, textField_2, 0, SpringLayout.EAST, textField_1);
		springLayout.putConstraint(SpringLayout.NORTH, textField_2, -3, SpringLayout.NORTH, lblUlica);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblKodPocztowy = new JLabel("Kod pocztowy:");
		springLayout.putConstraint(SpringLayout.NORTH, lblKodPocztowy, 16, SpringLayout.SOUTH, lblUlica);
		springLayout.putConstraint(SpringLayout.WEST, lblKodPocztowy, 0, SpringLayout.WEST, lblWaga);
		panel.add(lblKodPocztowy);
		
		textField_3 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, lblDaneNadawcy, 10, SpringLayout.SOUTH, textField_3);
		springLayout.putConstraint(SpringLayout.NORTH, textField_3, -3, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.WEST, textField_3, 0, SpringLayout.WEST, textField_1);
		springLayout.putConstraint(SpringLayout.EAST, textField_3, 0, SpringLayout.EAST, textField_1);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel label = new JLabel("Miasto:");
		springLayout.putConstraint(SpringLayout.NORTH, label, 36, SpringLayout.SOUTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.WEST, label, 0, SpringLayout.WEST, lblWaga);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Ulica:");
		springLayout.putConstraint(SpringLayout.NORTH, label_1, 0, SpringLayout.NORTH, lblKodPocztowy);
		springLayout.putConstraint(SpringLayout.WEST, label_1, 66, SpringLayout.EAST, textField_3);
		panel.add(label_1); 
		
		JScrollPane jsp = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(jsp);
		
		
	}
	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = true;

			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "Wyslij zamowienie":
				frame.setVisible(false);
				break;
			case "Powrot":
				frame.setVisible(false);
				new StronaGlowna();
				break;
			}

		}
	}
}
