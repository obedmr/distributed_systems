package view;

import java.awt.EventQueue;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard {

	public JFrame frame;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard window = new Dashboard();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Dashboard() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		int i;
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JTabbedPane homeworks = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(homeworks);
		
		JPanel generalPanel = new JPanel();
		homeworks.addTab("General", null, generalPanel, null);
		
		JPanel pnlHomework2 = new JPanel();
		homeworks.addTab("Homework 2", null, pnlHomework2, null);
		pnlHomework2.setLayout(null);
		
		JComboBox cmbHomework2 = new JComboBox();
		for(i=1; i<6; i++)
			cmbHomework2.addItem("Practice "+i);
		cmbHomework2.setBounds(117, 42, 150, 27);
		
		pnlHomework2.add(cmbHomework2);
		
		JButton btnHomework2 = new JButton("RUN");
		btnHomework2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("You want to run: "+cmbHomework2.getSelectedItem());
			}
		});
		btnHomework2.setBounds(117, 175, 150, 29);
		pnlHomework2.add(btnHomework2);
		
		JLabel lblHomework2 = new JLabel("Select Practice: ");
		lblHomework2.setBounds(6, 46, 95, 16);
		pnlHomework2.add(lblHomework2);
		
	}
}
