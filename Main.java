package autofill;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
	
		// number of args is incorrect
		if (args.length != 1) {
			System.err.println("invalid runtime args.  expecting path.");
			System.exit(1);
		}
		System.out.println(System.getProperty("user.dir"));
		
		File directory = new File(args[0]);
		
		List<String> corpus = null;
		try {
			corpus = CorpusReader.readInCorpus(directory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		
		Predictor predictor = new Predictor();
		predictor.add(corpus);
		
		System.out.println("Corpus successfully processed.");
		
		Scanner scanner = new Scanner(System.in);
		String inputString = "";
		List<String> inputWords = new ArrayList<String>();
		
		while(!inputString.equals("."))
		{
			System.out.println("Please enter a word:");
			inputString = scanner.nextLine();
			
			String word = cleanInput(inputString);
			
			if (isValidWord(word)) {
				inputWords.add(word);
				List<Suggestion> suggestions = predictor.generateSuggestions(inputWords);
				if (suggestions.isEmpty()) {
					System.out.println("No suggestions.");
				}
				else {
					for (Suggestion s : suggestions) {
						System.out.println(s.toString());
					}
				}
			}
			else {
				System.out.println("invalid word");
			}
		}
		
		System.out.println("Goodbye.");
		scanner.close();
		System.exit(0);
	}
	
	public static String cleanInput(String input) {
		input = input.trim();
		String[] tokens = input.split("\\s+");
		input = tokens[0].trim().toLowerCase();
		return input;
	}
	
	public static boolean isValidWord(String word) {
		if (word.equals(""))
			return false;
		if (word.charAt(0) < 'a' || word.charAt(0) > 'z')
			return false;
		return true;
	}
	
}
