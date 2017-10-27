package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"CCDirector_ClassLevel"})
public class Test_CCDirector extends TestCaseObject {

	Test_CCDirector() throws Exception{
		log.info("* Construction: Test_CCDirector");

	}

	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_CCDirector");
		InitializeAllVariables(); 
    }
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_CCDirector");
	}
	
	
	


	
	public void CCDirector_Service() throws Exception {
		String testName = "CCDirector_Service";
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) throw new SkipException("Skipping because it is not ready");
		
		
		//The following value is entered while running scenarios to stop all
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		try{

			sup1.Max_LogIn_PrepareTest_LogOut_Min();

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	

	


}
