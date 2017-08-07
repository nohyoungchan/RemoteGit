package HBTestesNG.TestCases;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import Utility.PostCondition;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"Test_Initiate"})
public class  Test_Initiate extends TestCaseObject{
	public static AllActors allActors;

	
	Test_Initiate() throws Exception{
		log.info("* Constructor: Test_initiate");
	}
	
	@BeforeSuite
	@Parameters({"waitUntilOneAm", "skipCCDPrepare"})
	public void beforeSuite(String waitUntilOneAm, String skipCCDPrepare) throws Exception {
		
		//wait_for_inputToStart("Press Enter to start a test");
		waitUntilTomorrowOneAm(waitUntilOneAm);
		allActors = new AllActors();
		startTestSuitMessage();  

			
		for (int i=0; i < allActors.agents.size() ; i++ ){
			if (0 ==allActors.agents.size()) { break;}
			//It is better to log in this way, not by thread because some unknown problem.
			if (TestObject.loginSequentially.contains("yes")) {
				allActors.agents.get(i).logIntoWebAgent();
			}
		}
		
		//Log in Manhattan client
		for (int i=0; i < allActors.customers.size() ; i++ ){
			if (0 ==allActors.customers.size()) { break;}
			//You can set the location of Manhattan: x(10), y(200)
			allActors.customers.get(i).startManhattan("10", "200");
			allActors.customers.get(i).logIn();

		}

		//CCD configuration before starting suite: You can skip this by "skipCCDPrepare" variable
		if (allActors.supervisors.size() >0 && skipCCDPrepare == "no") 
			allActors.supervisors.get(0).Max_LogIn_PrepareTest_LogOut_Min();
		
		currentTimeStart();

		


	}


	@AfterSuite
	@Parameters({"tearDownOrNot"})
	public void afterSuite(String tearDownOrNot) throws Exception {

		if (tearDownOrNot.contains("no")) {
			log.info("\n\n%%% Waiting for a user input: Enter any key to close all actors %%%");
			System.in.read();
			//return;
		}

		
		for (int i=0; i < allActors.agents.size() ; i++ ){
			if (0 ==allActors.agents.size()) { break;}
			
			//if WebBrowser is not closed, close
			if (!allActors.agents.get(i).state.contains("closed"))
				allActors.agents.get(i).tearDownAll();
			wait(1);
		}
		

		for (int i=0; i < allActors.supervisors.size() ; i++ ){
			if (0 ==allActors.supervisors.size()) { break;}
			
			//if WebBrowser is not closed, close
			if (!allActors.supervisors.get(i).state.contains("closed"))
				allActors.supervisors.get(i).tearDownAll();
			wait(1);
		}
		
		for (int i=0; i < allActors.chatCustomers.size() ; i++ ){
			if (0 ==allActors.chatCustomers.size()) { break;}
			//if WebBrowser is not closed, close
			if (!allActors.chatCustomers.get(i).state.contains("closed"))
				allActors.chatCustomers.get(i).tearDownAll();
			wait(1);
		}
		
		for (int i=0; i < allActors.emailCustomers.size() ; i++ ){
			if (0 ==allActors.emailCustomers.size()) { break;}
			
			//if WebBrowser is not closed, close
			if (!allActors.emailCustomers.get(i).state.contains("closed"))
				allActors.emailCustomers.get(i).tearDownAll();
			wait(1);
		}
		
		for (int i=0; i < allActors.customers.size() ; i++ ){
			if (0 ==allActors.customers.size()) { break;}
			
			allActors.customers.get(i).tearDownAll();
			//executeShellCommand("taskkill /IM  ShoreTel.exe /F");  //This is to kill ShoreTel.exe when graceful close failed.

		}
		
		currentTimeEnd();
		totalExecutionTime();
		endtTestSuitMessage();
	}
	
	public static AllActors getAllObjects(){
	    return allActors;
	}

}
