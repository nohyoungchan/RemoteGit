package HBTestesNG.TestCases;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import ExceptionCustom.EC_logintoAIC;
import Utility.PostCondition;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"Test_Initiate"})
public class  Test_Initiate extends TestCaseObject{
	public static AllActors allActors;

	
	Test_Initiate() throws Exception{
		log.info("* Constructor: Test_initiate");
	}
	
	@BeforeSuite
	public void beforeSuite() throws Exception {
		
		try  {
			//wait_for_inputToStart("Press Enter to start a test");

			allActors = new AllActors(); //This reads all ini and starts web.
			startTestSuitMessage();  
			waitUntilTomorrowOneAm(AllActors.iniMain.get("TestFlow", "waitUntilOneAm"));
			
			//CCD configuration before starting suite: You can skip this by "skipCCDPrepare" variable
			if (allActors.supervisors.size() >0 &&  AllActors.iniMain.get("TestFlow", "skipCCDPrepare").contains("no")) 
			{ 
				allActors.supervisors.get(0).Max_LogIn_PrepareTest_LogOut_Min();
			}
	
				
			for (int i=0; i < allActors.agents.size() ; i++ ){
				if (0 ==allActors.agents.size()) { break;}
				allActors.agents.get(i).logIntoWebAgent();
			}
			
			//Log in Manhattan client
			String x, y, startLocation;
			for (int i=0; i < allActors.customers.size() ; i++ ){
				 if (0 ==allActors.customers.size()) { break;}
				 //You can set the location of Manhattan: x(10), y(200)
				 startLocation = AllActors.iniMain.get("Manhattan", "startingLocation");
				 String splitResult[] = startLocation.split("/");
				 x= splitResult[0];
				 y= splitResult[1];

				allActors.customers.get(i).startManhattan(x, y);
				allActors.customers.get(i).logIn();
	
			}
	

			
			currentTimeStart();
		}catch (EC_logintoAIC e) {
			log.error("Exception on before suite(logIntoWebAgent: Failing all : " + e.toString());
			afterSuite();
			
		}catch (Exception e) {
			log.error("Exception on before suite: Failing all : " + e.toString());
			afterSuite();
			
		}
	
			


	}


	@AfterSuite
	public void afterSuite() throws Exception {
		
		log.info("\n\n");
		log.info("#######################################################");
		log.info("########### Starting End of TestSuite  ###################");
		log.info("#########################################################\n\n");

		if (AllActors.iniMain.get("TestFlow", "tearDownOrNot").contains("no")) {
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
