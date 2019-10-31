package main;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import blockHandling.BlockHandling;
import common.Common;
import compressionTable.CompressionTable;
import fileHandling.FileHandling;
import preprocess.Preprocess;
import query.Query;
import spimi_merge.SPIMI_Merge;

public class Main {
	public static int unfilteredSize = 0;

	public static void main(String[] args) throws IOException {
		String NEWID = "";
		String filePath = System.getProperty("user.dir") + "/reuters21578/";
		int DocCounter = 0;
		int blockCounter = 1;
		String fileName = "";

		// delete any previous block files
		FileHandling.DeleteFiles();

//Constructing indexes..

		// getting the file number based on if it's less or more than 10
		for (int file_number = 0; file_number < 21; file_number++) {
			if (file_number < 10) {
				fileName = "reut2-00" + file_number + ".sgm";
				System.out.println("Constructing index from reuter file " + file_number + "...");
			} else {
				fileName = "reut2-0" + file_number + ".sgm";
				System.out.println("Constructing index from reuter file " + file_number + "...");
			}

			String realPath = filePath + fileName;
			BufferedReader buffered = new BufferedReader(new FileReader(realPath));
			String readLine = buffered.readLine();
			System.out.println("Preprocess for file " + file_number);

//Start reading files			
			while (readLine != null) {

				// This statement scrolls through the document to get its' ID
				if (readLine.contains("NEWID=")) {
					DocCounter++;
					NEWID = readLine.substring(readLine.indexOf("NEWID="));
					NEWID = NEWID.substring(NEWID.indexOf("\"") + 1, NEWID.indexOf("\">"));
				}

				String[] tokens = readLine
						.split("[\\*\\s\\t\\n\\r<>\\=\\+\\$\\''\\\\\\-\\^\\�\\.\\,\\;\\:\\!\\?\"\\(\\)\\&lt;\\{\\}]+");

				// Preprocessing for all tokens (e.g: numbers, punctuation, case folding)
				for (String token : tokens) {
					unfilteredSize += 1;

					Preprocess.punctuation(token);

					// if it matched regex of digits, a number was removed
					Preprocess.remove_numbers(token);

					// case folding, if it doesnt match the lower case, then a case folding was done
					Preprocess.case_folding(token);

					// remove stopwords
					Preprocess.remove_stopwords(token);

					BlockHandling.addToBlock(token, NEWID);

				}
				// check number of docs, if 500 then write to block
				if (DocCounter == 500) {
					FileHandling.sendToBlock(blockCounter);
					blockCounter++;
					DocCounter = 1;
				}
				// if les than 500 docs, continue reading
				else
					readLine = buffered.readLine();
			}

			buffered.close();
			Common.getDictionnary().clear();

		}

		System.out.println();

		// merge into final index
		System.out.println("Merging blocks to final file....");
		SPIMI_Merge.Merge(blockCounter);
		System.out.println();

		// Compression Table
		CompressionTable.printTable();
		System.out.println();

		//AND, OR Queries
		Query.printQueries();

		
		// search 1 term query
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Would you like to test a query? (Y/N)");
		String answer = keyboard.nextLine();

		Boolean anwr = true;
		while (anwr) {
			if (answer.equalsIgnoreCase("Y")) {
				String query;
				System.out.println("Please enter your 1 keyword to search: ");
				query = keyboard.nextLine();
				System.out.print(query + ": ");

				Query.searchTerm(query);
				keyboard.nextLine();

			} else {
				anwr = false;
				System.out.print("Thank you, process will terminate");
				System.exit(0);
			}
		}
		keyboard.close();
	}

}
