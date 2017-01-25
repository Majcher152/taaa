package proba1;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
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
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.UIManager;

public class StronaGlowna {

	private JFrame frame;
	private JTextField textField;
	private PrintWriter output = null;
	private BufferedReader input = null;
	private Socket socket = null;
	private boolean flag = true;


	/**
	 * Create the application.
	 */
	public StronaGlowna(Socket socket) {
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
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setBackground(SystemColor.activeCaption);
		frame.setForeground(SystemColor.inactiveCaptionBorder);
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

		JButton btnwyjscie = new JButton("4. Wyjscie");
		btnwyjscie.setBackground(UIManager.getColor("ToggleButton.light"));
		btnwyjscie.addActionListener(myAction);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(106)
					.addComponent(lblNewLabel))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(lblCoChciabyWykona))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(33)
					.addComponent(btnlogowanie, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnznajdzSwojaPaczke, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
						.addComponent(btnwyslijPaczke, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
						.addComponent(btnwyjscie, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(32))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(6)
					.addComponent(lblNewLabel)
					.addGap(3)
					.addComponent(lblCoChciabyWykona)
					.addGap(18)
					.addComponent(btnlogowanie)
					.addGap(11)
					.addComponent(btnwyslijPaczke)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnznajdzSwojaPaczke)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnwyjscie)
					.addGap(81))
		);
		frame.getContentPane().setLayout(groupLayout);
	}

	private class MyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ustawienieInputOutput();
			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "1. Logowanie":
				frame.setVisible(false);
				new Logowanie(socket);
				break;
			case "2. Wyslij paczke":
				frame.setVisible(false);
				 new WyslijPaczke(socket);
				break;
			case "3. Znajdz swoja paczke":
				frame.setVisible(false);
				 new ZnajdzSwojaPaczke(socket);
				break;
			case "4. Wyjscie":
				int tmp = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz opuscic program?");
				if (tmp == JOptionPane.YES_OPTION) {
					String mess = "Wyjscie";
					io(output,input,mess);
					frame.setVisible(false);
					System.exit(0);
					break;
				}
			}

		}
		private void ustawienieInputOutput()
		{
			try {
				output = new PrintWriter(socket.getOutputStream(), true);
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		private void io(PrintWriter output, BufferedReader input, String mess)
		{	
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
	}
}
