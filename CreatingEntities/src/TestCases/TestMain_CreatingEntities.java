package TestCases;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
 
public class TestMain_CreatingEntities {
	 
	public static void main(String[] args) throws InterruptedException {
		 

		TestNG runner=new TestNG();
		List<String> suitefiles=new ArrayList<String>();

		suitefiles.add("testing.xml");
		
		runner.setTestSuites(suitefiles);
		runner.run();
		Thread.sleep(5000);
	}
 
}