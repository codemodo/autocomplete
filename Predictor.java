package autofill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Predictor {

	private Map<String, Map<String, Integer>> unigrams;
	private Map<String, Map<String, Integer>> bigrams;
	
	public Predictor() {
		unigrams = new HashMap<String, Map<String, Integer>>();
		bigrams = new HashMap<String, Map<String, Integer>>();
	}
	
	public void add(List<String> corpus) {
		//TODO need to adjust this so that it captures the last word
		for (int i = 0; i < corpus.size() - 1; i++) {
			String curr = corpus.get(i);
			String next = corpus.get(i+1);
			addUnigram(curr, next);
		}
		
		for (int i = 1; i < corpus.size() - 1; i++) {
			String prev = corpus.get(i - 1);
			String curr = corpus.get(i);
			String next = corpus.get(i + 1);
			addBigram(prev, curr, next);
		}
	}
	
	public List<Suggestion> generateSuggestions(List<String> inputs) {
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		
		if (inputs.size() != 1) {
			suggestions.addAll(generateBigramSuggestions(inputs));
		}
		
		if (suggestions.size() >= 3)
			return suggestions.subList(0, 2);
		
		suggestions.addAll(generateUnigramSuggestions(inputs.get(inputs.size() - 1), 3 - suggestions.size()));
		
		if (suggestions.size() >= 3)
			return suggestions.subList(0, 2);
		
		return suggestions;
	}
	
	private void addUnigram(String first, String second) {
		if (!unigrams.containsKey(first))
			unigrams.put(first, new HashMap<String, Integer>());
		
		if (!unigrams.get(first).containsKey(second))
			unigrams.get(first).put(second, 0);
		
		int count = unigrams.get(first).get(second);
		unigrams.get(first).put(second, count + 1);
	}
	
	private void addBigram(String first, String second, String third) {
		String bgram = first + ":" + second;
		
		if (!bigrams.containsKey(bgram))
			bigrams.put(bgram, new HashMap<String, Integer>());
		
		if (!bigrams.get(bgram).containsKey(third))
			bigrams.get(bgram).put(third, 0);
		
		int count = bigrams.get(bgram).get(third);
		bigrams.get(bgram).put(third, count + 1);
	}
	
	private List<Suggestion> generateUnigramSuggestions(String word, int n) {
		if (!unigrams.containsKey(word))
			return null;
		
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		for (Map.Entry<String, Integer> entry : unigrams.get(word).entrySet()) {
			suggestions.add(new Suggestion(word, entry.getKey(), entry.getValue()));
		}
		
		Collections.sort(suggestions);
		
		return suggestions.subList(0, n-1);
	}
	
	private List<Suggestion> generateBigramSuggestions(List<String> words) {
		String bgram = words.get(words.size() - 2) + ":" + words.get(words.size() - 1);
		
		if (!bigrams.containsKey(bgram))
			return null;
		
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		for (Map.Entry<String, Integer> entry : bigrams.get(bgram).entrySet()) {
			suggestions.add(new Suggestion(bgram, entry.getKey(), entry.getValue()));
		}
		
		Collections.sort(suggestions);
		
		return suggestions;
		
	}
}
