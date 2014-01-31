import java.util.List;

public class Message {
	
	String message;
	
	public Message(String messageString)
	{
		message = messageString;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public boolean verify()
	{
		Dictionary dict = new Dictionary("C://Users/John/workspace/MP3/bin/words/words.txt");
		List<String> wordList;
		
		String[] words = message.split(" ");
		for (String word : words) {
			word = word.toLowerCase();
			word = word.replaceAll("\\.","");			
			word = word.replaceAll("\\?","");			
			word = word.replaceAll("!","");			
			word = word.replaceAll(",","");			
			word = word.replaceAll(";","");
			wordList = dict.lookup(word);
			if (wordList.size() == 0) return false;
		}
		return true;

	}

	public String toString()
	{
		return message;
	}
}
