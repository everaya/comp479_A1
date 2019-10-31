package query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

import blockHandling.BlockHandling;

import java.util.Set;

import common.Common;

import java.util.List;
import java.util.Map;

public class Query {

	public static LinkedHashSet<String> searchTerm(String query) throws IOException {
		LinkedHashSet<String> results = new LinkedHashSet<String>();
		Boolean found = false;
		for (int final_file_number = 0; final_file_number <= 1; final_file_number++) {
			Common.setDictionnary(BlockHandling
					.getBlock(System.getProperty("user.dir") + "/DISK/FinalBlock" + final_file_number + ".txt"));
			for (Entry<String, LinkedHashSet<String>> entry : Common.getDictionnary().entrySet()) {
				if (query.equals(entry.getKey())) {
					found = true;
					for (String newID : entry.getValue()) {
						results.add(newID);
						System.out.print(newID + " ");
					}

				}

			}
		}
		return results;
	}

	public static LinkedHashSet<String> searchDictionary(String someQuery) throws IOException {
		LinkedHashSet<String> tempPostingList = new LinkedHashSet<String>();
		String getToken;
		String getValuesLine;

		for (int final_file_number = 0; final_file_number <= 1; final_file_number++) {

			FileReader fileReader = new FileReader(
					System.getProperty("user.dir") + "/DISK/FinalBlock" + final_file_number + ".txt");
			BufferedReader buffered = new BufferedReader(fileReader);

			String theLine = buffered.readLine();

			while (theLine != null) {
				int colonPosition = theLine.indexOf(":");
				getToken = theLine.substring(0, colonPosition);

				if (getToken.equals(someQuery)) {
					getValuesLine = theLine.substring(colonPosition + 1);
					String[] getValues = getValuesLine.split(" ");

					for (String v : getValues) {

						tempPostingList.add(v);
					}

					break;
				} else
					theLine = buffered.readLine();

			}
		}
		return tempPostingList;

	}

	public static Set<String> customAndQueries(String query) throws IOException {
		Set<String> all_values = new HashSet<String>();
		String[] queryTerms = query.split(" AND ");

		LinkedHashSet<String> h1 = new LinkedHashSet<String>();
		LinkedHashSet<String> h2 = new LinkedHashSet<String>();
		LinkedHashSet<String> h3 = new LinkedHashSet<String>();

		if (queryTerms.length <= 2) {
			h1 = searchDictionary(queryTerms[0]);
			h2 = searchDictionary(queryTerms[1]);
			all_values = intersection(h1, h2);
		} else if (queryTerms.length > 2) {
			h1 = searchDictionary(queryTerms[0]);
			h2 = searchDictionary(queryTerms[1]);
			h3 = searchDictionary(queryTerms[2]);

			all_values = intersection(h1, h2);
			all_values = intersection(h2, h3);

		}

		return all_values;

	}

	public static Set<String> customORQueries(String query) throws IOException {
		Set<String> all_values = new HashSet<String>();
		String[] queryTerms = query.split(" OR ");

		LinkedHashSet<String> h1 = new LinkedHashSet<String>();
		LinkedHashSet<String> h2 = new LinkedHashSet<String>();

		h1 = searchDictionary(queryTerms[0]);
		h2 = searchDictionary(queryTerms[1]);

		all_values.addAll(h1);
		all_values.addAll(h1);

		return all_values;

	}

	public List<Integer> union(List<Integer> list1, List<Integer> list2) {
		Set<Integer> set = new HashSet<Integer>();

		set.addAll(list1);
		set.addAll(list2);

		return new ArrayList<Integer>(set);
	}

	public static Set<String> intersection(LinkedHashSet<String> h1, LinkedHashSet<String> h2) {
		Set<String> list = new HashSet<String>();

		for (String t : h1) {
			if (h2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}

	public static void printQueries() throws IOException {
		// AND Queries
		System.out.println("==========Starting with AND queries===========");
		System.out.println("jimmy AND carter: " + customAndQueries("jimmy AND carter"));
		System.out.println("green AND party: " + customAndQueries("green AND party"));
		System.out.println("abusive AND added: " + customAndQueries("abusive AND added"));
		System.out.println("advances AND age: " + customAndQueries("advances AND age"));
		System.out.println("innovation AND in AND telecommunication: "
				+ customAndQueries("innovation AND in AND telecommunication"));
		System.out.println();
		// OR Queries
		System.out.println("==========Starting with OR queries===========");
		System.out.println("environmentalist OR ecologist: " + customORQueries("environmentalist OR ecologist"));
		System.out.println("jimmy OR carter: " + customORQueries("jimmy OR carter"));
		System.out.println("green OR party: " + customORQueries("green OR party"));
		System.out.println();
	}
}
