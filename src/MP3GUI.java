import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	JPanel keyPanel;
	JLabel keyLabel;
	JTextArea keyArea;
	JPanel rotationPanel;
	JLabel rotationLabel;
	
	
	Message message;
	
	

	public MP3GUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
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
		
		//Rotational Only
		rotationsBox = new JComboBox<String>(rotations);
		rotationLabel = new JLabel("Rotation: ");
		JPanel subrotationPanel = new JPanel();
		subrotationPanel.setLayout(new BoxLayout(subrotationPanel, BoxLayout.X_AXIS));
		subrotationPanel.add(rotationLabel);
		subrotationPanel.add(rotationsBox);
		rotationPanel = new JPanel();
		GridBagLayout g;
		GridBagConstraints gc = new GridBagConstraints();
		rotationPanel.setLayout(g = new GridBagLayout());
		JLabel subLabel = new JLabel(" ");
		JLabel subLabel2 = new JLabel(" ");
		g.setConstraints(subLabel, gc);
		rotationPanel.add(subLabel);
		gc.gridwidth = 2;
		g.setConstraints(subrotationPanel, gc);
		rotationPanel.add(subrotationPanel);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		g.setConstraints(subLabel2, gc);
		rotationPanel.add(subLabel2);
		
		
		//Key Only
		keyLabel = new JLabel("Key: ");
		keyArea = new JTextArea(1,20);
		keyPanel = new JPanel();
		gc = new GridBagConstraints();
		keyPanel.setLayout(g = new GridBagLayout());
		JPanel subkeyPanel = new JPanel();
		subLabel = new JLabel(" ");
		subLabel2 = new JLabel(" ");
		subkeyPanel.setLayout(new BoxLayout(subkeyPanel, BoxLayout.X_AXIS));
		subkeyPanel.add(keyLabel);
		subkeyPanel.add(keyArea);
		g.setConstraints(subLabel, gc);
		keyPanel.add(subLabel);
		gc.gridwidth = 2;
		g.setConstraints(subkeyPanel, gc);
		keyPanel.add(subkeyPanel);
		gc.gridwidth = GridBagConstraints.REMAINDER;
		g.setConstraints(subLabel2, gc);
		keyPanel.add(subLabel2);
		


		Container c = getContentPane();
		c.removeAll();
		c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
		c.add(messageArea);
		c.add(modeBox);
		c.add(rotationPanel);
		c.add(keyPanel);
		c.add(decryptArea);
		c.add(encryptDecryptPanel);
		
		setLayoutNone();

	}
	
	public void setLayoutNone(){
		rotationPanel.setVisible(false);
		keyPanel.setVisible(false);
		decryptArea.setVisible(false);
		encryptDecryptPanel.setVisible(false);
		setMinimumSize(new Dimension(0,0));
		pack();
		setMinimumSize(getSize());
		
		message = new Message(messageArea.getText());
	}
	
	public void setLayoutRotational(){
		rotationPanel.setVisible(true);
		keyPanel.setVisible(false);
		decryptArea.setVisible(true);
		encryptDecryptPanel.setVisible(true);
		setMinimumSize(new Dimension(0,0));
		pack();
		setMinimumSize(getSize());
		
		message = new RotatedMessage(messageArea.getText());
	}
	
	public void setLayoutKeyedRotational(){
		rotationPanel.setVisible(true);
		keyPanel.setVisible(true);
		decryptArea.setVisible(true);
		encryptDecryptPanel.setVisible(true);
		pack();
		setMinimumSize(getSize());
		
		message = new KeyedRotatedMessage(messageArea.getText());
	}
	
	
	public class EncryptHander implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(message instanceof KeyedRotatedMessage){
				KeyedRotatedMessage krmessage = new KeyedRotatedMessage(messageArea.getText());
				System.out.println("\'" + keyArea.getText() + "\'");
				krmessage.setKey(keyArea.getText());
				krmessage.setRotation(rotationsBox.getSelectedIndex());
				krmessage.encrypt();
				decryptArea.setText(krmessage.getMessage());
			}
			else if(message instanceof RotatedMessage){
				RotatedMessage rmessage = new RotatedMessage(messageArea.getText());
				rmessage.setRotation(rotationsBox.getSelectedIndex());
				rmessage.encrypt();
				decryptArea.setText(rmessage.getMessage());
			}
		}
	}
	
	public class DecryptHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(message instanceof KeyedRotatedMessage){
				KeyedRotatedMessage krmessage = new KeyedRotatedMessage(decryptArea.getText());
				krmessage.setKey(keyArea.getText());
				krmessage.decrypt();
				messageArea.setText(krmessage.getMessage());
				rotationsBox.setSelectedIndex(krmessage.getRotation());
			}
			else if(message instanceof RotatedMessage){
				RotatedMessage rmessage = new RotatedMessage(decryptArea.getText());
				rmessage.decrypt();
				messageArea.setText(rmessage.getMessage());
				rotationsBox.setSelectedIndex(rmessage.getRotation());
			}
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
