package autofill;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CorpusReader {

	public static List<String> readInCorpus(File directory) throws IOException
	{
		// directory does not exist
		if (!directory.exists() || !directory.isDirectory()) {
			System.err.println("invalid path");
			System.exit(1);
		}
		
		// directory cannot be opened for reading because of permissions
		if (!directory.canRead()) {
			System.err.println("invalid permissions");
			System.exit(1);
		}
		
		// directory is empty
		File[] files = directory.listFiles();
		if (files.length == 0) {
			System.err.println("directory is empty");
			System.exit(1);
		}
		
		List<String> corpus = new ArrayList<String>();
		
		// read in all files and store words
		for (File file : files) {
			corpus.addAll(readSingleFile(file));  // need to write this method
		}
		return corpus;
	}
	
	private static List<String> readSingleFile(File file) throws IOException {
		List<String> words = new ArrayList<String>();
		FileReader freader = new FileReader(file);
		BufferedReader buff = new BufferedReader(freader);
		
		String line = buff.readLine(); //first line
		line = buff.readLine();
		while (line != null) {
			String word = processSingleLine(line);
			if (word != null)
			{
				words.add(word);
			}
			line = buff.readLine();
		}
		buff.close();
		return words;
	}
	
	private static String processSingleLine(String line) {
		String[] words = line.split("\\t");
		if (words.length != 3)
			return null;
		if (words[1].length() == 0)
			return null;
		if (words[1].charAt(0) < 'a' || words[1].charAt(0) > 'z')
			return null;
		return words[1];
	}
}
