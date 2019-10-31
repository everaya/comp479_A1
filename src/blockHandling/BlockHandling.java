package blockHandling;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;

import common.Common;

public class BlockHandling {

	public static void addToBlock(String theToken, String docID) {
		if (Common.getDictionnary().containsKey(theToken) == false) // If the term does not previously exist
		{
			Common.setPostingList(new LinkedHashSet<String>());
			Common.getPostingList().add(docID);
			Common.getDictionnary().put(theToken, Common.getPostingList());
		} else {
			Common.getDictionnary().get(theToken).add(docID);

		}

	}

	public static HashMap<String, LinkedHashSet<String>> getBlock(String blockName) throws IOException {
		HashMap<String, LinkedHashSet<String>> block = new HashMap<String, LinkedHashSet<String>>();
		BufferedReader buffered = new BufferedReader(new FileReader(blockName));
		LinkedHashSet<String> docIDs = new LinkedHashSet<String>();

		String line = buffered.readLine();

		while (line != null) {
			String token;
			String docID;

			int seperator = line.indexOf(":");
			token = line.substring(0, seperator);
			docID = line.substring(seperator + 1);
			String[] strings_docIDs = docID.split(" ");

			for (String v : strings_docIDs) {
				docIDs.add(v);
			}

			block.put(token, docIDs);
			line = buffered.readLine();
			docIDs = new LinkedHashSet<String>();
		}

		buffered.close();

		return block;
	}
}
