package autofill;

public class Suggestion implements Comparable<Suggestion> {

	private String input;
	private String suggestion;
	private int count;
	
	public Suggestion(String input, String suggestion, int count) {
		this.input = input;
		this.suggestion = suggestion;
		this.count = count;
	}
	
	public String input() {
		return input;
	}
	
	public String suggestion() {
		return suggestion;
	}
	
	public int count() {
		return count;
	}
	

	@Override
	public int compareTo(Suggestion that) {
		if (count != that.count()) {
			return count - that.count();
		}
		else {
			return suggestion.compareTo(that.suggestion());
		}
	}
	
	public String toString() {
		return input + " -> " + suggestion + " -> " + count;
	}
	
	
}
