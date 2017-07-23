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
	@Parameters({"waitUntilOneAm"})
	public void beforeSuite(String waitUntilOneAm) throws Exception {
		
		//wait_for_inputToStart("Press Enter to start a test");
		waitUntilTomorrowOneAm(waitUntilOneAm);
		allActors = new AllActors();
		aa =allActors;
		startTestSuitMessage();  //actors need to initiate for log to work.
		log.info("I am here 0");
		
		for (int i=0; i < allActors.supervisors.size() ; i++ ){
			if (0 ==allActors.supervisors.size()) { break;}
			allActors.supervisors.get(i).minimizeBrowser();  //minimize: setSizeAndLocation(10, 10, 10, 700);
			wait(3);
		}
		log.info("I am here 1");
		
		for (int i=0; i < allActors.chatCustomers.size() ; i++ ){
			if (0 ==allActors.chatCustomers.size()) { break;}
			allActors.chatCustomers.get(i).minimizeBrowser();
			wait(3);
		}
		log.info("I am here 2");
		
		for (int i=0; i < allActors.emailCustomers.size() ; i++ ){
			if (0 ==allActors.emailCustomers.size()) { break;}
			allActors.emailCustomers.get(i).minimizeBrowser();
			wait(3);
		}
		log.info("I am here 3");
		
		for (int i=0; i < allActors.customers.size() ; i++ ){
			if (0 ==allActors.customers.size()) { break;}
			allActors.customers.get(i).logIntoCMWin();;
			wait(3);
			allActors.customers.get(i).minimizeBrowser();
			//allActors.customers.get(i).setSizeAndLocation(1300, 200, 20, 500);
		}
		log.info("I am here 4");
		
		
		int x, y; x=5; y=5;
		for (int i=0; i < allActors.agents.size() ; i++ ){
			if (0 ==allActors.agents.size()) { break;}
			//It is better to log in this way, not by thread because some unknown problem.
			if (TestObject.loginSequentially.contains("yes")) {
				allActors.agents.get(i).logIntoWebAgent();
			}
			//allActors.agents.get(i).MinimizeAll();  //this is not working.
			//allActors.agents.get(i).setSizeAndLocation(1000, 300, -2000, 0);
			allActors.agents.get(i).setSizeAndLocation(1000, 300, x, y);
			x = x+20; y = y+50;
			//wait(2);	
		}
		log.info("I am here 5");

		if (allActors.supervisors.size() >0 ) allActors.supervisors.get(0).Max_LogIn_PrepareTest_LogOut_Min();
		waitUntilTomorrowOneAm(waitUntilOneAm);
		currentTimeStart();
		log.info("I am here 6");
		


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
		
		for (int i=0; i < allActors.customers.size() ; i++ ){
			if (0 ==allActors.customers.size()) { break;}
			
			//if WebBrowser is not closed, close
			if (!allActors.customers.get(i).state.contains("closed"))
				allActors.customers.get(i).tearDownAll();

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
		
		currentTimeEnd();
		totalExecutionTime();
		endtTestSuitMessage();
	}
	
	public static AllActors getAllObjects(){
	    return allActors;
	}

}
