import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Gui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	public Gui() {
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setText("0");
		textField.setBounds(10, 11, 44, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		CandyCount count = new CandyCount(textField);
		JButton btnNewButton = new JButton("Click For More Candy!");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				count.addCandy();
			}
		});
		btnNewButton.setBounds(128, 231, 193, 20);
		contentPane.add(btnNewButton);
		loadDependencies();

	}
}
