package preprocess;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class Preprocess {

	static ArrayList<String> StopWords = new ArrayList<String>(Arrays.asList(new String[] { "a", "able", "about",
			"across", "after", "all", "almost", "also", "am", "among", "an", "and", "any", "are", "as", "at", "be",
			"because", "been", "but", "by", "can", "cannot", "could", "dear", "did", "do", "does", "either", "else",
			"ever", "every", "for", "from", "get", "got", "had", "has", "have", "he", "her", "hers", "him", "his",
			"how", "however", "i", "if", "in", "into", "is", "it", "its", "just", "least", "let", "like", "likely",
			"may", "me", "might", "most", "must", "my", "neither", "no", "nor", "not", "of", "off", "often", "on",
			"only", "or", "other", "our", "own", "rather", "said", "say", "says", "she", "should", "since", "so",
			"some", "than", "that", "the", "their", "them", "then", "there", "these", "they", "this", "tis", "to ",
			"too", "twas", "us", "wants", "was", "we", "were", "what", "when", "where", "which", "while", "who", "whom",
			"why", "will", "with", "would", "yet", "you", "your", "ain't", "aren't", "can't", "could've", "couldn't",
			"didn't", "doesn't", "don't", "hasn't", "he'd", "he'll", "he's", "how'd", "how'll", "how's", "i'd", "i'll",
			"i'm", "i've", "isn't", "it's", "might've", "mightn't", "must've", "mustn't", "shan't", "she'd", "she'll",
			"she's", "should've", "shouldn't", "that'll", "that's", "there's", "they'd", "they'll", "they're",
			"they've", "wasn't", "we'd", "we'll", "we're", "weren't", "what'd", "what's", "when'd", "when'll", "when's",
			"where'd", "where'll", "where's", "who'd", "who'll", "who's", "why'd", "why'll", "why's", "won't",
			"would've", "wouldn't", "you'd", "you'll", "you're", "you've" }));

	public static ArrayList<String> stopwords = new ArrayList<String>();
	public static ArrayList<String> no_number = new ArrayList<String>();
	public static ArrayList<String> caseFolding = new ArrayList<String>();

//	public static String[] remove_stopwords_30(String[] all_tokens) {
//		ArrayList<String> without_30 = new ArrayList<String>();
//		// choose 30 random stopwords from the list
//		Random r = new Random();
//		int random_stopword = r.nextInt(StopWords.size());
//		String[] random_30 = new String[30];
//		for (int i = 0; i < 29; i++) {
//			random_30[i] = StopWords.get(random_stopword);
//			for (String t : all_tokens) {
//				for (int x = 0; x < 29; i++) {
//					if (t == random_30[x]) {
//						without_30.add(all_tokens[i]);
//					}
//				}
//			}
//		}
//		String[] tokens_without_30 = without_30.toArray(new String[without_30.size()]);
//		return tokens_without_30;
//	}
//
//	public static String[] remove_stopwords_150(String[] all_tokens) {
//		ArrayList<String> without_150 = new ArrayList<String>();
//		// choose 30 random stopwords from the list
//		Random r = new Random();
//		int random_stopword = r.nextInt(StopWords.size());
//		String[] random_150 = new String[150];
//		for (int i = 0; i < 149; i++) {
//			random_150[i] = StopWords.get(random_stopword);
//			for (String t : all_tokens) {
//				for (int x = 0; x < 149; i++) {
//					if (t == random_150[x]) {
//						without_150.add(all_tokens[i]);
//					}
//				}
//			}
//		}
//		String[] tokens_without_150 = without_150.toArray(new String[without_150.size()]);
//		return tokens_without_150;
//	}

	public static String remove_stopwords(String token) {
		Boolean if_Found = false;
		for (int i = 0; i < Preprocess.StopWords.size(); i++) {
			if (Preprocess.StopWords.get(i).equals(token) == false && i == Preprocess.StopWords.size()) {
				if_Found = false;
			}

			if (Preprocess.StopWords.get(i).equals(token)) {
				stopwords.add(token);
				if_Found = true;
				break;
			}
		}
		return token;
	}

	public static String remove_numbers(String tokens) {
		if (tokens.matches("[0-9]")) {
			tokens = tokens.replaceAll("[0-9]", "");
			Preprocess.no_number.add(tokens);
		}
		return tokens;
	}

	public static String case_folding(String token) {
		if (!(token.equals(token.toLowerCase()))) {
			token = token.toLowerCase();
			caseFolding.add(token);
		}
		return token;
	}
	
	public static String punctuation(String token) {
		
			token = token.replaceAll("\\W", "");
			
		return token;
	}

}