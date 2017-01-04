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
import java.io.PrintWriter;
import java.net.Socket;

public class ZalogujKurier {

	private JFrame frame;
	private String login;
	private PrintWriter output = null;
	private BufferedReader input = null;
	private Socket socket = null;
	private boolean flag = true;

	public ZalogujKurier(String login,Socket socket) {
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

		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JTextArea textArea = new JTextArea();
		springLayout.putConstraint(SpringLayout.NORTH, textArea, 326, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textArea, -147, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textArea, -10, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(textArea);

		//textArea.setText(login + " " + haslo.toString());
		textArea.setText(login + " ");
		
		
		JButton btnWyloguj = new JButton("Wyloguj");
		springLayout.putConstraint(SpringLayout.EAST, textArea, 0, SpringLayout.EAST, btnWyloguj);
		springLayout.putConstraint(SpringLayout.NORTH, btnWyloguj, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnWyloguj, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnWyloguj);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Konto", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, -422, SpringLayout.WEST, textArea);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 349, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -2, SpringLayout.WEST, textArea);
		frame.getContentPane().add(panel);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Do odebrania", "Do dostarczenia" }));

		JLabel lblPrzesylka = new JLabel("Przesylki:");

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addGap(3).addComponent(lblPrzesylka).addGap(18)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane,
								GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)))
						.addGap(0)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPrzesylka))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE).addGap(4)));

		JList list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String message = (String) list.getSelectedValue();
				JOptionPane.showMessageDialog(null, message);
			}
		});

		list.setModel(new AbstractListModel() {
			String[] values = new String[] { "aa", "bb", "2", "4", "5", "6" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		panel.setLayout(gl_panel);

		btnWyloguj.addActionListener(myAction);
		
		try {
			output = new PrintWriter(socket.getOutputStream(), true);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output.println("ZalogujKurier");
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

		@Override
		public void actionPerformed(ActionEvent e) {
			String co = ((JButton) e.getSource()).getText();
			switch (co) {
			case "Wyloguj":
				int tmp = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz sie wylogowac?");
				if (tmp == JOptionPane.YES_OPTION) {
					frame.setVisible(false);
					new StronaGlowna(socket);
					break;
				}
			}
		}
	}
}
// paczki do odbioru
// paczki do zamowienia od klineta