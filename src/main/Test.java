package main;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import blockHandling.BlockHandling;
import common.Common;
import compressionTable.CompressionTable;
import fileHandling.FileHandling;
import preprocess.Preprocess;
import query.Query;
import spimi_merge.SPIMI_Merge;

public class Test {
	static int unfilteredSize = 0;

	public static void main(String[] args) throws IOException {
		String NEWID = "";
		String fileContents = "";
		String filePath = System.getProperty("user.dir") + "/reuters21578/";
		int DocCounter = 0;
		int blockCounter = 1;
		String fileName = "";

		// delete any previous block files
		FileHandling.DeleteFiles();

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

//			BufferedReader buffered = new BufferedReader(new FileReader(realPath));
//
//			String readLine = buffered.readLine();
//			
        	System.out.println("Preprocess for file "+file_number);
			Document doc = Jsoup.parse(new File(realPath), "UTF-8");
			String[] body = doc.select("BODY").first().children().text()
	        		.split("[\\*\\s\\t\\n\\r<>\\=\\+\\$\\''\\\\\\-\\^\\�\\.\\,\\;\\:\\!\\?\"\\(\\)\\&lt;\\{\\}]+");
	        
			List<String> news = new ArrayList<String>();

			news.addAll(Arrays.asList(fileContents.split("(?=(?:<REUTERS)(?:.)+(?:NEWID=\".+\">))+")));

			news.remove(0);
		
			

//
//				// This is statement will get the docID
//				if (readLine.contains("NEWID=")) {
//					DocCounter++;
//					NEWID = readLine.substring(readLine.indexOf("NEWID="));
//					NEWID = NEWID.substring(NEWID.indexOf("\"") + 1, NEWID.indexOf("\">"));
//				}

				// Clean tokens from unnecessary regex, leaving numbers to do them later during
				// compression
				
		       
//				String[] tokens = readLine
//						.split("[\\*\\s\\t\\n\\r<>\\=\\+\\$\\''\\\\\\-\\^\\�\\.\\,\\;\\:\\!\\?\"\\(\\)\\&lt;\\{\\}]+");

				// Preprocessing for all tokens (e.g: numbers, punctuation, case folding)

		        for (String token : news) {
		        	String docID = parseDocumentID(token.split("\n")[0]);
		        	token = Jsoup.clean(token, Whitelist.none());
					token = token.replaceAll("[\\*\\t\\n\\r<>\\=\\+\\$\\''\\\\\\-\\^\\�\\.\\,\\;\\:\\!\\?\"\\(\\)\\&lt;\\{\\}]+", "");

		        	DocCounter++;
		        	System.out.println(docID);
		        	
					unfilteredSize += 1;
					
					Preprocess.punctuation(token);
					
					// if it matched regex of digits, a number was removed
					Preprocess.remove_numbers(token);

					// case folding, if it doesnt match the lower case, then a case folding was done
					Preprocess.case_folding(token);

					// remove stopwords
					Preprocess.remove_stopwords(token);
					
					String[] terms = token.split("\\s");
					
					for (String term : terms) {
						System.out.println(term);
						BlockHandling.addToBlock(term, docID);
					}


				}

				if (DocCounter == 500) {
					FileHandling.sendToBlock(blockCounter);
					blockCounter++;
					DocCounter = 1;
				}

				

		}	

		
		Common.getDictionnary().clear();

		System.out.println();
		System.out.println("Merging blocks to final file....");
		SPIMI_Merge.Merge(blockCounter);

		// Compression Table
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.printf("%30s %30s %30s", "# distinct terms", "∆ % from unfiltered", "∆ % from previous");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.printf("%10s %12s", "Unfiltered ", unfilteredSize + "\n");
		System.out.printf("%10s %12s %37s %35s", "Numbers ", CompressionTable.noNumber,
				"-" + CompressionTable.percent_numbers_from_unfiltered + "%",
				"-" + CompressionTable.percent_previous_numbers + "%" + "\n");
		System.out.printf("%10s %12s %37s %35s", "StopWords ", CompressionTable.stop_words,
				"-" + CompressionTable.percent_stopwords_from_unfiltered + "%",
				"-" + CompressionTable.percent_previous_stopwords + "%" + "\n");
		System.out.printf("%10s %9s %37s %35s", "Case folding ", CompressionTable.case_fold,
				"-" + CompressionTable.percent_casefolding_from_unfiltered + "%",
				"-" + CompressionTable.percent_previous_case_folding + "%" + "\n");

		Scanner keyboard = new Scanner(System.in);
		System.out.print("Would you like to test a query? (Y/N)");
		String answer = keyboard.nextLine();
		if (answer.equalsIgnoreCase("Y")) {
			String theQuery;
			System.out.println("Please enter your words to search: ");
			theQuery = keyboard.nextLine();
			System.out.print(theQuery + ": ");

			Query.searchTerm(theQuery);
		} else {
			System.out.print("Thank you, process will terminate");
			System.exit(0);
		}
		keyboard.close();
	}
	

	private static String parseDocumentID(String reutersHeader) {
		Document doc = Jsoup.parse(reutersHeader);
		Element reuters = doc.select("REUTERS").first();
		return reuters.attr("NEWID");
	}

}
