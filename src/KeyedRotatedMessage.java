/*-------------------------------------------------------------------
 *
 * KeyedRotatedMessage Class: for CSCI 235 MP2
 * Mike Jipping, January 2011
 *
 * Some wrinkles: key is always set first, then rotation
 * Also, decryption must be rewritten to *translate* the message, not simply decrypt like MP1.
 *
 *-------------------------------------------------------------------
 */
 
public class KeyedRotatedMessage extends RotatedMessage {
	
	// Once we find the message key, hang onto it!
	String messageKey;

	// *** Constructor
	// Call the constructor for RotatedMessage (necessary because we have a parameter) and 
	// initialize the key.
	public KeyedRotatedMessage(String rotatedMessage)
	{
		super(rotatedMessage);
		
		messageKey = "";
	}
	
	// *** setKey
	// Sets the key in the substitutionPattern array.  Note that the key is set first, then 
	// the whole thing is rotated if need be.  The key is lowercased, then inserted one character
	// at a time, deleting the character that exists in the normal key,.
	public void setKey(String key)
	{
		int pos, border;
		String subPattern;
		
		// Step 1: lowercase
		key = key.toLowerCase();
		
		// Step 2: insert the key.  Start with an initialized substitutionPattern.
		for (int i=0; i<26; i++) substitutionPattern[i] = (char)(i+97);

		border = 0;  // keep track of how many key letters we have inserted.
		for (int i=0; i<key.length(); i++) {
			// Check for a duplicate character in the inserted key (0 -> border)
			subPattern = new String(substitutionPattern);
			if (border > 0) 
				if (subPattern.substring(0,border).indexOf(key.charAt(i))>=0)
					continue;
			
			// Now look for the character being inserted starting at the end of the inserted key
			pos = subPattern.indexOf(key.charAt(i), border);
			// Shift all the characters to eliminiate the duplicate character
			for (int j = pos; j > border; j--) {
				substitutionPattern[j] = substitutionPattern[j-1];
			}
			// And stick the next character in
			substitutionPattern[border] = key.charAt(i);
			border++;  // the border moves
		}
		
		// Record the key and rotate!  (note that rotationFactor is inherited from RotatedMessage)
		messageKey = key;
		super.setRotation(rotationFactor);
	}
	
	// *** setRotation
	// Rotate the key by setting the key (which rotates the key too)
	public void setRotation(int rotation)
	{
		rotationFactor = rotation;
		setKey(messageKey);
		// setKey does the rotation for us
	}
	
	// *** translate
	// Instead of computing where the substitution can be found, we need to find it and translate.
	private void translate()
	{
		char c, nc;
		int pos;
		String newstr;
		
		// Initialize
		newstr = "";
		
		// For each character in the message, find the character in the substitutionPattern and 
		// derive the character it corresponds to by computing the position+97.  Uppercase if 
		// necessary.
		for (int i=0; i<message.length(); i++)
		{
			c = nc = message.charAt(i);
			if (Character.isLetter(nc)) {
				if (Character.isUpperCase(c)) {
					nc = Character.toLowerCase(c);
				} 
				nc = (char)(new String(substitutionPattern).indexOf(nc)+97);
				if (Character.isUpperCase(c)) nc = Character.toUpperCase(nc);
			}
			newstr += nc;
		}
		
		// Set up the new message
		message = newstr;
		
	}
	
	// *** decrypt
	// Like the old decrypt (see RotatedMessage) except that we *translate* the messages instead
	// of just computing where each character was in the substitutionPattern
	public boolean decrypt()
	{
		KeyedRotatedMessage krm = null;
		int r;
		
		for (r = 0; r <= 25; r++) {
			krm = new KeyedRotatedMessage(message.toString());
			krm.setKey(messageKey);
			krm.setRotation(r);
			krm.translate();
			if (krm.verify()) break;
		}
		
		if (r == 27) {
			return false;
		} else {
			rotationFactor = krm.getRotation();
			message = krm.toString();
			return true;
		}
	}
	

	
}
