import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class MP3GUI extends JFrame{
	
	JTextArea messageArea;
	String[] options = new String[]{"None", "Rotational", "Keyed Rotational"};
	JComboBox<String> modeBox;
	JButton encryptButton;
	JButton decryptButton;
	JPanel encryptDecryptPanel;
	JTextArea decryptArea;
	String[] rotations = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25".split(" ");
	JComboBox<String> rotationsBox;
	JPanel rotationalPanel;
	JPanel keyedRotationalPanel;
	JPanel keyPanel;
	JLabel keyLabel;
	JTextArea keyArea;
	JPanel rotationPanel;
	JLabel rotationLabel;
	
	

	public MP3GUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("MP3");
		
		//Top
		messageArea = new JTextArea(5,40);
		
		//Middle
		modeBox = new JComboBox<String>(options);
		modeBox.addActionListener(new ModeHandler());
		
		//Bottom
		
		//Encrypt Decrypt Buttons
		encryptButton = new JButton("Encrypt");
		encryptButton.addActionListener(new EncryptHander());
		decryptButton = new JButton("Decrypt");
		decryptButton.addActionListener(new DecryptHandler());
		encryptDecryptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		encryptDecryptPanel.add(encryptButton);
		encryptDecryptPanel.add(decryptButton);
		
		//Area for decrypted Message
		decryptArea = new JTextArea(5,40);
		decryptArea.setEditable(false);
		
		//Rotational Only
		rotationsBox = new JComboBox<String>(rotations);
		rotationLabel = new JLabel("Rotation: ");
		rotationPanel = new JPanel();
		rotationPanel.setLayout(new BoxLayout(rotationPanel, BoxLayout.X_AXIS));
		rotationPanel.add(rotationLabel);
		rotationPanel.add(rotationsBox);
		
		//Key Only
		keyLabel = new JLabel("Key:");
		keyArea = new JTextArea();
		keyPanel = new JPanel();
		keyPanel.setLayout(new BoxLayout(keyPanel, BoxLayout.X_AXIS));
		keyPanel.add(keyLabel);
		keyPanel.add(keyArea);
		
		//Rotational Panel -- The big picture
		rotationalPanel = new JPanel();
		rotationalPanel.setLayout(new BoxLayout(rotationalPanel, BoxLayout.Y_AXIS));
		rotationalPanel.add(rotationPanel,BorderLayout.NORTH);
		rotationalPanel.add(decryptArea, BorderLayout.CENTER);
		rotationalPanel.add(encryptDecryptPanel,BorderLayout.SOUTH);
		
		//Keyed Rotational Panel
		
		keyedRotationalPanel = new JPanel();
		keyedRotationalPanel.setLayout(new BoxLayout(keyedRotationalPanel, BoxLayout.Y_AXIS));
		keyedRotationalPanel.add(rotationPanel);
		keyedRotationalPanel.add(keyPanel);
		keyedRotationalPanel.add(decryptArea);
		keyedRotationalPanel.add(encryptDecryptPanel);

		setLayoutNone();
	}
	
	public void setLayoutNone(){
		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
		c.add(messageArea);
		c.add(modeBox);
		pack();
	}
	
	public void setLayoutRotational(){
		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
		c.add(messageArea);
		c.add(modeBox);
		c.add(rotationalPanel);
		pack();
		
	}
	
	public void setLayoutKeyedRotational(){
		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
		c.add(messageArea);
		c.add(modeBox);
		c.add(keyedRotationalPanel);
		pack();
	}
	
	
	public class EncryptHander implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	public class DecryptHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	public class ModeHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			int index = modeBox.getSelectedIndex();
			switch(index){
			case 0:
				setLayoutNone();
				break;
			case 1:
				setLayoutRotational();
				break;
			case 2: 
				setLayoutKeyedRotational();
				break;
			}
		}
	}

}
