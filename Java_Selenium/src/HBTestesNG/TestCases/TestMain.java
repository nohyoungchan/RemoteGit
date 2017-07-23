package HBTestesNG.TestCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

import HBTestesNG.BaseObjects.TestCaseObject;
 
public class TestMain extends TestCaseObject{
	 
	public static void main(String[] args) throws InterruptedException, IOException {
		 

		TestNG runner=new TestNG();
		List<String> suitefiles=new ArrayList<String>();

		suitefiles.add("testing5_LoadTestOnly.xml");
		stopMain = "no";
		
		for (int i = 0; i < 1000; i++){
 			runner.setTestSuites(suitefiles);
			runner.run();

			if(stopMain.equals("yes")) {
				log.info("#### Ending main is requested : Bye ###");
				System.exit(0);
			}
			System.out.println("@@ New test suit would start after 60 sec @@");
			Thread.sleep(60 * 1000);
		}
	}
	
	public static void main2(String[] args) throws InterruptedException {
		 

		TestNG runner=new TestNG();
		List<String> suitefiles=new ArrayList<String>();
		//suitefiles.add("C:\\TestNG\\testing.xml");
		suitefiles.add("testing4_For_LoadTest.xml");
		//suitefiles.add("testing5_LoadTestOnly.xml");
		//suitefiles.add("testing6_Director_Agent.xml");
		//C2-HB2 is missing chat/email, so testing3_For_C2HB2.xml is needed to run some acd scenarios.
	
		runner.setTestSuites(suitefiles);
		runner.run();
		Thread.sleep(5000);
		
/*		for (int i = 0; i < 1000; i++){
 			runner.setTestSuites(suitefiles);
			runner.run();
			Thread.sleep(5000);
		}*/
	}
 
}