package proba1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SpringLayout;

public class StronaGlowna {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Create the application.
	 */
	public StronaGlowna() {
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

		JLabel lblNewLabel = new JLabel("Witaj na stronie kurierskiej Pio£uk MajMañ XD");

		JLabel lblCoChciabyWykona = new JLabel("Co chcia\u0142by\u015B zrobi\u0107?");

		JButton btnlogowanie = new JButton("1. Logowanie");
		btnlogowanie.addActionListener(myAction);
		JButton btnwyslijPaczke = new JButton("2. Wyslij paczke");
		btnwyslijPaczke.addActionListener(myAction);
		JButton btnznajdzSwojaPaczke = new JButton("3. Znajdz swoja paczke");
		btnznajdzSwojaPaczke.addActionListener(myAction);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, btnznajdzSwojaPaczke, 6, SpringLayout.SOUTH, btnwyslijPaczke);
		springLayout.putConstraint(SpringLayout.EAST, btnwyslijPaczke, 0, SpringLayout.EAST, btnlogowanie);
		springLayout.putConstraint(SpringLayout.WEST, btnznajdzSwojaPaczke, 6, SpringLayout.WEST,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnwyslijPaczke, 6, SpringLayout.SOUTH, btnlogowanie);
		springLayout.putConstraint(SpringLayout.WEST, btnwyslijPaczke, 6, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnlogowanie, 0, SpringLayout.EAST, btnznajdzSwojaPaczke);
		springLayout.putConstraint(SpringLayout.NORTH, lblCoChciabyWykona, 23, SpringLayout.NORTH,
				frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblCoChciabyWykona, 6, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 6, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblNewLabel, 106, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnlogowanie, 40, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnlogowanie, 6, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().add(btnlogowanie);
		frame.getContentPane().add(btnwyslijPaczke);
		frame.getContentPane().add(btnznajdzSwojaPaczke);
		
				JButton btnwyjscie = new JButton("4. Wyjscie");
				springLayout.putConstraint(SpringLayout.NORTH, btnwyjscie, 6, SpringLayout.SOUTH, btnznajdzSwojaPaczke);
				springLayout.putConstraint(SpringLayout.WEST, btnwyjscie, 6, SpringLayout.WEST, frame.getContentPane());
				springLayout.putConstraint(SpringLayout.EAST, btnwyjscie, 0, SpringLayout.EAST, btnlogowanie);
				btnwyjscie.addActionListener(myAction);
				frame.getContentPane().add(btnwyjscie);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(lblCoChciabyWykona);
	}

	private class MyActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = true;

			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "1. Logowanie":
				frame.setVisible(false);
				new Logowanie();

				break;
			case "2. Wyslij paczke":
				frame.setVisible(false);
				new WyslijPaczke();
				break;
			case "3. Znajdz swoja paczke":
				frame.setVisible(false);
				new ZnajdzSwojaPaczke();
				break;
			case "4. Wyjscie":
				int tmp = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz opuscic program?");
				if (tmp == JOptionPane.YES_OPTION) {
					frame.setVisible(false);
					System.exit(0);
					break;
				}
			}

		}
	}
}
