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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class MP3GUI extends JFrame{
	
	//Unencrypted Message
	JTextArea messageArea;
	JScrollPane messageScrollPane;
	
	//Mode
	String[] options = new String[]{"None", "Rotational", "Keyed Rotational"};
	JComboBox<String> modeBox;
	
	//Encrypted Message
	JScrollPane decryptScrollPane;
	JTextArea decryptArea;
	
	//Rotation
	String[] rotations = "0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25".split(" ");
	JComboBox<String> rotationsBox;
	JPanel rotationPanel;
	JLabel rotationLabel;
	
	//Key
	JPanel keyPanel;
	JLabel keyLabel;
	JTextField keyField;
	
	//Encrypt and Decrypt
	JButton encryptButton;
	JButton decryptButton;
	JPanel encryptDecryptPanel;
	
	
	Message message;
	
	

	/**
	 *  Makes an Mp3 gui
	 */
	public MP3GUI(){
		//Frame stuff
		//Any reason you mess around with the window listener?
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//This was false earlier in the devcycle
		setResizable(true);
		setTitle("MP3");
		
		//Unencrypted Message
		messageArea = new JTextArea(5,40);
		messageArea.setLineWrap(true);
		//Allows scrolling
		messageScrollPane = new JScrollPane(messageArea);
		
		//Mode
		modeBox = new JComboBox<String>(options);
		modeBox.addActionListener(new ModeHandler());
		
		//The rest may or may show
		
		//Rotation
		rotationsBox = new JComboBox<String>(rotations);
		rotationLabel = new JLabel("Rotation: ");
		JPanel subrotationPanel = new JPanel();
		//Sub Rotational Panel occupies the middle 1/2 of the rotation panel
		subrotationPanel.setLayout(new BoxLayout(subrotationPanel, BoxLayout.X_AXIS));
		subrotationPanel.add(rotationLabel);
		subrotationPanel.add(rotationsBox);
		rotationPanel = middleHalf(subrotationPanel);
		
		//Key
		keyLabel = new JLabel("Key: ");
		keyField = new JTextField(20);
		//SubkeyPanel will occupy the middle half of keyPanel
		JPanel subkeyPanel = new JPanel();
		subkeyPanel.setLayout(new BoxLayout(subkeyPanel, BoxLayout.X_AXIS));
		subkeyPanel.add(keyLabel);
		subkeyPanel.add(keyField);
		keyPanel = middleHalf(subkeyPanel);
				
		//Area for decrypted Message
		decryptArea = new JTextArea(5,40);
		decryptArea.setLineWrap(true);
		decryptScrollPane = new JScrollPane(decryptArea);
		
		//Encrypt Decrypt Buttons
		encryptButton = new JButton("Encrypt");
		encryptButton.addActionListener(new EncryptHander());
		decryptButton = new JButton("Decrypt");
		decryptButton.addActionListener(new DecryptHandler());
		encryptDecryptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		encryptDecryptPanel.add(encryptButton);
		encryptDecryptPanel.add(decryptButton);

		Container c = getContentPane();
		c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
		c.add(messageScrollPane);
		c.add(modeBox);
		c.add(rotationPanel);
		c.add(keyPanel);
		c.add(decryptScrollPane);
		c.add(encryptDecryptPanel);
		
		//Start Out as a regular message
		setLayoutNone();

	}
	
	/**
	 * Only shows the messageArea and the modeBox
	 */
	public void setLayoutNone(){
		rotationPanel.setVisible(false);
		keyPanel.setVisible(false);
		decryptScrollPane.setVisible(false);
		encryptDecryptPanel.setVisible(false);
		setMinimumSize(new Dimension(0,0));
		pack();
		setMinimumSize(getSize());
		
		message = new Message(messageArea.getText());
	}
	
	
	/**
	 * Shows everything but the key Panel
	 */
	public void setLayoutRotational(){
		rotationPanel.setVisible(true);
		keyPanel.setVisible(false);
		decryptScrollPane.setVisible(true);
		encryptDecryptPanel.setVisible(true);
		setMinimumSize(new Dimension(0,0));
		pack();
		setMinimumSize(getSize());
		
		message = new RotatedMessage(messageArea.getText());
	}
	
	/**
	 * Shows everything
	 */
	public void setLayoutKeyedRotational(){
		rotationPanel.setVisible(true);
		keyPanel.setVisible(true);
		decryptScrollPane.setVisible(true);
		encryptDecryptPanel.setVisible(true);
		pack();
		setMinimumSize(getSize());
		
		message = new KeyedRotatedMessage(messageArea.getText());
	}
	
	/**
	 * @param middle The panel to put in the middle of the screen
	 * @return The panel with 'middle' in the middle of it
	 */
	public JPanel middleHalf(JPanel middle){
		// Middle should take up the middle half of the panel
		// http://docs.oracle.com/javase/7/docs/api/java/awt/GridBagLayout.html
		// http://docs.oracle.com/javase/7/docs/api/java/awt/doc-files/GridBagLayout-1.gif
		//This code works, not how I thought i was going to but I like what it does
		//It might not because not all of the components are homogenious like the buttons
		//Im gonna return (ret) this
		JPanel ret = new JPanel();
		GridBagLayout g;
		GridBagConstraints gc = new GridBagConstraints();
		ret.setLayout(g = new GridBagLayout());
		//These two will pad the sides of the middle
		JLabel subLabel = new JLabel(" ");
		JLabel subLabel2 = new JLabel(" ");
		//Adds the left one
		g.setConstraints(subLabel, gc);
		ret.add(subLabel);
		//So itll take up half
		gc.gridwidth = 2;
		g.setConstraints(middle, gc);
		ret.add(middle);
		//So it'll finish the line on the right
		gc.gridwidth = GridBagConstraints.REMAINDER;
		g.setConstraints(subLabel2, gc);
		ret.add(subLabel2);
		
		//return
		return ret;
	}
	
	
	public class EncryptHander implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(message instanceof KeyedRotatedMessage){
				//Nothing fancy here
				KeyedRotatedMessage krmessage = new KeyedRotatedMessage(messageArea.getText());
				krmessage.setKey(keyField.getText());
				krmessage.setRotation(rotationsBox.getSelectedIndex());
				krmessage.encrypt();
				decryptArea.setText(krmessage.getMessage());
			}
			else if(message instanceof RotatedMessage){
				//Nor here
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
				//Like encrypt but backwards
				KeyedRotatedMessage krmessage = new KeyedRotatedMessage(decryptArea.getText());
				krmessage.setKey(keyField.getText());
				//Make sure it actually worked
				if(krmessage.decrypt()){
					messageArea.setText(krmessage.getMessage());
					rotationsBox.setSelectedIndex(krmessage.getRotation());
				}
				else{
					//If it didn't dont mess with anything putup an error dialog. Not gonna throw an exception because the user 
					//shouldn't bump into that kinda stuff
					JOptionPane.showMessageDialog(MP3GUI.this, "The message cannot be decrypted with this key", "Cannot decrypt", 0);
					messageArea.setText("");
				}
			}
			else if(message instanceof RotatedMessage){
				RotatedMessage rmessage = new RotatedMessage(decryptArea.getText());
				//Same as before
				if(rmessage.decrypt()){
					messageArea.setText(rmessage.getMessage());
					rotationsBox.setSelectedIndex(rmessage.getRotation());
				}
				else{
					//Changed the wording to mention nothing of a key
					JOptionPane.showMessageDialog(MP3GUI.this, "The message cannot be decrypted", "Cannot decrypt", 0);
					messageArea.setText("");
				}
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
// ^- favorite way to end a class I think I've gotten more than that before 