package compressionTable;
import main.Main;
import preprocess.Preprocess;

public class CompressionTable {

	public static int noNumber = Main.unfilteredSize - Preprocess.no_number.size();
	public static int case_fold = Main.unfilteredSize - Preprocess.caseFolding.size();
	public static int stop_words = Main.unfilteredSize - Preprocess.stopwords.size();
	public static double percent_numbers_from_unfiltered = (1 - (((double) (noNumber) / (double) Main.unfilteredSize))) * 100;
	public static double percent_casefolding_from_unfiltered = (1 - (((double) (case_fold) / (double) Main.unfilteredSize))) * 100;
	public static double percent_stopwords_from_unfiltered = (1 - (((double) (stop_words) / (double) Main.unfilteredSize))) * 100;
	public static double percent_previous_numbers = percent_numbers_from_unfiltered;
	public static double percent_previous_stopwords = percent_stopwords_from_unfiltered + percent_previous_numbers;
	public static double percent_previous_case_folding = percent_previous_stopwords+percent_casefolding_from_unfiltered;
	public static void printTable() {
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.printf("%30s %30s %30s", "# distinct terms", "∆ % from unfiltered", "∆ % T");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------");
		System.out.printf("%10s %12s", "Unfiltered ", Main.unfilteredSize + "\n");
		System.out.printf("%10s %12s %37s %35s", "Numbers ", CompressionTable.noNumber,
				"-" + CompressionTable.percent_numbers_from_unfiltered + "%",
				"-" + CompressionTable.percent_previous_numbers + "%" + "\n");
		System.out.printf("%10s %12s %37s %35s", "StopWords ", CompressionTable.stop_words,
				"-" + CompressionTable.percent_stopwords_from_unfiltered + "%",
				"-" + CompressionTable.percent_previous_stopwords + "%" + "\n");
		System.out.printf("%10s %9s %37s %35s", "Case folding ", CompressionTable.case_fold,
				"-" + CompressionTable.percent_casefolding_from_unfiltered + "%",
				"-" + CompressionTable.percent_previous_case_folding + "%" + "\n");
	}
	
}
