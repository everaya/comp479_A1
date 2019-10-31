package common;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeMap;

public abstract class Common {
	private static HashMap<String, LinkedHashSet<String>> dictionnary = new HashMap<String, LinkedHashSet<String>>(); //terms+posting lists
	private static TreeMap<String, LinkedHashSet<String>> sorteddictionnary = new TreeMap<String, LinkedHashSet<String>>();
	private static LinkedHashSet<String> postingList; //list with all doc id's 

	
	public static TreeMap<String, LinkedHashSet<String>> getSorteddictionnary() {
		return sorteddictionnary;
	}
	public static void setSorteddictionnary(TreeMap<String, LinkedHashSet<String>> sorteddictionnary) {
		Common.sorteddictionnary = sorteddictionnary;
	}
	public static LinkedHashSet<String> getPostingList() {
		return postingList;
	}
	public static void setPostingList(LinkedHashSet<String> postingList) {
		Common.postingList = postingList;
	}
	
	public static HashMap<String, LinkedHashSet<String>> getDictionnary() {
		return dictionnary;
	}
	public static void setDictionnary(HashMap<String, LinkedHashSet<String>> dictionnary) {
		Common.dictionnary = dictionnary;
	}
}
