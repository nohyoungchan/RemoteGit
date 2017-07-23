package TestCases;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;


import Actors.*;

@Test(groups= {"Test_Initiate"})
public class  Test_Prepare extends TestCaseBaseObject{
	public static AllEntities all;

	
	Test_Prepare() throws Exception{
		log.info("* Constructor: Test_Prepare");
	}
	
	@BeforeSuite
	public void beforeSuite() throws Exception {
		
		startTestSuitMessage();  //actors need to initiate for log to work.
		currentTimeStart();
		all = new AllEntities();
		

	}


	@AfterSuite
	public void afterSuite() throws Exception {

		currentTimeEnd();
		totalExecutionTime();
		endtTestSuitMessage();
	}


}
