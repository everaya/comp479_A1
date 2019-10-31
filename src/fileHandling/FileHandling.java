package fileHandling;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.TreeMap;
import java.util.Map.Entry;

import common.Common;

public class FileHandling {
	
	public static void sendToBlock(int blockNumber) throws IOException {
		BufferedWriter testWrite = new BufferedWriter(
				new FileWriter(System.getProperty("user.dir") + "/DISK/Block" + blockNumber + ".txt"));
		
		for (Entry<String, LinkedHashSet<String>> entry : Common.getDictionnary().entrySet()) {
			testWrite.write(entry.getKey().toLowerCase().replaceAll("[^A-Za-z]", "") + ":");
			if(Common.getDictionnary().entrySet().isEmpty()) {
				Common.getDictionnary().remove(entry.getKey()); //remove any empty string that might have happened from the previous regex replacement
			}
			else{

				for (String newID : entry.getValue()) {
					
						testWrite.write(newID + " ");
					
				}
			}

			testWrite.write("\n");
		}

		testWrite.close();

		Common.getDictionnary().clear();
	}
	
	public static TreeMap<String, LinkedHashSet<String>> sendToFinalFile(TreeMap<String, LinkedHashSet<String>> sortedList, int final_file_number)
			throws IOException {
		BufferedWriter testWrite = new BufferedWriter(
				new FileWriter(System.getProperty("user.dir") + "/DISK/FinalBlock" + final_file_number + ".txt"));
		for (Entry<String, LinkedHashSet<String>> entry : sortedList.entrySet()) {

			testWrite.write(entry.getKey() + ":");
			for (String newID : entry.getValue()) {
				testWrite.write(newID + " ");
			}
			testWrite.write("\n");

		}
		testWrite.close();
		Common.getDictionnary().clear();

		System.out.println("Sorted list and sent to FinalBlock" + final_file_number);
		
		return sortedList;
	}
		
	public static void DeleteFiles() {
		File root_blocks = new File(System.getProperty("user.dir") + "/DISK");
    	File[] all_files = root_blocks.listFiles();
    	for(File file : all_files) {
    		file.delete();
    	}
	}
}
