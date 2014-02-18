
public class RotatedMessage extends Message {
	
	int rotationFactor;
	char[] substitutionPattern;
	
	public RotatedMessage(String rotatedMessage) {
		super(rotatedMessage);
		
		rotationFactor = 0;
		substitutionPattern = new char[26];
		for (int i=0; i<26; i++) substitutionPattern[i] = (char)(i+97);
	}
	
	public void encrypt(){
		char c;
		int pos;
		String newstr;
		
		newstr = "";
		for (int i=0; i<message.length(); i++){
			c = message.charAt(i);
			if (Character.isUpperCase(c)) {
				newstr += (char)((substitutionPattern[c+32-97])-32);
			} else if (Character.isLowerCase(c)) {
				newstr += substitutionPattern[c-97];					
			} else {
				newstr = newstr + c;
			}
		}
		message = newstr;
	}
	
	public boolean decrypt(){
		RotatedMessage rm;
		int r;
		
		rm = new RotatedMessage(message.toString());
		for (r = 1; r <= 26; r++) {
			rm = new RotatedMessage(message.toString());
			rm.setRotation(r);
			rm.encrypt();
			if (rm.verify()) break;
		}
		
		if (r == 27) {
			return false;
		} else {
			rotationFactor = 26-rm.getRotation();
			message = rm.toString();
			return true;
		}
	}
	
	public int getRotation(){
		return rotationFactor;
	}
	
	public void setRotation(int rotation){
		char[] newPattern = new char[26];
		
		if (rotation >= 26) {
			System.err.println("ERROR: rotation factor >= 26!");
			return;
		}
		if (rotation < 0) {
			System.err.println("ERROR: rotation factor < 0!");
			return;
		}
		
		rotationFactor = rotation;
		for (int i=0; i<26; i++) newPattern[(i+26-rotation)%26] = substitutionPattern[i]; 
		for (int i=0; i<26; i++) substitutionPattern[i] = newPattern[i]; 
	}
	
	public String getSubstitutionPattern(){
		return new String(substitutionPattern);
	}
	
	public String toString(){
		return message;
	}
}
