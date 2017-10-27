package HBTestesNG.BaseObjects;

import java.util.ArrayList;
import java.util.List;

import java.util.Hashtable;

public final class TestStatus extends TestObject{

	public static List<String> errorReason = new ArrayList<String>();
	public static Hashtable<String, String> failedCases  = new Hashtable<String, String>(); //String:Method name, String: error reason
	public static Hashtable<String, String> skippedCases  = new Hashtable<String, String>(); //String:Method name, String: skpped reason
	public static void printFailedCases() {
		log.info("### Prinitng Failed test cases ###");
		log.info("==> Total failed test cases: " + failedCases.size());
		int i = 1;
		for (String key : failedCases.keySet()) {
			log.info(i + ") " + key + " : " + failedCases.get(key));
			i++;
		}
		log.info("\n");
		log.info("==> Detailed failed test cases");
		String[] errors;
		i = 1;
		int j, k;
		k = 1;
		for (String key : failedCases.keySet()) 
		{
			log.info(i + ". " + key + " :");
			i++;
			errors = failedCases.get(key).split(",");
			for (j = 0; j < errors.length; j++)
			{
			   log.info("\t" + k + ") " + errors[j]);
			   k = k+1;
			}
		}
	}
	
	public static void printSkippedCases() {
		log.info("### Prinitng Skipped test cases ###");
		log.info("==> Total Skipped test cases: " + skippedCases.size());
		int i = 1;
		for (String key : skippedCases.keySet()) {
			log.info(i + ") " + key + " : " + skippedCases.get(key));
			i++;
		}
	}
	
}
