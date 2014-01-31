import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	JPanel keyAndRotationPanel;
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
		messageArea = new JTextArea();
		messageArea.setMaximumSize(new Dimension(500,100));
		messageArea.setPreferredSize(new Dimension(500,100));
		
		//Middle
		modeBox = new JComboBox<String>(options);
		modeBox.addActionListener(new ModeHandler());
		modeBox.setMaximumSize(new Dimension(500,25));
		modeBox.setPreferredSize(new Dimension(500,25));
		
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
		decryptArea = new JTextArea();
		decryptArea.setEditable(false);
		
		//Rotational Only
		rotationsBox = new JComboBox<String>(rotations);
		rotationLabel = new JLabel("Rotation: ");
		rotationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		rotationPanel.add(rotationLabel);
		rotationPanel.add(rotationsBox);
		
		//Key Only
		keyLabel = new JLabel("Key:");
		keyArea = new JTextArea();
		keyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		keyPanel.add(keyLabel);
		keyPanel.add(keyArea);
		
		//Rotational Panel -- The big picture
		rotationalPanel = new JPanel();
		rotationalPanel.setLayout(new BorderLayout());
		rotationalPanel.add(rotationPanel,BorderLayout.NORTH);
		rotationalPanel.add(decryptArea, BorderLayout.CENTER);
		rotationalPanel.add(encryptDecryptPanel,BorderLayout.SOUTH);
		rotationalPanel.setPreferredSize(new Dimension(500,200));
		rotationalPanel.setMaximumSize(new Dimension(500,200));
		
		//Keyed Rotational Panel
		keyAndRotationPanel = new JPanel(new GridLayout(1,2));
		keyAndRotationPanel.add(keyLabel);
		keyAndRotationPanel.add(keyArea);
		keyedRotationalPanel = new JPanel(new BorderLayout());
		keyedRotationalPanel.add(keyAndRotationPanel,BorderLayout.NORTH);
		keyedRotationalPanel.add(decryptArea, BorderLayout.CENTER);
		keyedRotationalPanel.add(encryptDecryptPanel,BorderLayout.SOUTH);
		keyedRotationalPanel.setPreferredSize(new Dimension(500,200));
		keyedRotationalPanel.setMaximumSize(new Dimension(500,200));

		setLayoutKeyedRotational();
	}
	
	public void setLayoutNone(){
		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BorderLayout());
		c.add(messageArea, BorderLayout.NORTH);
		c.add(modeBox, BorderLayout.CENTER);
		pack();
	}
	
	public void setLayoutRotational(){
		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BorderLayout());
		c.add(messageArea, BorderLayout.NORTH);
		c.add(modeBox, BorderLayout.CENTER);
		c.add(rotationalPanel, BorderLayout.SOUTH);
		pack();
		
	}
	
	public void setLayoutKeyedRotational(){
		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BorderLayout());
		c.add(messageArea, BorderLayout.NORTH);
		c.add(modeBox, BorderLayout.CENTER);
		c.add(keyedRotationalPanel, BorderLayout.SOUTH);
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
