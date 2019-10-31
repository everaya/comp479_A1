package spimi_merge;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;

import blockHandling.BlockHandling;
import common.Common;
import fileHandling.FileHandling;

public class SPIMI_Merge {

	public static void Merge(int nbrOfBlocks) throws IOException {
		HashMap<String, LinkedHashSet<String>> block = new HashMap<String, LinkedHashSet<String>>();
		Common.setDictionnary(BlockHandling.getBlock(System.getProperty("user.dir") + "/DISK/Block1.txt"));
		int final_file_number = 0;

		for (int i = 1; i < nbrOfBlocks; i++) {
			block = BlockHandling.getBlock(System.getProperty("user.dir") + "/DISK/Block" + i + ".txt");

			for (String str : block.keySet()) {
				for (String IDs : block.get(str)) {
					if (Common.getDictionnary().containsKey(str)) {
						Common.getDictionnary().get(str).add(IDs);
					} else {
						LinkedHashSet<String> newdictionnary = new LinkedHashSet<String>();
						newdictionnary.add(IDs);
						Common.getDictionnary().put(str, newdictionnary);
					}

				}
				if (Common.getDictionnary().size() == 25000) {
					Common.getSorteddictionnary().putAll(Common.getDictionnary());
					FileHandling.sendToFinalFile(Common.getSorteddictionnary(), final_file_number);
					Common.getSorteddictionnary().clear();
					Common.getDictionnary().clear();
					final_file_number += 1;
				}

			}

		}

	}
}
