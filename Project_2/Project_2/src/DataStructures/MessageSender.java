package DataStructures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import javafx.collections.ObservableList;

public class MessageSender {

	private Conversation conversation = null;
	
	public MessageSender(Conversation conversation) {
		this.conversation = conversation;
	}
	
	public BufferedWriter makeTextFile() throws IOException {
		File textfile = new File("Conversation");
		ObservableList<String> conversationList = conversation.getMessageHistory();
		String conversationString = "";
		for (int x = 0; x < conversationList.size(); x++) {
			conversationString = conversationString + conversationList.get(x) + "\n";
		}
		OutputStreamWriter output = null; //make a try catch for this possible null pointer exception
		try {
			output.write(conversationString);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		output.close();
		
		BufferedWriter writer = new BufferedWriter(output);
		
		return writer;
	}

}
