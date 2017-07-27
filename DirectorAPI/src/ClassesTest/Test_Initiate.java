package ClassesTest;

import ClassesEntity.*;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import CCEntity.JasonHBSupervisor;



public class  Test_Initiate extends TestCaseObject{
	protected static AllObjects allObjects;
	String strJasonMessage, strReturn;

	
	Test_Initiate() throws Exception{
		log.info("* Constructor: Test_initiate");
	}
	
	@BeforeSuite
	@Parameters({"waitUntilOneAm"})
	public void beforeSuite(String waitUntilOneAm) throws Exception {
		String testName = "Test_Initiate: beforeSuite";
		startTestSuitMessage(testName);
		
		//wait_for_inputToStart("Press Enter to start a test");
		waitUntilTomorrowOneAm(waitUntilOneAm);

		//############ Read testData.ini file and initiate all objects
		allObjects = new AllObjects();
		//strCertificate = apiTesterClient.logIn(hbSup);
		currentTimeStart();

	}


	@AfterSuite
	@Parameters({"tearDownOrNot"})
	public void afterSuite(String tearDownOrNot) throws Exception {

		
		currentTimeEnd();
		totalExecutionTime();
		endtTestSuitMessage();
	}
	
	public static AllObjects getAllObjects(){
	    return allObjects;
	}

}
